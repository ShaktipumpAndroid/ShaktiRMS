package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.fragment.FragmentWalkTroughone;
import com.shaktipumps.shakti_rms.fragment.FragmentWalkTroughthree;
import com.shaktipumps.shakti_rms.fragment.FragmentWalkTroughtwo;
import com.shaktipumps.shakti_rms.other.CustomViewPager;
import com.shaktipumps.shakti_rms.retrofit.BaseRequest;

import de.hdodenhof.circleimageview.CircleImageView;

public class WalthroughActivity extends AppCompatActivity {


    private ViewPagerAdapter adapter;
    // private ViewPager viewPager;
    private CustomViewPager viewPager;

    private RelativeLayout rlvPrevViewID, rlvNextViewID;
    private TextView txtPReviousID, txtNextID;

    private  int PagePositionIndex = 0;

    private String TAG ;

private Context mContext = null;
private String mCheckLogin = null,mUserID= null, mDeviceToken= null;
    private BaseRequest baseRequest;

    private CircleImageView imgdotOneID, imgdotThreeID, imgdotTwoID;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walthrough);
        mContext = this;

        baseRequest =  new BaseRequest(this);


        initView();

    }

    private void initView() {

        rlvPrevViewID = (RelativeLayout) findViewById(R.id.rlvPrevViewID);
        rlvNextViewID = (RelativeLayout) findViewById(R.id.rlvNextViewID);
        txtPReviousID = (TextView) findViewById(R.id.txtPReviousID);
        imgdotOneID = (CircleImageView) findViewById(R.id.imgdotOneID);
        imgdotTwoID = (CircleImageView) findViewById(R.id.imgdotTwoID);
        imgdotThreeID = (CircleImageView) findViewById(R.id.imgdotThreeID);
        txtNextID = (TextView) findViewById(R.id.txtNextID);
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(0);

        /*PageListener pageListener = new PageListener();
        viewPager.setOnPageChangeListener(pageListener);
        viewPager.setCurrentItem(PagePositionIndex);*/

        if (viewPager != null) {

            if (adapter != null)
                adapter = null;
            //   adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            PageListener pageListener = new PageListener();
            viewPager.setOnPageChangeListener(pageListener);
            // viewPager.setCurrentItem(0);
            viewPager.setCurrentItem(PagePositionIndex);
        }


        rlvNextViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagePositionIndex = PagePositionIndex+1;
                if(PagePositionIndex > 0)
                {
                    // rlvPrevViewID.
                    changeButtonVisibility(true, 1.0f, rlvPrevViewID);
                }

                if(PagePositionIndex == 2)
                {
                    txtNextID.setText("Get Started");
                }

                if(PagePositionIndex > 2)
                {
                    PagePositionIndex  = 2;
                }
                PageListener pageListener = new PageListener();
                viewPager.setOnPageChangeListener(pageListener);
                viewPager.setCurrentItem(PagePositionIndex);

                if(PagePositionIndex == 0)
                {
                    imgdotOneID.setBackgroundColor(getResources().getColor(R.color.white));
                    imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                else  if(PagePositionIndex == 1)
                {
                    imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.white));
                    imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                else
                {
                    imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.white));
                }

            }
        });

        rlvPrevViewID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PagePositionIndex = PagePositionIndex - 1;
                if(PagePositionIndex == -1)
                    PagePositionIndex = 0;

                if(PagePositionIndex == 0)
                {
                   // rlvPrevViewID.
                            changeButtonVisibility(true, 0.5f, rlvPrevViewID);
                }
                if(PagePositionIndex < 2)
                {
                    txtNextID.setText("Next");
                }

                if(PagePositionIndex == 0)
                {
                    imgdotOneID.setBackgroundColor(getResources().getColor(R.color.white));
                    imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                else  if(PagePositionIndex == 1)
                {
                    imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.white));
                    imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                }
                else
                {
                    imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                    imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.white));
                }
                PageListener pageListener = new PageListener();
                viewPager.setOnPageChangeListener(pageListener);
                viewPager.setCurrentItem(PagePositionIndex);
            }
        });

    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private int COUNT = 3;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    //fragment = callFrag(fragment);
                    fragment = new FragmentWalkTroughone();
                    break;
                case 1:
                    fragment = new FragmentWalkTroughtwo();
                    //fragment = callFrag(fragment);
                    break;
                case 2:
                    fragment = new FragmentWalkTroughthree();
                    // fragment = callFragGraph(fragment);
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "Tab " + (position + 1);
        }
    }


    private void changeButtonVisibility(boolean state, float alphaRate, RelativeLayout txtDoanloadFileBTNID) {
        txtDoanloadFileBTNID.setEnabled(state);
        txtDoanloadFileBTNID.setAlpha(alphaRate);
    }


    private class PageListener extends CustomViewPager.SimpleOnPageChangeListener {


        public void onPageSelected(int position) {
            Log.i(TAG, "page selected " + position);
            if (position == 0){
                try {


                    //mSwipePosition = 0;
                    //callOrderListAPI(mPageNumber = 1);
                    viewPager.setCurrentItem(0);

                    PagePositionIndex = 0;
                    if(PagePositionIndex == 0)
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.white));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    else  if(PagePositionIndex == 1)
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.white));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    else
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.white));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else  if (position == 1){
                try {


                    PagePositionIndex = 1;
                   // mSwipePosition = 1;
                    //callOrderListAPI(mPageNumber = 1);
                    viewPager.setCurrentItem(1);
                    if(PagePositionIndex == 0)
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.white));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    else  if(PagePositionIndex == 1)
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.white));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    else
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.white));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                try {


                   // mSwipePosition = 2;
                    //callOrderListAPI(mPageNumber = 1);
                    viewPager.setCurrentItem(2);

                    PagePositionIndex = 2;

                    if(PagePositionIndex == 0)
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.white));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    else  if(PagePositionIndex == 1)
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.white));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                    else
                    {
                        imgdotOneID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotTwoID.setBackgroundColor(getResources().getColor(R.color.gray));
                        imgdotThreeID.setBackgroundColor(getResources().getColor(R.color.white));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //currentPage = position;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }



}
