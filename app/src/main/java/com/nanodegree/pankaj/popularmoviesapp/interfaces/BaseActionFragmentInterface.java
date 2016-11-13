package com.nanodegree.pankaj.popularmoviesapp.interfaces;

/**
// * Created by pankaj.singh on 11/06/2016.
 */
public interface BaseActionFragmentInterface {
    /**
     * Activity will call this if it want to ask the fragment for navigation advice
     */
    void onBackClicked();


    /**
     * Suggested ActionBar title resource
     */
    int getFragmentTitleResource();
}
