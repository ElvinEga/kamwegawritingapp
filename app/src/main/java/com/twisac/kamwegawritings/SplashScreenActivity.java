package com.twisac.kamwegawritings;
/**
 * Created by elvin Ambasa on 2/23/17.
 *
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;

/**
 * Activity for loading layout resources
 *  This activity is used to display splash layout as the app starts.Activity displays the layout resources fot hte user interface.
 * @author Elvin Ambasa
 * @version 1.0
 * @since 2/23/17
 */
public class SplashScreenActivity extends AppCompatActivity {
    private TextView nameTv;
    private RippleBackground rippleBackground;
    private SharedPreferences sharedPreferences;
    private static final int COUNTEND = 3000;
    private static final int COUNSTART = 1000;
    //SessionManager sessionManager;

    /**
     * Called when the activity is first created.
     * This is where you should do all of your normal static set up: create views, bind data to lists, etc.
     * This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
       // sessionManager = new SessionManager(SplashScreenActivity.this);

        //The ripple animation setup
        rippleBackground = (RippleBackground) findViewById(R.id.content);
        nameTv = (TextView) findViewById(R.id.textView1);
        rippleBackground.startRippleAnimation();

//        if (sessionManager.isLoggedIn()) {
//
//            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//            startActivity(intent);
//        } else {

            new CountDownTimer(COUNTEND, COUNSTART) {
                /**
                 * This method simply is called when the millisecond counter finishes to run.
                 *
                 * @param millisUntilFinished trigger when the milliseconds are reached.
                 *
                 */
                public void onTick(long millisUntilFinished) {
                    nameTv.setText("KamwegaWritings");
                }

                /**
                 * This method is triggered If an activity is paused or stopped, the system can drop the activity from memory by either asking it to finish, or simply killing its process. When it is displayed again to the user, it must be completely restarted and restored to its previous state.
                 *
                 */
                public void onFinish() {

                    startActvity();
                }
            }.start();

      //  }
    }

    /**
     * Called when the system is about to start resuming a previous activity.
     * This is typically used to commit unsaved changes to persistent data, stop animations and other things that may be consuming CPU, etc.
     * Implementations of this method must be very quick because the next activity will not be resumed until this method returns.
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    /**
     * The Activity can then start intent of LoginActivity or SplashActivity.
     * if Session is logged in it skips intro
     */
    private void startActvity() {
//     //skipping intro if true
//     if (sessionManager.isLoggedIn()) {
//
//         Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//         startActivity(intent);
//     } else {
//
//         Intent intent2 = new Intent(SplashScreenActivity.this, IntroActivity.class);
//         startActivity(intent2);
//     }
//
//     Intent intent2 = new Intent(SplashScreenActivity.this, IntroActivity.class);
//     startActivity(intent2);
// }

        Intent intent2 = new Intent(SplashScreenActivity.this, IntroActivity.class);
        startActivity(intent2);
    }
}