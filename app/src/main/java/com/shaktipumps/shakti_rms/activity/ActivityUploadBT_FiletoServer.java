package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;

public class ActivityUploadBT_FiletoServer extends AppCompatActivity {

    private Context mContext;

    private TextView txt15thDaysBTNID;
    private TextView txt5thYearBTNID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_bt__fileto_server);
        mContext = this;
        initView();
    }

    private void initView() {

        txt15thDaysBTNID = findViewById(R.id.txt15thDaysBTNID);
        txt5thYearBTNID = findViewById(R.id.txt5thYearBTNID);


        txt15thDaysBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txt5thYearBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
