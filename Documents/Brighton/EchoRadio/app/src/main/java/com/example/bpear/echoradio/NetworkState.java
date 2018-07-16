package com.example.bpear.echoradio;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by bpear on 6/19/2018.
 */

public class NetworkState {
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
