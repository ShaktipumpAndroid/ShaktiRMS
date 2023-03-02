package com.shaktipumps.shakti_rms.activity.ExcleView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

public class ExcleViewActivity extends AppCompatActivity {

    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    private Toolbar mToolbar;
    private Context mContext;

    private ProgressDialog progressDialog;
    private BaseRequest baseRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excle_view);


        mContext = this;

        initView();
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        baseRequest = new BaseRequest(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.action_verify_account);


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
    }
}
