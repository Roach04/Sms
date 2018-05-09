package com.project.sms.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    //declare.
    TextView splash;

    Typeface typeface;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //initialize the text view.
        splash = (TextView) findViewById(R.id.textViewSplash);

        //assign the fonts.
        typeface = Typeface.createFromAsset(getAssets(), "fonts/SansationLight.ttf");

        //apply the fonts.
        splash.setTypeface(typeface);

        //load the animation file.
        animation = AnimationUtils.loadAnimation(this, R.anim.splash);

        //assign the animation to the text view.
        splash.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //kill the splash activity.
                finish();

                //re direct accordingly to the main activity.
                startActivity(new Intent(getApplicationContext(), Test.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
