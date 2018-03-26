package com.example.lenovo.myapp2;

import android.app.ActivityOptions;
import android.content.Context;
import android.os.Bundle;

public class ActivityTransition {

    // Add right to left slide transition
    public static Bundle moveToNextAnimation(Context _context) {
        return ActivityOptions.makeCustomAnimation
                (_context, R.anim.animation_one, R.anim.animation_two).toBundle();
    }
}
