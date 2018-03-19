package com.example.lenovo.myapp2;

import android.content.Context;

/**
 * @author Girish D Mane (gmane@birdzi.com)
 *         Created on 3/19/2018
 *         Last modified on 3/19/2018
 */

public class GetResponses_ {

    public static String getStatusMessage(int status, Context context) {
        switch (status) {
            case 1:
                return context.getResources().getString(R.string.successful);
            case 2:
                return context.getResources().getString(R.string.failed);
            case 11:
                return context.getResources().getString(R.string.network_error);
            case 12:
                return context.getResources().getString(R.string.internal_error);
            default:
                return "";
        }
    }
}
