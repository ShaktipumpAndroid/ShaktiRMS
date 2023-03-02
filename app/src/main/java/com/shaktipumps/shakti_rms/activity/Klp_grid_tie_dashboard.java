package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;

public class Klp_grid_tie_dashboard extends AppCompatActivity {
    private Context mContext ;
    private RelativeLayout rlvUserBottomViewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klp_grid_tie_dashboard);
        mContext = this;
        initView();
    }

    private void initView() {

        rlvUserBottomViewID = findViewById(R.id.rlvUserBottomViewID);

    }
}