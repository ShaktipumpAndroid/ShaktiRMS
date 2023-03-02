package com.shaktipumps.shakti_rms.activity.GRID;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.AddDevice;

public class AddPlantAndDeviceOPtion extends AppCompatActivity {

    private Context mContextt;
    private LinearLayout lvlAddPlantButtonViewID, lvlAddDeviceButtonViewID;
    private RelativeLayout rlvBackViewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_and_device_option);
        mContextt =  this;

        initView();
    }

    private void initView() {
        rlvBackViewID  = findViewById(R.id.rlvBackViewID);
        lvlAddPlantButtonViewID  = findViewById(R.id.lvlAddPlantButtonViewID);
        lvlAddDeviceButtonViewID  = findViewById(R.id.lvlAddDeviceButtonViewID);



        cleckEventAll();

       // callGetPlantListCheckAPI();


    }

    private void cleckEventAll() {


        rlvBackViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        lvlAddPlantButtonViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mContextt, AddPlantAndDevice.class);
                startActivity(mIntent);
               // finish();

            }
        });

        lvlAddDeviceButtonViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContextt, AddDevice.class);
                startActivity(mIntent);
               // finish();


            }
        });





    }






}
