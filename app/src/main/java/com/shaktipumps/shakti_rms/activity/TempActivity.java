package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;

import java.util.ArrayList;
import java.util.List;

public class TempActivity extends AppCompatActivity {

List<String> imgList = new ArrayList<>();
List<String> nameList = new ArrayList<>();
    int[] imaArray = {R.drawable.banner,R.drawable.banner,R.drawable.banner,R.drawable.banner,R.drawable.banner};
    String[] srtNameArray = {"R.drawable.banner","R.drawable.banner","R.drawable.banner","R.drawable.banner","R.drawable.banner"};

    private Context mContext =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        mContext = this;
        initView();
    }

    private void initView() {

        imgList = new ArrayList<>();
         nameList = new ArrayList<>();

        for (int i = 0; i <imaArray.length ; i++) {
            imgList.add(""+imaArray[i]);
        }

        for (int j = 0; j <srtNameArray.length ; j++) {
            nameList.add(srtNameArray[j]);
        }


    }

}