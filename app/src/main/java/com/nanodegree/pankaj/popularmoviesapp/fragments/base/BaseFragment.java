package com.nanodegree.pankaj.popularmoviesapp.fragments.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanodegree.pankaj.popularmoviesapp.BaseActivity;
import com.nanodegree.pankaj.popularmoviesapp.interfaces.BaseFragmentInterface;

import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class BaseFragment<E extends BaseFragment.BaseFragmentListener> extends Fragment implements BaseFragmentInterface {


    protected E mCallback;
    protected BaseActivity mActivity;


    public abstract View onInitFragment(View rootView, Bundle savedInstanceState);


    public abstract int getLayout();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if (!ensureActivityImplementsCallback())
            return;
        try {
            mCallback = (E) activity;
            mActivity = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement BaseFragmentListener");
        }
    }

    public interface BaseFragmentListener {



        void showToast(String message);


    }



    public void showToast(String message) {
        getCallback().showToast(message);
    }

    public E getCallback() {
        return mCallback;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRetainInstance(true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    protected boolean ensureActivityImplementsCallback() {
        return true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, rootView);
        onInitFragment(rootView, savedInstanceState);


        return rootView;
    }
}
