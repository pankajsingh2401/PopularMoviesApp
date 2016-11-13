package com.nanodegree.pankaj.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.nanodegree.pankaj.popularmoviesapp.fragments.common.CommonErrorFragment;
import com.nanodegree.pankaj.popularmoviesapp.fragments.dashboard.PopularMoviesDashboardFragment;
import com.nanodegree.pankaj.popularmoviesapp.fragments.details.PopularMoviesDetailsFragment;
import com.nanodegree.pankaj.popularmoviesapp.model.GetMoviesResponse;
import com.nanodegree.pankaj.popularmoviesapp.model.MoviesResults;
import com.nanodegree.pankaj.popularmoviesapp.movieapi.MoviesAPI;
import com.nanodegree.pankaj.popularmoviesapp.utils.Connectivity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PopularMoviesDashboardActivity extends PopularMoviesBaseActivity implements PopularMoviesDashboardFragment.PopularMoviesDashboardFragmentListener, CommonErrorFragment.CommonErrorFragmentListener {
    public final String movie_list = "movie_list";
    ArrayList<String> mPosterPaths;
    public final String sort_by = "sort_by";
    public final String movie_result = "movie_result";

    private String mSortingPreference;
    private List<MoviesResults> mMoviesResults;
    private static final String TAG = PopularMoviesDashboardActivity.class.getName();
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isTablet(this)) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, PopularMoviesDetailsFragment.newInstance(new Bundle()), PopularMoviesDetailsFragment.FRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            if (savedInstanceState == null) {
                requestMoviesDetails();
            }

        }


    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    protected void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        }
    }

    private void requestMoviesDetails() {
        RestAdapter restAdapter;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mSortingPreference = sharedPref.getString(getString(R.string.pref_sort_title), getString(R.string.pref_sort_default_value));
        restAdapter = new RestAdapter.Builder().setEndpoint(getString(R.string.base_url)).build();
        MoviesAPI moviesAPI = restAdapter.create(MoviesAPI.class);
        showLoadingDisplay();
        moviesAPI.getMovieList(mSortingPreference, getString(R.string.api_key), new Callback<GetMoviesResponse>() {
            @Override
            public void success(GetMoviesResponse moviesResponse, Response response) {
                hideLoadingDisplay();
                mMoviesResults = moviesResponse.getMoviesResults();
                Log.d(TAG, "" + moviesResponse.getMoviesResults().size());
                mPosterPaths = new ArrayList<>();
                for (int i = 0; i < mMoviesResults.size(); i++) {
                    MoviesResults moviesResult = mMoviesResults.get(i);
                    mPosterPaths.add(moviesResult.getPosterPath());
                }
                Bundle args = new Bundle();
                args.putString(sort_by, mSortingPreference);
                args.putStringArrayList(movie_list, mPosterPaths);
                launchFragment(PopularMoviesDashboardFragment.newInstance(args));
            }

            @Override
            public void failure(RetrofitError error) {
                hideLoadingDisplay();
                launchFragment(CommonErrorFragment.newInstance(new Bundle()));
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(movie_result, (ArrayList<? extends Parcelable>) mMoviesResults);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mMoviesResults = savedInstanceState.getParcelableArrayList(movie_result);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //evaluate mTwoPane
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onRetryButtonClicked() {
        if (Connectivity.isConnected(this)) {
            requestMoviesDetails();

        }
        showToast(getString(R.string.common_error_fragment_message));
    }

    @Override
    public void onMoviePosterClicked(int position) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelableArrayList(movie_result, (ArrayList<? extends Parcelable>) mMoviesResults);

            // args.putString(PopularMoviesDetailsFragment.MOVIE_DETAILS, mMoviesResults);

            PopularMoviesDetailsFragment fragment = new PopularMoviesDetailsFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, PopularMoviesDetailsFragment.FRAGMENT_TAG)
                    .commit();


        } else {
            Intent intent = new Intent(this, PopularMoviesDetailsActivity.class);
            intent.putExtra(movie_result, mMoviesResults.get(position));
            startActivity(intent);
        }
    }
}
