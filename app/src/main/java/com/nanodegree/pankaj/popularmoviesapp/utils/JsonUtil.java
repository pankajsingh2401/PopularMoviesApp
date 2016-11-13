package com.nanodegree.pankaj.popularmoviesapp.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.nanodegree.pankaj.popularmoviesapp.model.MoviesResults;

/**
 * Created by Pankaj Singh on 11/6/2016.
 */
public class JsonUtil {

    public static String convertToJsonString(Context context, MoviesResults mMovieDetails) {
        Gson gson = new Gson();
        return gson.toJson(mMovieDetails);
    }

}
