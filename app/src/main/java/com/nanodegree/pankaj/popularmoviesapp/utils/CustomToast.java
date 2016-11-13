package com.nanodegree.pankaj.popularmoviesapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

/**
 * Created by Pankaj Singh on 11/9/2016.
 */
public class CustomToast {
    /**
     * {@link Toast} decorator allowing for easy cancellation of notifications. Use
     * this class if you want subsequent Toast notifications to overwrite current
     * ones. </p>
     * <p/>
     * By default, a current {@link Boast} notification will be cancelled by a
     * subsequent notification. This default behaviour can be changed by calling
     * certain methods like {@link #show(boolean)}.
     */

    /**
     * Keeps track of certain {@link CustomToast} notifications that may need to be cancelled.
     * This functionality is only offered by some of the methods in this class.
     */
    private volatile static CustomToast globalBoast = null;

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Internal reference to the {@link Toast} object that will be displayed.
     */
    private Toast internalToast;

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Private constructor creates a new {@link CustomToast} from a given
     * {@link Toast}.
     *
     * @throws NullPointerException if the parameter is <code>null</code>.
     */
    private CustomToast(Toast toast) {
        // null check
        if (toast == null) {
            throw new NullPointerException(
                    "Boast.Boast(Toast) requires a non-null parameter.");
        }

        internalToast = toast;
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Make a standard {@link CustomToast} that just contains a text view.
     */
    @SuppressLint("ShowToast")
    public static CustomToast makeText(Context context, CharSequence text,
                                       int duration) {
        return new CustomToast(Toast.makeText(context, text, duration));
    }

    /**
     * Make a standard {@link CustomToast} that just contains a text view with the
     * text from a resource.
     */
    @SuppressLint("ShowToast")
    public static CustomToast makeText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        return new CustomToast(Toast.makeText(context, resId, duration));
    }

    /**
     * Make a standard {@link CustomToast} that just contains a text view. Duration
     */
    @SuppressLint("ShowToast")
    public static CustomToast makeText(Context context, CharSequence text) {
        return new CustomToast(Toast.makeText(context, text, Toast.LENGTH_SHORT));
    }

    /**
     * Make a standard {@link CustomToast} that just contains a text view with the
     */
    @SuppressLint("ShowToast")
    public static CustomToast makeText(Context context, int resId)
            throws Resources.NotFoundException {
        return new CustomToast(Toast.makeText(context, resId, Toast.LENGTH_SHORT));
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Show a standard {@link CustomToast} that just contains a text view.
     */
    public static void showText(Context context, CharSequence text, int duration) {
        CustomToast.makeText(context, text, duration).show();
    }

    /**
     * Show a standard {@link CustomToast} that just contains a text view with the
     * text from a resource.
     */
    public static void showText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        CustomToast.makeText(context, resId, duration).show();
    }

    /**
     * Show a standard {@link CustomToast} that just contains a text view. Duration
     */
    public static void showText(Context context, CharSequence text) {
        CustomToast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show a standard {@link CustomToast} that just contains a text view with the
     * text from a resource. Duration defaults to length short
     *
     * @param context The context to use. Usually your {@link android.app.Application}
     *                or {@link android.app.Activity} object.
     * @param resId   The resource id of the string resource to use. Can be formatted
     *                text.
     * @throws Resources.NotFoundException if the resource can't be found.
     */
    public static void showText(Context context, int resId)
            throws Resources.NotFoundException {
        CustomToast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }


    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this. Normally view will disappear on
     * its own after the appropriate duration.
     */
    public void cancel() {
        internalToast.cancel();
    }

    /**
     * Show the view for the specified duration. By default, this method cancels
     * any current notification to immediately display the new one. For
     * conventional {@link Toast#show()} queueing behaviour, use method
     * {@link #show(boolean)}.
     *
     * @see #show(boolean)
     */
    public void show() {
        show(true);
    }

    /**
     * Show the view for the specified duration. This method can be used to
     * cancel the current notification, or to queue up notifications.
     *
     * @param cancelCurrent <code>true</code> to cancel any current notification and replace
     *                      it with this new one
     * @see #show()
     */
    public void show(boolean cancelCurrent) {
        // cancel current
        if (cancelCurrent && (globalBoast != null)) {
            globalBoast.cancel();
        }

        // save an instance of this current notification
        globalBoast = this;

        internalToast.show();
    }

}

