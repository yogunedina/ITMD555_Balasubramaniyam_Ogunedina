package com.arthi.moviebooking;

import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {  //import android.os.Handler;

            @Override
            public void run () {
                Intent i = new Intent(Splash.this, RegisterActivity.class);
                startActivity(i);
                //invoke second activity.

                finish();
            }
        }, 5000); //set sleep mode for 5 secs
    }
}
