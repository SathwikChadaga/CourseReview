package com.mobops.chadaga.coursereview.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.mobops.chadaga.coursereview.Databases.DatabaseForSearch;
import com.mobops.chadaga.coursereview.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import java.util.ArrayList;

// Splash screen timer
//THIS CLASS USES A THIRD PARTY LIBRARY


public class SplashActivity extends AwesomeSplash {

    //DO NOT OVERRIDE onCreate()!
    //if you need to start some services do it in initSplash()!


    private static int SPLASH_TIME_OUT = 500;

    @Override
    public void initSplash(ConfigSplash configSplash) {

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("");
        configSplash.setAnimTitleDuration(0);

    }

    @Override
    public void animationsFinished() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> number = new ArrayList<String>();
                int cnt = 0;

                DatabaseForSearch db2 = new DatabaseForSearch(getApplicationContext());
                Cursor cursor = db2.getCourseMatches("*", null, 0);

                if (cursor == null) Log.i("Cursor: ", "NULL :P");
                else if (cursor.moveToFirst()) {
                    do {
                        String name1 = cursor.getString(1);
                        String number1 = cursor.getString(0);
                        name.add(name1);
                        number.add(number1);
                        cnt++;
                    } while (cursor.moveToNext());
                }

                i.putStringArrayListExtra("NAME_LIST", name);
                i.putStringArrayListExtra("NUMBER_LIST", number);
                i.putExtra("COUNT", cnt);

                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
