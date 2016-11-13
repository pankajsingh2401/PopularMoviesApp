package com.nanodegree.pankaj.popularmoviesapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.nanodegree.pankaj.popularmoviesapp.fragments.base.BaseFragment;
import com.nanodegree.pankaj.popularmoviesapp.interfaces.BaseFragmentInterface;
import com.nanodegree.pankaj.popularmoviesapp.utils.CustomToast;

public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.BaseFragmentListener {

    protected Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(getTransitionIn(), getTransitionOut());

    }


    @Override
    public void showToast(String message) {
        CustomToast.showText(this, message,
                Toast.LENGTH_SHORT);

    }


    protected void launchFragment(final Fragment fragment, final int fragmentReplaceId, final int animationIn, final int animationOut, final int popAnimationIn, final int popAnimationOut) {
        handleLaunchFragment(fragment, fragmentReplaceId, animationIn, animationOut, popAnimationIn, popAnimationOut);
    }

    private void handleLaunchFragment(Fragment fragment, int fragmentReplaceId, int animationIn, int animationOut, int popAnimationIn, int popAnimationOut) {
        String fragmentTag = getFragmentTag(fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(fragmentTag);
        ft.setCustomAnimations(animationIn, animationOut, popAnimationIn, popAnimationOut);
        ft.replace(fragmentReplaceId, fragment, fragmentTag);
        try {
            ft.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    protected String getFragmentTag(Fragment fragment) {
        if (fragment instanceof BaseFragmentInterface) {
            return ((BaseFragmentInterface) fragment).getFragmentTag();
        } else {
            return fragment.getClass().getSimpleName();
        }
    }

    protected int getTransitionIn() {
        return android.R.anim.fade_in;
    }

    protected int getTransitionOut() {
        return android.R.anim.fade_out;
    }

    protected abstract int getFragmentContentFrameResourceID();

    @Override
    public void finish() {

        super.finish();
        overridePendingTransition(getTransitionIn(), getTransitionOut());
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {

        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(getTransitionIn(), getTransitionOut());
    }

    /**
     * Suggested to lock the orientation of the screen
     */
    protected void lockOrientation() {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    int rotation = getWindowManager().getDefaultDisplay().getRotation();
                    if (rotation == android.view.Surface.ROTATION_90 || rotation == android.view.Surface.ROTATION_180) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.FROYO) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    int rotation = getWindowManager().getDefaultDisplay().getRotation();
                    if (rotation == android.view.Surface.ROTATION_0 || rotation == android.view.Surface.ROTATION_90) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    } else {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    }
                }
                break;
        }
    }

    /**
     * Suggested to unlock the orientation of the screen
     */
    protected void unLockOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    protected void unlockTouchInput() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    protected void lockTouchInput() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
