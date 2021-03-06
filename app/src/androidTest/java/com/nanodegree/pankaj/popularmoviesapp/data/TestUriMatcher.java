package com.nanodegree.pankaj.popularmoviesapp.data;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

public class TestUriMatcher extends AndroidTestCase {
    public static final String LOG_TAG = TestUriMatcher.class.getSimpleName();
    private static final long TEST_MOVIE_ID = 49026L;


    private static final Uri TEST_MOVIES_DIR = MovieContract.MovieEntries.CONTENT_URI;

    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();
        assertEquals("Error: The Movies URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIES_DIR), MovieProvider.MOVIES);
    }
}
