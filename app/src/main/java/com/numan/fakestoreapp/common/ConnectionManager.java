package com.numan.fakestoreapp.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Prototype on 12/9/16.
 */
public final class ConnectionManager {

    private ConnectionManager() {
    }

    /**
     * To check if the device is connected to an internet connection.
     * usually to check internet before doing any net related tasks e.g. API calling
     *
     * @param context for the application context.
     * @return returns boolean value as result.
     */
    public static boolean isOnline(final Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
