package com.shaktipumps.shakti_rms.aryabata.splash_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.Login_Activity;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen1);


        Thread logoTimer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                    startActivity(new Intent(SplashScreen.this, Login_Activity.class));
                }
                finish();

            }
        };
        logoTimer.start();
    }

}
