package com.nanodegree.pankaj.popularmoviesapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.nanodegree.pankaj.popularmoviesapp.fragments.base.BaseActionFragment;
import com.nanodegree.pankaj.popularmoviesapp.interfaces.BaseActionFragmentInterface;

import java.util.ArrayList;

public abstract class BaseActionActivity extends BaseActivity implements BaseActionFragment.BaseActionFragmentListener, FragmentManager.OnBackStackChangedListener {


    private static final String FRAGMENT_LIST = "mSaveFragmentFinishToList";

    public static final int ANIMATION_ENTER_IN = R.anim.slide_enter_right_to_left;
    public static final int ANIMATION_ENTER_OUT = R.anim.slide_enter_left_to_right;
    public static final int ANIMATION_EXIT_IN = R.anim.slide_exit_right_to_left;
    public static final int ANIMATION_EXIT_OUT = R.anim.slide_exit_left_to_right;
    protected ArrayList<Integer> mSaveFragmentFinishToList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


//     Launch  a new never leave fragment using the default animation If you are displaying the default loading interface, this method will disable the interface and transition properly


    public void launchFragment(Fragment fragment) {
        launchFragment(fragment, getFragmentContentFrameResourceID(), ANIMATION_ENTER_IN, ANIMATION_ENTER_OUT, ANIMATION_EXIT_IN, ANIMATION_EXIT_OUT, false);
    }


    protected void launchFragment(Fragment fragment, int fragmentReplaceId, int animationIn, int animationOut, int popAnimationIn, int popAnimationOut, boolean popStop) {
        if (popStop)
            mSaveFragmentFinishToList.add(getSupportFragmentManager().getBackStackEntryCount() + 1);
        super.launchFragment(fragment, fragmentReplaceId, animationIn, animationOut, popAnimationIn, popAnimationOut);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(FRAGMENT_LIST, mSaveFragmentFinishToList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mSaveFragmentFinishToList = savedInstanceState.getIntegerArrayList(FRAGMENT_LIST);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackStackChanged() {

    }


    protected BaseActionFragmentInterface getBaseActionFragmentInterface() {
        return ((BaseActionFragmentInterface) getSupportFragmentManager().findFragmentById(getFragmentContentFrameResourceID()));
    }


    /**
     * Action fragment giving navigation to back fragment
     */
    public void onBackPressed() {
        // By default we ask the fragment for navigation advice
        BaseActionFragmentInterface bf = getBaseActionFragmentInterface();
        if (bf != null) {
            bf.onBackClicked();
        } else {
            super.onBackPressed();
        }
    }


    protected void popStack() {
        int nextBackStackCount = getSupportFragmentManager().getBackStackEntryCount() - 1;
        mSaveFragmentFinishToList.clear();
        if (nextBackStackCount == 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 300);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onRequestDisplayHomeAsUpEnabled(boolean value) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(value);// enables home up and shows arrow
            getSupportActionBar().setHomeButtonEnabled(value);
        }

    }

    @Override
    public void onRequestDisplayShowHomeEnabled(boolean value) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(value);// shows logo
        }
    }

    @Override
    public void onRequestTitleUpdate(int titleResource) {
        if (getSupportActionBar() != null) {
            if (titleResource != 0 && titleResource != -1)
                getSupportActionBar().setTitle(titleResource);
        }
    }

    public void saveFragmentFinishTo() {
        mSaveFragmentFinishToList.add(getSupportFragmentManager().getBackStackEntryCount() - 1);
    }

    protected void clearFragmentFinishTo() {
        mSaveFragmentFinishToList.clear();
    }

    protected boolean hasFragmentFinishToSet() {
        return mSaveFragmentFinishToList.size() != 0;
    }

    public void removeTopFinishTo() {
        mSaveFragmentFinishToList.remove(mSaveFragmentFinishToList.size() - 1);
    }
    /**
     * Action fragment giving navigation to parent activity
     */
    @Override
    public void onFragmentActionBack() {
        popStack();
    }

    /**
     * Action fragment giving navigation to parent activity
     */
    @Override
    public void onFragmentActionFinish() {
        // onFragmentActionFinish means a confirmation screen was being displayed which is related to some form of money transactions
        // Set the result to trigger a transaction update request when the home activity resumes

        // if activity saved a pop to header, try to pop back to it
        if (!mSaveFragmentFinishToList.isEmpty()) {
            int destination = mSaveFragmentFinishToList.get(mSaveFragmentFinishToList.size() - 1);
            while (destination < getSupportFragmentManager().getBackStackEntryCount() - 1) {
                getSupportFragmentManager().popBackStackImmediate();
                if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                    finish();
            }
            mSaveFragmentFinishToList.remove(mSaveFragmentFinishToList.size() - 1);
            return;
        }
        finish();
    }


    protected abstract int getFragmentContentFrameResourceID();
}