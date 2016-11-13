package com.nanodegree.pankaj.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.nanodegree.pankaj.popularmoviesapp.fragments.details.PopularMoviesDetailsFragment;
import com.nanodegree.pankaj.popularmoviesapp.utils.CustomToast;

public class PopularMoviesDetailsActivity extends PopularMoviesBaseActivity implements PopularMoviesDetailsFragment.PopularMoviesDetailsFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle args = getIntent().getExtras();
            args.getParcelable("movie_result");
            launchFragment(PopularMoviesDetailsFragment.newInstance(args));

        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }


    @Override
    public void onFragmentPlayTrailer(String trailerLink) {
        if (trailerLink == "" || trailerLink == null) {
            //show some error regarding trailer not available
            CustomToast.makeText(this, "No trailer available", Toast.LENGTH_LONG).show();
        } else {
            //get the video link
            String youtubeLink = "https://www.youtube.com/watch?v=";
            //set intent
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink + trailerLink));
            //start intent
            startActivity(intent);
        }
    }

    @Override
    public void onFavoriteButtonClicked(String mMovieId, String mMovieDetails) {
        //check the current state of the movie as favorite
        AddMovieTask addMovieTask = new AddMovieTask(this);
        addMovieTask.execute(mMovieId, mMovieDetails);
    }
}
