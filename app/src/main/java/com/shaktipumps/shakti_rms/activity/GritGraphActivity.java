package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shaktipumps.shakti_rms.R;

public class GritGraphActivity extends AppCompatActivity {

    private Context mContext;

    private RelativeLayout rlvDayGharpID, rlvMonthGharpID, rlvYearGharpID;


    private RelativeLayout rlvDayGharpViewContainerID, rlvMonthGharpViewContainerID, rlvYearGharpViewContainerID;

    private TextView txtDayGharpID, txtMonthGharpID, txtYearGharpID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grit_graph);
        mContext = this;
        initView();
    }

    private void initView() {

        rlvDayGharpID = (RelativeLayout) findViewById(R.id.rlvDayGharpID);
        rlvMonthGharpID = (RelativeLayout) findViewById(R.id.rlvMonthGharpID);
        rlvYearGharpID = (RelativeLayout) findViewById(R.id.rlvYearGharpID);


        rlvDayGharpViewContainerID = (RelativeLayout) findViewById(R.id.rlvDayGharpViewContainerID);
        rlvMonthGharpViewContainerID = (RelativeLayout) findViewById(R.id.rlvMonthGharpViewContainerID);
        rlvYearGharpViewContainerID = (RelativeLayout) findViewById(R.id.rlvYearGharpViewContainerID);

        txtDayGharpID = (TextView) findViewById(R.id.txtDayGharpID);
        txtMonthGharpID = (TextView) findViewById(R.id.txtMonthGharpID);
        txtYearGharpID = (TextView) findViewById(R.id.txtYearGharpID);

        rlvDayGharpViewContainerID.setVisibility(View.VISIBLE);
        rlvMonthGharpViewContainerID.setVisibility(View.GONE);
        rlvYearGharpViewContainerID.setVisibility(View.GONE);


        setClickEventLis();

    }

    private void setClickEventLis() {

        rlvDayGharpID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlvDayGharpID.setBackgroundColor(getResources().getColor(R.color.blue_fb));
                rlvMonthGharpID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvYearGharpID.setBackgroundColor(getResources().getColor(R.color.gray_btn));


                rlvDayGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.blue_fb));
                rlvMonthGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvYearGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.gray_btn));

                rlvDayGharpViewContainerID.setVisibility(View.VISIBLE);
                rlvMonthGharpViewContainerID.setVisibility(View.GONE);
                rlvYearGharpViewContainerID.setVisibility(View.GONE);





            }
        });
        rlvMonthGharpID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlvDayGharpID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvMonthGharpID.setBackgroundColor(getResources().getColor(R.color.green));
                rlvYearGharpID.setBackgroundColor(getResources().getColor(R.color.gray_btn));

                rlvDayGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvMonthGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.green));
                rlvYearGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.gray_btn));

                rlvDayGharpViewContainerID.setVisibility(View.GONE);
                rlvMonthGharpViewContainerID.setVisibility(View.VISIBLE);
                rlvYearGharpViewContainerID.setVisibility(View.GONE);

            }
        });
        rlvYearGharpID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlvDayGharpID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvMonthGharpID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvYearGharpID.setBackgroundColor(getResources().getColor(R.color.red));

                rlvDayGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvMonthGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.gray_btn));
                rlvYearGharpViewContainerID.setBackgroundColor(getResources().getColor(R.color.red));

                rlvDayGharpViewContainerID.setVisibility(View.GONE);
                rlvMonthGharpViewContainerID.setVisibility(View.GONE);
                rlvYearGharpViewContainerID.setVisibility(View.VISIBLE);


            }
        });

    }


}
