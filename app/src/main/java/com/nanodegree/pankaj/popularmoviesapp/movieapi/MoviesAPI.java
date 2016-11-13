package com.nanodegree.pankaj.popularmoviesapp.movieapi;

import com.nanodegree.pankaj.popularmoviesapp.model.GetMoviesResponse;
import com.nanodegree.pankaj.popularmoviesapp.model.MovieReviews;
import com.nanodegree.pankaj.popularmoviesapp.model.MovieTrailers;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by pankaj.singh on 11/4/2016.
 */
public interface MoviesAPI {
    @GET("/discover/movie")
    void getMovieList(@Query("sort_by") String sort_by, @Query("api_key") String api_key, Callback<GetMoviesResponse> cb);

    @GET("/movie/{id}/videos")
    void getMovieTrailers(@Path("id") String id, @Query("api_key") String api_key, Callback<MovieTrailers> cb);

    @GET("/movie/{id}/reviews")
    void getMovieReviews(@Path("id") String id, @Query("api_key") String api_key, Callback<MovieReviews> cb);
}
