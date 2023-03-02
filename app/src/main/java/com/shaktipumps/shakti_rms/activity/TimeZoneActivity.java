package com.shaktipumps.shakti_rms.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.bean.TimeZoneName;
import com.shaktipumps.shakti_rms.searchlist.SearchTimeZoneListViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneActivity extends AppCompatActivity {
    Context context ;
    private Toolbar mToolbar;
    SharedPreferences pref ;
    ListView list;
    EditText editsearch;
    SearchTimeZoneListViewAdapter adapter;
    ArrayList<TimeZoneName> arraylist ;
    TimeZoneName timeZoneName ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zone);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select time zone");

        context  = this;
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);



        editsearch = (EditText) findViewById(R.id.search);

        list = (ListView) findViewById(R.id.listview);
        arraylist = new ArrayList<TimeZoneName>();




        SetTimeZone();




        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void  SetTimeZone() {



        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        TimeZone.getDefault().getDisplayName();
        TimeZone.getDefault().getID();
//
//

        String [] timezo =TimeZone.getAvailableIDs();

//        Log.d("Time_zone","=" +  timezo[1] +"--"+tz.getDisplayName());

//        for (int i = 0; i < timezo.length; i++)
//        {
        //Log.d("Time_zone","=" +  timezo[i] +"--"+TimeZone.getTimeZone( timezo[i]).getDisplayName(false,TimeZone.LONG));
       // Log.d("Time_zone","=" +  TimeZone.getDefault().getID() +"--"+TimeZone.getTimeZone( TimeZone.getDefault().getID()).getDisplayName(false,TimeZone.LONG));



//        }



//        String[] separated ;
//
//
//
//        String city_name ;
//
//        try{
//            arraylist.clear();
//
//            for (int i = 0; i < timezo.length; i++)
//        {
//
//            separated =  timezo[i].split("/");
//            city_name  = separated[1];
//
//
//            timeZoneName = new TimeZoneName();
//
//            timeZoneName.setTimezone_city( city_name);
//            timeZoneName.setTimezone_long(TimeZone.getTimeZone( timezo[i]).getDisplayName(false,TimeZone.LONG));
//            timeZoneName.setTimezone_short(TimeZone.getTimeZone( timezo[i]).getDisplayName(false,TimeZone.SHORT));
//
//            arraylist.add(timeZoneName );
//            city_name = "";
//        }
//
//            adapter = new SearchTimeZoneListViewAdapter(context,arraylist);
//
//            //Log.d("city_name12", ""+arraylist.size());
//
//
//            list.setAdapter(adapter);
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();  ;
//        }
//


        try {

            for (int i = 0; i < timezo.length; i++)
       {

            timeZoneName = new TimeZoneName();

            timeZoneName.setTimezone_city(timezo[i]);
            timeZoneName.setTimezone_long(TimeZone.getTimeZone( timezo[i]).getDisplayName(false,TimeZone.LONG));
            timeZoneName.setTimezone_short(TimeZone.getTimeZone( timezo[i]).getDisplayName(false,TimeZone.SHORT));

            arraylist.add(timeZoneName);


        }

            adapter = new SearchTimeZoneListViewAdapter(context,arraylist);
            list.setAdapter(adapter);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
