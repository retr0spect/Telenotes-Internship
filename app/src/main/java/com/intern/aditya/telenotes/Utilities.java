package com.intern.aditya.telenotes;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Aditya on 3/23/2016.
 */
public class Utilities {

    public static boolean isInternetAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }
}
