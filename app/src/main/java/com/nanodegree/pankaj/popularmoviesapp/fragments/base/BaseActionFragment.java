package com.nanodegree.pankaj.popularmoviesapp.fragments.base;

import android.os.Bundle;

import com.nanodegree.pankaj.popularmoviesapp.interfaces.BaseActionFragmentInterface;

public abstract class BaseActionFragment<E extends BaseActionFragment.BaseActionFragmentListener> extends BaseFragment<E> implements BaseActionFragmentInterface {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (getFragmentTitleResource() != -1)
            getCallback().onRequestTitleUpdate(getFragmentTitleResource());
        getCallback().onRequestDisplayHomeAsUpEnabled(requestDisplayHomeAsUpEnabled());
        getCallback().onRequestDisplayShowHomeEnabled(requestDisplayShowHomeEnabled());
    }

    private boolean requestDisplayShowHomeEnabled() {
        return true;
    }

    protected boolean requestDisplayHomeAsUpEnabled() {
        return true;
    }

    public abstract int getFragmentTitleResource();


    public interface BaseActionFragmentListener extends BaseFragmentListener {
        /**
         * Let the activity know we want to pop the stack
         */
        void onFragmentActionBack();

        /**
         * Let the activity know we want to finish the flow
         */
        void onFragmentActionFinish();

        /**
         * Suggested ActionBar display to activity
         */
        void onRequestDisplayHomeAsUpEnabled(boolean value);

        /**
         * Suggested ActionBar display to activity
         */
        void onRequestDisplayShowHomeEnabled(boolean value);

        /**
         * Suggested ActionBar title display
         */
        void onRequestTitleUpdate(int titleResource);

        
    }

    @Override
    public void onBackClicked() {

        getCallback().onFragmentActionBack();


    }


}
