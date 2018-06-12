package com.twisac.kamwegawritings;

/**
 * Created by simba on 2/23/17.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.twisac.kamwegawritings.intro.AppIntroFragment;

public class IntroActivity extends AppIntro {
    // Please DO NOT override onCreate. Use init
    SharedPreferences sharedPreferences;
    @Override
    public void init(Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      // sharedPreferences.getBoolean("intro",false);

       if(sharedPreferences.getBoolean("intro",false)){
           Intent i = new Intent(getApplicationContext(), MainActivity.class);
           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(i);
           finish();
       }

        //adding the three slides for introduction app you can ad as many you needed
        addSlide(AppIntroFragment.newInstance(R.layout.app_intro1));
        addSlide(AppIntroFragment.newInstance(R.layout.app_intro2));
        addSlide(AppIntroFragment.newInstance(R.layout.app_intro3));

        // Show and Hide Skip and Done buttons
        showStatusBar(false);
        showSkipButton(true);



        // Turn vibration on and set intensity
        // You will need to add VIBRATE permission in Manifest file
        setVibrate(false);
      //  setVibrateIntensity(30);

        //Add animation to the intro slider
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {
        // Do something here when users click or tap on Skip button.
       // Toast.makeText(getApplicationContext(),"Skipped", Toast.LENGTH_SHORT).show();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("intro",true);
        editor.apply();
        editor.commit();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onNextPressed() {
        // Do something here when users click or tap on Next button.
    }

    @Override
    public void onDonePressed() {
        // Do something here when users click or tap tap on Done button.

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("intro",true);
        editor.apply();
        editor.commit();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }

    @Override
    public void onSlideChanged() {
        // Do something here when slide is changed
    }
}