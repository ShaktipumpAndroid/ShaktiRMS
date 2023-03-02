package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.webservice.Constant;


public class LanguageChangeActivity extends AppCompatActivity {

    private Context mContext;
    private RelativeLayout rlvPortuguesViewID,rlvArabicViewID,rlvHindiViewID,rlvMarathiViewID,rlvEnglishViewID;
    private ImageView imgEnglishlcon,imgHindilcon, imgMarathilcon;
    private TextView txtApplyLanguagBTNID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_change);
        mContext = this;
        initView();
    }

    private void initView() {


        rlvHindiViewID = findViewById(R.id.rlvHindiViewID);
        rlvMarathiViewID = findViewById(R.id.rlvMarathiViewID);
        rlvEnglishViewID = findViewById(R.id.rlvEnglishViewID);

        imgEnglishlcon = findViewById(R.id.imgEnglishlcon);
        imgHindilcon = findViewById(R.id.imgHindilcon);
        imgMarathilcon = findViewById(R.id.imgMarathilcon);

        txtApplyLanguagBTNID = findViewById(R.id.txtApplyLanguagBTNID);

        if (SharedPreferencesUtil.getData(mContext, Constant.LANGUAGE_NAME_CODE) == null || SharedPreferencesUtil.getData(mContext, Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase(""))
        {
            imgEnglishlcon.setImageResource(R.drawable.ic_select_icon);
            imgHindilcon.setImageResource(R.drawable.ic_unselect_icon);
            imgMarathilcon.setImageResource(R.drawable.ic_unselect_icon);

            Constant.SELECTED_LANGUAGE_NEWSCREEN = "en";
            Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME = "Englisg";
        }
        else if (SharedPreferencesUtil.getData(mContext, Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase("en") )
        {
            imgEnglishlcon.setImageResource(R.drawable.ic_select_icon);
            imgHindilcon.setImageResource(R.drawable.ic_unselect_icon);
            imgMarathilcon.setImageResource(R.drawable.ic_unselect_icon);

            Constant.SELECTED_LANGUAGE_NEWSCREEN = "en";
            Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME = "Englisg";
           // Constant.setLocale(mContext, SharedPreferencesUtil.getData(mContext, Constant.LANGUAGE_NAME_CODE));
        }
        else if (SharedPreferencesUtil.getData(mContext, Constant.LANGUAGE_NAME_CODE).equalsIgnoreCase("hi") )
        {
            imgEnglishlcon.setImageResource(R.drawable.ic_unselect_icon);
            imgHindilcon.setImageResource(R.drawable.ic_select_icon);
            imgMarathilcon.setImageResource(R.drawable.ic_unselect_icon);

            Constant.SELECTED_LANGUAGE_NEWSCREEN = "hi";
            Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME = "Hindi";

        }
        else
        {
            imgEnglishlcon.setImageResource(R.drawable.ic_unselect_icon);
            imgHindilcon.setImageResource(R.drawable.ic_unselect_icon);
            imgMarathilcon.setImageResource(R.drawable.ic_select_icon);

            Constant.SELECTED_LANGUAGE_NEWSCREEN = "mr";
            Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME = "Marathi";

        }

        setClickListnersView();

        String mmCheckLogin_Welcom = SharedPreferencesUtil.getData(LanguageChangeActivity.this, Constant.USER_APP_OPEN_STATUS);
        Log.e("LanguageChangeActivity",mmCheckLogin_Welcom);

    }

    private void setClickListnersView() {

        rlvEnglishViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEnglishlcon.setImageResource(R.drawable.ic_select_icon);
                imgHindilcon.setImageResource(R.drawable.ic_unselect_icon);
                imgMarathilcon.setImageResource(R.drawable.ic_unselect_icon);

                Constant.SELECTED_LANGUAGE_NEWSCREEN = "en";
                Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME = "Englisg";

              //  Constant.setLocale(mContext, Constant.SELECTED_LANGUAGE_NEWSCREEN);

            }
        });

        rlvHindiViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEnglishlcon.setImageResource(R.drawable.ic_unselect_icon);
                imgHindilcon.setImageResource(R.drawable.ic_select_icon);
                imgMarathilcon.setImageResource(R.drawable.ic_unselect_icon);

                Constant.SELECTED_LANGUAGE_NEWSCREEN = "hi";
                Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME = "Hindi";

            }
        });

        rlvMarathiViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEnglishlcon.setImageResource(R.drawable.ic_unselect_icon);
                imgHindilcon.setImageResource(R.drawable.ic_unselect_icon);
                imgMarathilcon.setImageResource(R.drawable.ic_select_icon);

                Constant.SELECTED_LANGUAGE_NEWSCREEN = "mr";
                Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME = "Marathi";

            }
        });


        txtApplyLanguagBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SharedPreferencesUtil.setData(mContext, Constant.USER_APP_OPEN_STATUS, "true");
                SharedPreferencesUtil.setData(mContext, Constant.LANGUAGE_NAME_SAVE, Constant.SELECTED_LANGUAGE_NEWSCREEN_NAME);
                SharedPreferencesUtil.setData(mContext, Constant.LANGUAGE_NAME_CODE, Constant.SELECTED_LANGUAGE_NEWSCREEN);
                Constant.setLocale(mContext, Constant.SELECTED_LANGUAGE_NEWSCREEN);
                Intent mIntent = new Intent(mContext, MainActivity.class);
               startActivity(mIntent);
                finish();

            }
        });

    }

    @Override
    protected void onDestroy() {

        System.gc();

        super.onDestroy();
    }
}
