package com.example.lenovo.myapp2;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * @author Girish D Mane (gmane@birdzi.com)
 *         Created on 3/19/2018
 *         Last modified on 3/19/2018
 */

class SnackResponse {

    static void success(String message, Activity activity) {
        Snackbar snackbar = Snackbar
                .make(activity.getWindow().getDecorView().getRootView(),
                        message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(activity.getApplicationContext().getResources().getColor(R.color.green));
        snackbar.show();
    }

    static void failed(String message, Activity activity) {
        Snackbar snackbar = Snackbar
                .make(activity.getWindow().getDecorView().getRootView(),
                        message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(activity.getApplicationContext().getResources().getColor(R.color.red));
        snackbar.show();
    }
}
