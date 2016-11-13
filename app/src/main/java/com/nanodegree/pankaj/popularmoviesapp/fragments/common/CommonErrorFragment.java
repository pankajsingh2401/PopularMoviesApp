package com.nanodegree.pankaj.popularmoviesapp.fragments.common;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nanodegree.pankaj.popularmoviesapp.R;
import com.nanodegree.pankaj.popularmoviesapp.fragments.base.PopularMoviesBaseFragment;

import butterknife.Bind;


public class CommonErrorFragment extends PopularMoviesBaseFragment<CommonErrorFragment.CommonErrorFragmentListener> {

    public static String FRAGMENT_TAG = "CommonErrorFragment";

    @Bind(R.id.fragment_error_retryButton)
    Button mRetryButton;

    public static CommonErrorFragment newInstance(Bundle args) {
        CommonErrorFragment fragment = new CommonErrorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CommonErrorFragment() {

    }

    @Override
    public View onInitFragment(View rootView, Bundle savedInstanceState) {
        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallback().onRetryButtonClicked();
            }
        });
        return rootView;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_common_error;
    }


    @Override
    public int getFragmentTitleResource() {
        return R.string.error_fragment_title;
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }


    public interface CommonErrorFragmentListener extends PopularMoviesBaseFragment.PopularMoviesBaseFragmentListener {
        void onRetryButtonClicked();
    }

    @Override
    public void onBackClicked() {
        getCallback().onFragmentActionFinish();

    }

    @Override
    protected boolean requestDisplayHomeAsUpEnabled() {
        return true;
    }
}
