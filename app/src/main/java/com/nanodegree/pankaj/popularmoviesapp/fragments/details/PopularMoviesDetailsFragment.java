package com.nanodegree.pankaj.popularmoviesapp.fragments.details;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nanodegree.pankaj.popularmoviesapp.R;
import com.nanodegree.pankaj.popularmoviesapp.adapters.ImageCursorAdapter;
import com.nanodegree.pankaj.popularmoviesapp.data.MovieContract;
import com.nanodegree.pankaj.popularmoviesapp.fragments.base.PopularMoviesBaseFragment;
import com.nanodegree.pankaj.popularmoviesapp.model.MovieReview;
import com.nanodegree.pankaj.popularmoviesapp.model.MovieReviews;
import com.nanodegree.pankaj.popularmoviesapp.model.MovieTrailer;
import com.nanodegree.pankaj.popularmoviesapp.model.MovieTrailers;
import com.nanodegree.pankaj.popularmoviesapp.model.MoviesResults;
import com.nanodegree.pankaj.popularmoviesapp.movieapi.MoviesAPI;
import com.nanodegree.pankaj.popularmoviesapp.utils.ImagePathUtil;
import com.nanodegree.pankaj.popularmoviesapp.utils.JsonUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PopularMoviesDetailsFragment extends PopularMoviesBaseFragment<PopularMoviesDetailsFragment.PopularMoviesDetailsFragmentListener> {

    public static String FRAGMENT_TAG = "PopularMoviesDetailsFragment";
    @Bind(R.id.movie_title)
    TextView mMovieTitle;
    @Bind(R.id.movie_rating)
    TextView mMovieRating;
    @Bind(R.id.movie_overview)
    TextView mMovieOverview;
    @Bind(R.id.movie_release_date)
    TextView mMovieRelease;
    @Bind(R.id.movie_backdrop_image)
    ImageView mBackDropImageView;
    @Bind(R.id.movie_poster)
    ImageView mPosterImageView;
    MoviesResults mMoviesResults;
    @Bind(R.id.favoriteButton)
    ImageButton mFavoriteButton;
    @Bind(R.id.movie_reviews)
    LinearLayout mMovieReviews;
    @Bind(R.id.button_trailer)
    Button mButtonTrailer;
    static final String MOVIE_DETAILS = "MovieDetails";


    private String mTrailerLink;
    private String mMovieDetails = "NA";
    private final String EXTRA_MESSAGE="MovieDetails";

    private ImageCursorAdapter mImageCursorAdapter;

    private static final String[] MOVIES_COLUMNS={
            MovieContract.MovieEntries._ID,
            MovieContract.MovieEntries.COLUMN_NAME_MOVIE_ID,
            MovieContract.MovieEntries.COLUMN_NAME_MOVIE_DETAILS
    };
    // These indices are tied to MOVIES_COLUMNS.  If MOVIES_COLUMNS changes, these
    // must change
    static final int COL_ID=0;
    static final int COL_MOVIE_ID=1;
    static final int COL_MOVIE_DETAILS=2;

    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;

    public static PopularMoviesDetailsFragment newInstance(Bundle args) {
        PopularMoviesDetailsFragment fragment = new PopularMoviesDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public PopularMoviesDetailsFragment() {
    }

    @Override
    public View onInitFragment(View rootView, Bundle savedInstanceState) {

        mMovieTitle.setText(mMoviesResults.getTitle());

        if (mMoviesResults.getVoteAverage() != null) {
            mMovieRating.setText(getResources().getString(R.string.rating) + mMoviesResults.getVoteAverage().toString());

        } else {
            mMovieRating.setText(getResources().getString(R.string.rating) + getResources().getString(R.string.na));
        }
        if ((mMoviesResults.getOverview() != null)) {
            mMovieOverview.setText(mMoviesResults.getOverview());

        } else {
            mMovieOverview.setText(getResources().getString(R.string.na));
        }

        if (mMoviesResults.getReleaseDate() != null) {
            mMovieRelease.setText(getResources().getString(R.string.release_date) + mMoviesResults.getReleaseDate());

        } else {
            mMovieRelease.setText(getResources().getString(R.string.release_date) + getResources().getString(R.string.na));

        }
        Picasso.with(getActivity()).load((ImagePathUtil.getMovieImageBackDropPath(getActivity(), mMoviesResults.getBackdropPath()))).placeholder(R.drawable.loading_image_back_path).error(R.drawable.loading_image_error_place_holder).into(mBackDropImageView);
        Picasso.with(getActivity()).load((ImagePathUtil.getMovieImagePosterPath(getActivity(), mMoviesResults.getPosterPath()))).placeholder(R.drawable.loading_image_placeholder).error(R.drawable.loading_image_error_place_holder).into(mPosterImageView);
        getReviewsAndTrailers(rootView);
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallback().onFavoriteButtonClicked(mMoviesResults.getId().toString(), mMovieDetails);
            }
        });
        return rootView;
    }


    private void getReviewsAndTrailers(final View rootView) {
        // set the trailer link for the View trailer button
        RestAdapter restAdapter;
        restAdapter = new RestAdapter.Builder().setEndpoint(getString(R.string.base_url)).build();
        MoviesAPI moviesAPI = restAdapter.create(MoviesAPI.class);
        moviesAPI.getMovieTrailers(mMoviesResults.getId().toString(), getString(R.string.api_key), new Callback<MovieTrailers>() {
            @Override
            public void success(MovieTrailers movieTrailers, Response response) {
                List<MovieTrailer> trailers = movieTrailers.getResults();
                for (MovieTrailer movieTrailer : trailers) {
                    if (movieTrailer.getSite().compareTo("YouTube") == 0) {
                        mTrailerLink = movieTrailer.getKey();
                        break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                mTrailerLink = "";
            }
        });

        moviesAPI.getMovieReviews(mMoviesResults.getId().toString(), getString(R.string.api_key), new Callback<MovieReviews>() {
            @Override
            public void success(MovieReviews movieReviews, Response response) {
                List<MovieReview> movieReviewList = movieReviews.getResults();
                for (MovieReview movieReview : movieReviewList) {
                    TextView movie_review_content = new TextView(rootView.getContext());
                    movie_review_content.setGravity(Gravity.LEFT);
                    movie_review_content.setPadding(10, 10, 10, 10);
                    movie_review_content.setText(movieReview.getContent());
                    TextView movie_review_author = new TextView(rootView.getContext());
                    movie_review_author.setGravity(Gravity.RIGHT);
                    movie_review_author.setPadding(10, 10, 10, 10);
                    movie_review_author.setTextColor(getResources().getColor(R.color.abc_primary_text_material_dark));
                    movie_review_author.setText("- Reviewed by: " + movieReview.getAuthor());
                    mMovieReviews.addView(movie_review_content);
                    mMovieReviews.addView(movie_review_author);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        mButtonTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallback().onFragmentPlayTrailer(mTrailerLink);
            }
        });
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_popular_movies_details;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        mMoviesResults = getArguments().getParcelable("movie_result");
        mMovieDetails = JsonUtil.convertToJsonString(getActivity(), mMoviesResults);


    }

    @Override
    public int getFragmentTitleResource() {
        return R.string.title_fragment_popular_movies_details;
    }


    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }


    public interface PopularMoviesDetailsFragmentListener extends PopularMoviesBaseFragment.PopularMoviesBaseFragmentListener {

        void onFragmentPlayTrailer(String trailerLink);

        void onFavoriteButtonClicked(String movieId, String movieDetail);
    }

}
