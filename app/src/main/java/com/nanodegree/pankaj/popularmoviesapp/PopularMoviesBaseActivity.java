package com.nanodegree.pankaj.popularmoviesapp;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.nanodegree.pankaj.popularmoviesapp.fragments.base.PopularMoviesBaseFragment;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PopularMoviesBaseActivity extends BaseActionActivity implements PopularMoviesBaseFragment.PopularMoviesBaseFragmentListener, FragmentManager.OnBackStackChangedListener {
    private final int ANIMATION_TRANSITION_DURATION = 500;
    protected boolean mLoadingDisplay = false;
    private AnimatorSet mAnimSet;
    @Bind(R.id.common_loading_display)
    protected ProgressBar mProgressBar;
    @Bind(R.id.content_frame)
    protected FrameLayout mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies_base);
        setupActionBar();
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        ButterKnife.bind(this);


    }

    /**
     * Default action bar initialization
     */
    protected void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);// enables home up and shows arrow
            getSupportActionBar().setDisplayShowHomeEnabled(false);// shows logo
        }

    }


    @Override
    protected int getFragmentContentFrameResourceID() {
        return R.id.content_frame;
    }

    @Override
    public void onSuggestActionBar(boolean show) {
        if (getSupportActionBar() != null) {
            if (!getSupportActionBar().isShowing() && show) {
                getSupportActionBar().show();
            } else if (getSupportActionBar().isShowing() && !show) {
                getSupportActionBar().hide();
            }
        }
    }


    /**
     * Show activity loading display
     */

    public void showLoadingDisplay() {
        if (mLoadingDisplay)
            return;
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingDisplay = true;
        mAnimSet = new AnimatorSet();
        mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "alpha", 1.0f, 0.5f));
        mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
        lockOrientation();
        lockTouchInput();

    }


    /**
     * Hide activity loading display
     */


    public void hideLoadingDisplay() {
        if (!mLoadingDisplay)
            return;
        mProgressBar.setVisibility(View.INVISIBLE);
        mLoadingDisplay = false;
        mAnimSet = new AnimatorSet();
        mAnimSet.playTogether(ObjectAnimator.ofFloat(mContent, "alpha", 0.5f, 1.0f),
                ObjectAnimator.ofFloat(mContent, "rotationY", 0, 0)
        );
        unLockOrientation();
        unlockTouchInput();
        mAnimSet.setDuration(ANIMATION_TRANSITION_DURATION).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies_base, menu);
        return true;
    }

    /**
     * To unlock the touch input of the view
     */
    protected void unlockTouchInput() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        onFragmentActionBack();
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

