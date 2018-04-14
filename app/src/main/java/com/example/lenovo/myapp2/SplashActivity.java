package com.example.lenovo.myapp2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * @author Girish D. Mane gmane@birdzi.com
 * created on 4/14/18
 * last modified on 4/14/18
 * All rights reserved by Birdzi Inc
 */
public class SplashActivity extends AppCompatActivity {

    private static final int DURATION = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.splash_activity_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Animation animation = fadeIn(DURATION);
        animation.setAnimationListener(animationListener);

        findViewById(R.id.splash_icon).startAnimation(animation);
        findViewById(R.id.splash_text).startAnimation(animation);

        HomePreferences.initialize(getApplicationContext());
    }

    private void moveNext(Intent nextIntent) {
        startActivity(nextIntent, ActivityTransition.moveToNextAnimation(getApplicationContext()));
        finish();
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (HomePreferences.contains("is_login")) {
                        if (HomePreferences.get("is_login").equals("1")) {
                            moveNext(new Intent(getApplicationContext(), VoiceActivity.class));
                        } else {
                            moveNext(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    } else {
                        moveNext(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                }
            }, 1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public static Animation fadeIn(long duration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator()); //add this
        fadeIn.setRepeatCount(0);
        fadeIn.setDuration(duration);
        return fadeIn;
    }


}
