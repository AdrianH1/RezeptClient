package com.example.rezeptclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            if (action == ConnectivityManager.EXTRA_NO_CONNECTIVITY) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkInterfaceAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
