package com.digvijaysingh.kiwi.Utils;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;

public class Utils {
    public static boolean isNetworkConnected(Context mContext) {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) mContext.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }

        return false;
    }
}
