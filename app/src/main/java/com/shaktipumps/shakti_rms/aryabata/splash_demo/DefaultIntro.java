package com.shaktipumps.shakti_rms.aryabata.splash_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.Login_Activity;


public final class DefaultIntro extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro1));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro2));
        addSlide(SampleSlide.newInstance(R.layout.fragment_intro3));


        showStatusBar(true);

        setDepthAnimation();

    }


    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        loadMainActivity();
    }

    private void loadMainActivity() {
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
    }

    public void getStarted(View v) {
        loadMainActivity();
    }
}
