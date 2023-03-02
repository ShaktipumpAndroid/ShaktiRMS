package com.shaktipumps.shakti_rms.aryabata;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatSpinner;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.aryabata.classes.EditTextDecimalFilter;
import com.shaktipumps.shakti_rms.aryabata.classes.SensorMode_Data;
import com.shaktipumps.shakti_rms.aryabata.settings.SettingsActivity;
import com.shaktipumps.shakti_rms.aryabata.splash_demo.CustomShowcaseView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class Parameters_GetSet_Activity extends AppCompatActivity implements View.OnClickListener {

    Resources resources;

    MaterialFancyButton btnpumpget;
    MaterialFancyButton btnpumpset;
    TextView txtpumptime;
    TextView txtpumpunits;
    EditText editpumpvariety;
    TextView txtpumpresp;
    MaterialFancyButton btnratedcurrentget;
    MaterialFancyButton btnratedcurrentset;
    TextView txtratedcurrenttime;
    EditText editratedcurrent;
    TextView txtratedcurrentresp;
    MaterialFancyButton btolcurrentget;
    MaterialFancyButton btnolcurrentset;
    TextView txtolcurrenttime;
    TextView txtolcurrentunits;
    EditText editolcurrent;
    TextView txtolcurrentresp;
    MaterialFancyButton btnolshorttimeget;
    MaterialFancyButton btnolshorttimeset;
    TextView txtolshorttimetime;
    TextView txtolshorttimeunits;
    EditText editolshorttime;
    TextView txtolshorttimeresp;
    MaterialFancyButton btnautomanget;
    MaterialFancyButton btnautomanset;
    TextView txtautomantime;
    TextView txtautomanunits;
    Switch switchautoman;
    TextView txtautomanresp;
    MaterialFancyButton btndrypowersetget, getdrypowersetbutton1,getdrypowersetbutton2;
    MaterialFancyButton btndrypowersetset, setdrypowersetbutton1,setdrypowersetbutton2;
    TextView txtdrypowersettime;
    TextView txtdrypowersettime1;
    TextView txtdrypowersettime2;
    TextView txtdrypowersetunits , txtdrypowersetunits1;
    EditText editdrypowerset, editdrypowerset1, editdrypowerset2;
    TextView txtdrypowersetresp;
    TextView txtdrypowersetresp1;
    TextView txtdrypowersetresp2;
    MaterialFancyButton btndryrunenablordisableget;
    MaterialFancyButton btndryrunenablordisableset;
    TextView txtdryrunenablordisabletime;
    TextView txtdryrunenablordisableunits;
    Switch switchdryrunenablordisable;
    TextView txtdryrunenablordisableresp;
    MaterialFancyButton btndryrunrestarttimeget;
    MaterialFancyButton btndryrunrestarttimeset;
    TextView txtdryrunrestarttimetime;
    TextView txtdryrunrestarttimeunits;
    EditText editdryrunrestarttime;
    TextView txtdryrunrestarttimeresp;
    MaterialFancyButton btndryrunrestartenableodisableget;
    MaterialFancyButton btndryrunrestartenableodisableset;
    TextView txtdryrunrestartenableodisabletime;
    TextView txtdryrunrestartenableodisableunits;
    Switch switchdryrunrestartenableodisable;
    TextView txtdryrunrestartenableodisableresp;
    MaterialFancyButton btnuvtripsetget;
    MaterialFancyButton btnuvtripsetset;
    TextView txtuvtripsettime;
    TextView txtuvtripsetunits;
    EditText edituvtripset;
    TextView txtuvtripsetresp;
    MaterialFancyButton btnovtripget;
    MaterialFancyButton btnovtripset;
    TextView txtovtriptime;
    TextView txtovtripunits;
    EditText editovtrip;
    TextView txtovtripresp;
    MaterialFancyButton btnontimeget;
    MaterialFancyButton btnontimeset;
    TextView txtontimetime;
    TextView txtontimeunits;
    EditText editontime;
    TextView txtontimeresp;


    MaterialFancyButton btnstarttimeget;
    MaterialFancyButton btnstarttimeset;
    TextView txtstarttimetime;
    TextView txtstarttimeunits;
    EditText editstarttime;
    TextView txtstarttimeresp;


    MaterialFancyButton btnpumpheadget;
    MaterialFancyButton btnpumpheadset;
    TextView txtpumpheadtime;
    TextView txtpumpheadunits;
    EditText editpumphead;
    TextView txtpumpheadresp;
    MaterialFancyButton btnmotorconfigget;
    MaterialFancyButton btnmotorconfigset;
    TextView txtmotorconfigtime;
    TextView txtmotorconfigunits;
    EditText editmotorconfig;
    TextView txtmotorconfigresp;
    MaterialFancyButton btndwelltimeget;
    MaterialFancyButton btndwelltimeset;
    TextView txtdwelltime;
    TextView txtdwelltimeunits;
    EditText editdwelltime;
    TextView txtdwellresp;
    MaterialFancyButton btnresetallenergyget;
    MaterialFancyButton btnresetallenergyset;
    TextView txtresetallenergytime;
    TextView txtresetallenergyunits;
    TextView txtresetallenergyresp;
    MaterialFancyButton btnunbalancecurrentget;
    MaterialFancyButton btnunbalancecurrentset;
    TextView txtunbalancecurrenttime;
    TextView txtunbalancecurrentunits;
    EditText editunbalancecurrent;
    TextView txtunbalancecurrentresp;
    MaterialFancyButton btnvoltageunbalget;
    MaterialFancyButton btnvoltageunbalset;
    TextView txtvoltageunbaltime;
    TextView txtvoltageunbalunits;
    EditText editvoltageunbal;
    TextView txtvoltageunbalresp;
    MaterialFancyButton btnmaxstartorstopget;
    MaterialFancyButton btnmaxstartorstopset;
    TextView txtmaxstartorstoptime;
    TextView txtmaxstartorstopunits;
    EditText editmaxstartorstop;
    TextView txtmaxstartorstopresp;
    MaterialFancyButton btnenergylogget;
    MaterialFancyButton btnenergylogset;
    TextView txtenergylogtime;
    TextView txtenergylogunits;
    EditText editenergylog;
    TextView txtenergylogresp;
    MaterialFancyButton btnoperatingfreqget;
    MaterialFancyButton btnoperatingfreqset;
    TextView txtoperatingfreqtime;
    TextView txtoperatingfrequnits;
    TextView txtoperatingfreqresp;
    Switch switchresetallenergy, switoperatingfreqency, switchsettodefault;

    MaterialFancyButton getsensormode;
    MaterialFancyButton setsensormode;
    TextView txtsensormodetime;
    TextView txttxtsensormodeunits;
    AppCompatSpinner sensormodespinner;
    TextView txtsensormoderesp;

    MaterialFancyButton getsensorpolaritybutton;
    MaterialFancyButton setsensorpolaritybutton;
    TextView txtsensorpolaritytime;
    TextView txtsensorpolarityunits;
    EditText editsensorpolarity;
    TextView txtsensorpolarityresp;


    MaterialFancyButton btnsoftstoptimeget, btnsoftstoptimeset;
    TextView txtsoftstoptimetime;
    TextView txtsoftstoptimeunits, txtsoftstoptimeresp;
    EditText editsoftstoptime;


    MaterialFancyButton btngroundfaultcurrentget;
    MaterialFancyButton btngroundfaultcurrentset, btnsettodefaultget, setsettodefaultbutton;
    TextView txtgroundfaultcurrenttime;
    TextView txtgroundfaultcurrentunits;
    EditText editgroundfaultcurrent;
    TextView txtgroundfaultcurrentresp, txtsettodefaulttime, txtsettodefault, txtsettodefaultresp;

    MaterialFancyButton getflowcalibrationbutton;
    EditText editflowcalibration;
    TextView txtflowcalibrationunits;
    MaterialFancyButton setflowcalibrationbutton;
    TextView txtflowcalibrationtime;
    TextView txtflowcalibrationresp;


    ArrayAdapter<String> sensorMode_dataArrayAdapter;

    BluetoothSocket btSocket = null;

    ProgressDialog pd;
    String address = null;
    String name = null;
    private ShowcaseView showcaseView;
    private ShowcaseView showcaseView1;
    private int counter = 0;
    private Context CURRENT_CONTEXT;

    private ImageView imgBackBTNID;
    private ImageView imgRefreshBTNID;
    private ImageView imgSettingBTNID;
    private TextView txtTopHEadingID;

    int clientid = 0;
    SharedPreferences pref;

    private LinearLayout lvlRatedMainViewID, lvlOLPercentMainViewID,
            lvlOverCurrentMaxMainViewID, lvlAutoManualMainViewID, lvlDryPowerSetMainViewID,
            lvlMaxLuxMainViewID, lvlMinLuxMainViewID, lvlDRYRUNEnableDMainViewID,
            lvlDRYRUNRestartTimeMainViewID,lvlDRYRUNRestartEnableDMainViewID,
            lvlUVTRIPSettingMainViewID,lvlUOVTRIPSettingMainViewID,lvlSTARTTimeMainViewID,
            lvlSoftSTOPTimeMainViewID, lvlONtimeMainViewID, lvlPumpHeadMainViewID,lvlIOLMultiplierMainViewID,
            lvlDwellTimeMainViewID, lvlUnbalanceCurrentMainViewID,
            lvlUnbalanceVoltageMainViewID, lvlSettoDefaultMainViewID,lvlEnergyLogIntervalMainViewID,
            lvlOperatingFrequencyMainViewID, lvlSensorModeMainViewID, lvlSensorPolarityMainViewID,
            lvlGroundFaultCurrentMainViewID, lvlFlowCalibrationMainViewID, lvlPumpVarietyMainViewID,lvlMaximumStartStopperhourMainViewID;



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (btSocket != null) {
            if (btSocket.isConnected()) {
                try {
                    btSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void setAlpha(float alpha, View... views) {
        for (View view : views) {
            view.setAlpha(alpha);
        }
    }


    @Override
    public void onClick(View v) {

        TextPaint title = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        title.setTextSize(getResources().getDimension(R.dimen.title_Font_Size));

        switch (counter) {
            case 0:
                showcaseView.setShowcase(new ViewTarget(btnpumpset), true);
                showcaseView.setContentTitle("Set Button");
                showcaseView.setContentText("Click To Set Parameters to device");
                setAlpha(0.4f, btnpumpget, btnpumpset);
                break;
            case 1:
                Resources resources = getResources();
                showcaseView.hide();
                setAlpha(1.0f, btnpumpget, btnpumpset);
                showcaseView1 = new ShowcaseView.Builder(this)
                        .setTarget(new ViewTarget(switchautoman))
                        .setOnClickListener(this)
                        .setContentTitlePaint(title)
                        .setStyle(R.style.CustomShowcaseTheme_red)
                        .setShowcaseDrawer(new CustomShowcaseView(resources.getDimension(R.dimen.custom_showcase_width1), resources.getDimension(R.dimen.custom_showcase_height), resources.getColor(R.color.colorAccentLight1), resources.getColor(R.color.colorAccentLight1))).build();
                showcaseView1.setContentTitle("Switch");
                showcaseView1.setContentText("Switch between values 1,0 Default is 0 (ie.) Disable");
                break;
            case 2:
                showcaseView1.setTarget(Target.NONE);
                showcaseView1.setContentTitle("Ready to Rock!");
                showcaseView1.setContentText("Go Ahead.!");
                showcaseView1.setButtonText("Close");
                setAlpha(0.4f, switchautoman);
                break;
            case 3:
                showcaseView1.hide();
                setAlpha(1.0f, switchautoman);
                break;
        }
        counter++;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_parameters);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        resources = getResources();



        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Pref", 0);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        address = sharedPref.getString(Bluetooth_DeviceLists_Activity.EXTRA_ADDRESS, "");
        name = sharedPref.getString(Bluetooth_DeviceLists_Activity.EXTRA_NAME, "");

        CURRENT_CONTEXT = this;
        pd = new ProgressDialog(CURRENT_CONTEXT);
        pd.setCancelable(false);

        btnpumpget = (MaterialFancyButton) findViewById(R.id.getpumpvarietybutton);
        showcaseView = new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(btnpumpget))
                .setOnClickListener(this)
                .setStyle(R.style.CustomShowcaseTheme2)
                .singleShot(4)
                .withMaterialShowcase()
                .build();
        showcaseView.setButtonText(getString(R.string.next));
        showcaseView.setContentTitle("Get Button");
        showcaseView.setContentText("Click To Get Parameters from device");




        btnpumpset = (MaterialFancyButton) findViewById(R.id.setpumpvarietybutton);
        txtpumptime = (TextView) findViewById(R.id.txtpumpvarietytime);
        txtpumpunits = (TextView) findViewById(R.id.txtpumpvariertunits);
        editpumpvariety = (EditText) findViewById(R.id.editpumpvariety);
        txtpumpresp = (TextView) findViewById(R.id.txtpumpvarietyresp);

        btnratedcurrentget = (MaterialFancyButton) findViewById(R.id.getratedcurrentbutton);
        btnratedcurrentset = (MaterialFancyButton) findViewById(R.id.setratedcurrentbutton);
        txtratedcurrenttime = (TextView) findViewById(R.id.txtratedcurrenttime);
        editratedcurrent = (EditText) findViewById(R.id.editratedcurrent);
        editratedcurrent.setFilters(new InputFilter[]{new EditTextDecimalFilter(3, 1)});

        txtratedcurrentresp = (TextView) findViewById(R.id.txtratedcurrentresp);

        btolcurrentget = (MaterialFancyButton) findViewById(R.id.getolcurrentbutton);
        btnolcurrentset = (MaterialFancyButton) findViewById(R.id.setolcurrentbutton);
        txtolcurrenttime = (TextView) findViewById(R.id.txtolcurrenttime);
        txtolcurrentunits = (TextView) findViewById(R.id.txtolcurrentunits);
        editolcurrent = (EditText) findViewById(R.id.editolcurrent);
        txtolcurrentresp = (TextView) findViewById(R.id.txtolcurrentresp);

        btnolshorttimeget = (MaterialFancyButton) findViewById(R.id.getolshorttimebutton);
        btnolshorttimeset = (MaterialFancyButton) findViewById(R.id.setolshorttimebutton);
        txtolshorttimetime = (TextView) findViewById(R.id.txtolshorttimetime);
        txtolshorttimeunits = (TextView) findViewById(R.id.txtolshorttimeunits);
        editolshorttime = (EditText) findViewById(R.id.editolshorttime);
        txtolshorttimeresp = (TextView) findViewById(R.id.txtolshorttimeresp);






        btnautomanget = (MaterialFancyButton) findViewById(R.id.getautoormanualbutton);
        btnautomanset = (MaterialFancyButton) findViewById(R.id.setautoormanualbutton);
        txtautomantime = (TextView) findViewById(R.id.txtautoormanualtime);
        txtautomanunits = (TextView) findViewById(R.id.txtautoormanualunits);
        switchautoman = (Switch) findViewById(R.id.switchautoormanual);
        txtautomanresp = (TextView) findViewById(R.id.txtautoormanualresp);

        btndrypowersetget = (MaterialFancyButton) findViewById(R.id.getdrypowersetbutton);
        getdrypowersetbutton1 = (MaterialFancyButton) findViewById(R.id.getdrypowersetbutton1);
        getdrypowersetbutton2 = (MaterialFancyButton) findViewById(R.id.getdrypowersetbutton2);
        btndrypowersetset = (MaterialFancyButton) findViewById(R.id.setdrypowersetbutton);
        setdrypowersetbutton1 = (MaterialFancyButton) findViewById(R.id.setdrypowersetbutton1);
        setdrypowersetbutton2 = (MaterialFancyButton) findViewById(R.id.setdrypowersetbutton2);
        txtdrypowersettime = (TextView) findViewById(R.id.txtdrypowersettime);
        txtdrypowersettime1 = (TextView) findViewById(R.id.txtdrypowersettime1);
        txtdrypowersettime2 = (TextView) findViewById(R.id.txtdrypowersettime2);
        txtdrypowersetunits = (TextView) findViewById(R.id.txtdrypowersetunits);
        txtdrypowersetunits1 = (TextView) findViewById(R.id.txtdrypowersetunits1);
        editdrypowerset = (EditText) findViewById(R.id.editdrypowerset);
        editdrypowerset1 = (EditText) findViewById(R.id.editdrypowerset1);
        editdrypowerset2 = (EditText) findViewById(R.id.editdrypowerset2);
        txtdrypowersetresp = (TextView) findViewById(R.id.txtdrypowersetresp);
        txtdrypowersetresp1 = (TextView) findViewById(R.id.txtdrypowersetresp1);
        txtdrypowersetresp2 = (TextView) findViewById(R.id.txtdrypowersetresp2);

        btndryrunenablordisableget = (MaterialFancyButton) findViewById(R.id.getdryrunenableordisablebutton);
        btndryrunenablordisableset = (MaterialFancyButton) findViewById(R.id.setdryrunenableordisablebutton);
        txtdryrunenablordisabletime = (TextView) findViewById(R.id.txtdryrunenableordisabletime);
        txtdryrunenablordisableunits = (TextView) findViewById(R.id.txtdryrunenableordisableunits);
        switchdryrunenablordisable = (Switch) findViewById(R.id.switchdryrunenableordisable);
        txtdryrunenablordisableresp = (TextView) findViewById(R.id.txtdryrunenableordisableresp);

        btndryrunrestarttimeget = (MaterialFancyButton) findViewById(R.id.getdryrunrestarttimebutton);
        btndryrunrestarttimeset = (MaterialFancyButton) findViewById(R.id.setdryrunrestarttimebutton);
        txtdryrunrestarttimetime = (TextView) findViewById(R.id.txtdryrunrestarttimetime);
        txtdryrunrestarttimeunits = (TextView) findViewById(R.id.txtdryrunrestarttimeunits);
        editdryrunrestarttime = (EditText) findViewById(R.id.editdryrunrestarttim);
        txtdryrunrestarttimeresp = (TextView) findViewById(R.id.txtdryrunrestarttimeresp);

        btndryrunrestartenableodisableget = (MaterialFancyButton) findViewById(R.id.getdryrunrestartenableordisablebutton);
        btndryrunrestartenableodisableset = (MaterialFancyButton) findViewById(R.id.setdryrunrestartenableordisablebutton);
        txtdryrunrestartenableodisabletime = (TextView) findViewById(R.id.txtdryrunrestartenableordisabletime);
        txtdryrunrestartenableodisableunits = (TextView) findViewById(R.id.txtdryrunrestartenableordisableunits);
        switchdryrunrestartenableodisable = (Switch) findViewById(R.id.switchdryrunrestartenableordisable);
        txtdryrunrestartenableodisableresp = (TextView) findViewById(R.id.txtdryrunrestartenableordisableresp);

        btnuvtripsetget = (MaterialFancyButton) findViewById(R.id.getuvtripsetbutton);
        btnuvtripsetset = (MaterialFancyButton) findViewById(R.id.setuvtripsetbutton);
        txtuvtripsettime = (TextView) findViewById(R.id.txtuvtripsettime);
        txtuvtripsetunits = (TextView) findViewById(R.id.txtuvtripsetunits);
        edituvtripset = (EditText) findViewById(R.id.edituvtripset);
        txtuvtripsetresp = (TextView) findViewById(R.id.txtuvtripsetresp);

        btnovtripget = (MaterialFancyButton) findViewById(R.id.getovtripsetbutton);
        btnovtripset = (MaterialFancyButton) findViewById(R.id.setovtripsetbutton);
        txtovtriptime = (TextView) findViewById(R.id.txtovtripsettime);
        txtovtripunits = (TextView) findViewById(R.id.txtovtripsetunits);
        editovtrip = (EditText) findViewById(R.id.editovtripset);
        txtovtripresp = (TextView) findViewById(R.id.txtovtripsetresp);

        btnstarttimeget = (MaterialFancyButton) findViewById(R.id.getstarttimebutton);
        btnstarttimeset = (MaterialFancyButton) findViewById(R.id.setstarttimebutton);
        txtstarttimetime = (TextView) findViewById(R.id.txtstarttimetime);
        txtstarttimeunits = (TextView) findViewById(R.id.txtstarttimeunits);
        editstarttime = (EditText) findViewById(R.id.editstarttimebutton);
        txtstarttimeresp = (TextView) findViewById(R.id.txtstarttimeresp);


        btnsoftstoptimeget = (MaterialFancyButton) findViewById(R.id.getsoftstoptimebutton);
        btnsoftstoptimeset = (MaterialFancyButton) findViewById(R.id.setsoftstoptimebutton);
        txtsoftstoptimetime = (TextView) findViewById(R.id.txtsoftstoptimetime);
        txtsoftstoptimeunits = (TextView) findViewById(R.id.txtsoftstoptimeunits);
        editsoftstoptime = (EditText) findViewById(R.id.editsoftstoptimebutton);
        txtsoftstoptimeresp = (TextView) findViewById(R.id.txtsoftstoptimeresp);


        btnontimeget = (MaterialFancyButton) findViewById(R.id.getontimebutton);
        btnontimeset = (MaterialFancyButton) findViewById(R.id.setontimebutton);
        txtontimetime = (TextView) findViewById(R.id.txtontimetime);
        txtontimeunits = (TextView) findViewById(R.id.txtontimeunits);
        editontime = (EditText) findViewById(R.id.editontimebutton);
        txtontimeresp = (TextView) findViewById(R.id.txtontimetimeresp);

        btnpumpheadget = (MaterialFancyButton) findViewById(R.id.getpumpheadbutton);
        btnpumpheadset = (MaterialFancyButton) findViewById(R.id.setpumpheadbutton);
        txtpumpheadtime = (TextView) findViewById(R.id.txtpumpheadtime);
        txtpumpheadunits = (TextView) findViewById(R.id.txtpumpheadunits);
        editpumphead = (EditText) findViewById(R.id.editpumphead);
        txtpumpheadresp = (TextView) findViewById(R.id.txtpumpheadtimeresp);

        btnmotorconfigget = (MaterialFancyButton) findViewById(R.id.getmotoconfigurationbutton);
        btnmotorconfigset = (MaterialFancyButton) findViewById(R.id.setmotoconfigurationbutton);
        txtmotorconfigtime = (TextView) findViewById(R.id.txtmotoconfigurationtime);
        txtmotorconfigunits = (TextView) findViewById(R.id.txtmotoconfiguration);
        editmotorconfig = (EditText) findViewById(R.id.editmotoconfiguration);
        txtmotorconfigresp = (TextView) findViewById(R.id.txtmotoconfigurationresp);

        btndwelltimeget = (MaterialFancyButton) findViewById(R.id.getdwelltimebutton);
        btndwelltimeset = (MaterialFancyButton) findViewById(R.id.setdwelltimebutton);
        txtdwelltime = (TextView) findViewById(R.id.txtdwelltimetime);
        txtdwelltimeunits = (TextView) findViewById(R.id.txtdwelltimeunits);
        editdwelltime = (EditText) findViewById(R.id.editdwelltime);
        txtdwellresp = (TextView) findViewById(R.id.txtdwelltimeresp);

        /*btnresetallenergyget = findViewById(R.id.getresetallenergybutton);
        btnresetallenergyset = findViewById(R.id.setresetallenergybutton);
        txtresetallenergytime = findViewById(R.id.txtresetallenergytime);
        txtresetallenergyunits = findViewById(R.id.txtresetallenergy);
        switchresetallenergy = findViewById(R.id.switchresetallenergy);
        txtresetallenergyresp = findViewById(R.id.txtresetallenergyresp);*/

        btnunbalancecurrentget = (MaterialFancyButton) findViewById(R.id.getunbalancebutton);
        btnunbalancecurrentset = (MaterialFancyButton) findViewById(R.id.setunbalancebutton);
        txtunbalancecurrenttime = (TextView) findViewById(R.id.txtunbalancetime);
        txtunbalancecurrentunits = (TextView) findViewById(R.id.txtunbalanceunits);
        editunbalancecurrent = (EditText) findViewById(R.id.editunbalance);
        txtunbalancecurrentresp = (TextView) findViewById(R.id.txtunbalanceresp);

        btnvoltageunbalget = (MaterialFancyButton) findViewById(R.id.getvoltageunbalancebutton);
        btnvoltageunbalset = (MaterialFancyButton) findViewById(R.id.setvoltageunbalancebutton);
        txtvoltageunbaltime = (TextView) findViewById(R.id.txtvoltageunbalancetime);
        txtvoltageunbalunits = (TextView) findViewById(R.id.txtvoltageunbalance);
        editvoltageunbal = (EditText) findViewById(R.id.editvoltageunbalance);
        txtvoltageunbalresp = (TextView) findViewById(R.id.txtvoltageunbalanceresp);

        btnsettodefaultget = (MaterialFancyButton) findViewById(R.id.getsettodefaultbutton);
        setsettodefaultbutton = (MaterialFancyButton) findViewById(R.id.setsettodefaultbutton);
        txtsettodefaulttime = (TextView) findViewById(R.id.txtsettodefaulttime);
        txtsettodefault = (TextView) findViewById(R.id.txtsettodefault);
        switchsettodefault = (Switch) findViewById(R.id.switchsettodefault);
        txtsettodefaultresp = (TextView) findViewById(R.id.txtsettodefaultresp);



        btnmaxstartorstopget = (MaterialFancyButton) findViewById(R.id.getmaxstartstopbutton);
        btnmaxstartorstopset = (MaterialFancyButton) findViewById(R.id.setmaxstartstopbutton);
        txtmaxstartorstoptime = (TextView) findViewById(R.id.txtmaxstartstoptime);
        txtmaxstartorstopunits = (TextView) findViewById(R.id.txtmaxstartstopunits);
        editmaxstartorstop = (EditText) findViewById(R.id.editmaxstartstop);
        txtmaxstartorstopresp = (TextView) findViewById(R.id.txtmaxstartstopresp);

        btnenergylogget = (MaterialFancyButton) findViewById(R.id.getenergylogbutton);
        btnenergylogset = (MaterialFancyButton) findViewById(R.id.setenergylogbutton);
        txtenergylogtime = (TextView) findViewById(R.id.txtenergylogbuttontime);
        txtenergylogunits = (TextView) findViewById(R.id.txtenergylogunits);
        editenergylog = (EditText) findViewById(R.id.editenergylog);
        txtenergylogresp = (TextView) findViewById(R.id.txtenergylogbuttonresp);

        btnoperatingfreqget = (MaterialFancyButton) findViewById(R.id.getoperatingfreqencybutton);
        btnoperatingfreqset = (MaterialFancyButton) findViewById(R.id.setoperatingfreqencybutton);
        txtoperatingfreqtime = (TextView) findViewById(R.id.txtoperatingfreqencytime);
        txtoperatingfrequnits = (TextView) findViewById(R.id.txtoperatingfreqenc);
        switoperatingfreqency = (Switch) findViewById(R.id.switoperatingfreqency);
        txtoperatingfreqresp = (TextView) findViewById(R.id.txtoperatingfreqencyresp);


        setsensormode = (MaterialFancyButton) findViewById(R.id.setsensormode);
        getsensormode = (MaterialFancyButton) findViewById(R.id.getsensormode);
        txtsensormodetime = (TextView) findViewById(R.id.txtsensoremodetime);
        sensormodespinner = (AppCompatSpinner) findViewById(R.id.sensormodespinner);
        txtsensormoderesp = (TextView) findViewById(R.id.txtsensormoderesp);

        getsensorpolaritybutton = (MaterialFancyButton) findViewById(R.id.getsensorpolaritybutton);
        setsensorpolaritybutton = (MaterialFancyButton) findViewById(R.id.setsensorpolaritybutton);
        txtsensorpolaritytime = (TextView) findViewById(R.id.txtsensorpolaritytime);
        editsensorpolarity = (EditText) findViewById(R.id.editsensorpolarity);
        txtsensorpolarityresp = (TextView) findViewById(R.id.txtsensorpolarityresp);

        btngroundfaultcurrentget = (MaterialFancyButton) findViewById(R.id.getgroundfaultcurrentbutton);
        btngroundfaultcurrentset = (MaterialFancyButton) findViewById(R.id.setgroundfaultcurrentbutton);
        txtgroundfaultcurrenttime = (TextView) findViewById(R.id.txtgroundfaultcurrenttime);
        txtgroundfaultcurrentunits = (TextView) findViewById(R.id.txtgroundfaultcurrentunits);
        editgroundfaultcurrent = (EditText) findViewById(R.id.editgroundfaultcurrent);
        editgroundfaultcurrent.setFilters(new InputFilter[]{new EditTextDecimalFilter(2, 1)});
        txtgroundfaultcurrentresp = (TextView) findViewById(R.id.txtgroundfaultcurrentresp);


        getflowcalibrationbutton = (MaterialFancyButton) findViewById(R.id.getflowcalibrationbutton);
        editflowcalibration = (EditText) findViewById(R.id.editflowcalibration);
        txtflowcalibrationunits = (TextView) findViewById(R.id.txtflowcalibrationunits);
        setflowcalibrationbutton = (MaterialFancyButton) findViewById(R.id.setflowcalibrationbutton);
        txtflowcalibrationtime = (TextView) findViewById(R.id.txtflowcalibrationtime);
        txtflowcalibrationresp = (TextView) findViewById(R.id.txtflowcalibrationresp);

        sensorMode_dataArrayAdapter = new ArrayAdapter<>(CURRENT_CONTEXT, R.layout.parameters_spinner_item, Objects.requireNonNull(SensorMode_Data.DefaultStrings()));
        sensorMode_dataArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        initView();

        sensormodespinner.setAdapter(sensorMode_dataArrayAdapter);

        btnpumpget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=32#", ":32=", "Pump Variety");
            }
        });
        btnpumpset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editpumpvariety.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 1 && cVal <= 9999) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 32=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Pump Variety");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Pump Variety' value should be between 0001-9999", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Pump Variety' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnratedcurrentget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=02#", ":02=", "Rated Current");
            }
        });
        btnratedcurrentset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editratedcurrent.getText().toString();
                if (getText.length() > 0) {
                    float cVal = Float.parseFloat(getText);
                    //if (cVal >= 0 && cVal <= 999.9)
                    if (cVal >= 1 && cVal <= 250)// change by dr. chinmay and santosh sir
                    {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 02=";
                        request += String.format("%04d", (int) (cVal * 10));
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Rated Current");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Rated Current' value should be between 1-45", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Rated Current' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btolcurrentget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=28#", ":28=", "OL Percent");
            }
        });
        btnolcurrentset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editolcurrent.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                   // if (cVal >= 30 && cVal <= 250)
                    if (cVal >= 80 && cVal <= 130)/// change by Dr. chinmay sir and santosh sir
                    {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 28=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "OL Percent");
                    } else {
                       // Toast.makeText(CURRENT_CONTEXT, "'OL Percent' value should be between 30-250", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CURRENT_CONTEXT, "'OL Percent' value should be between 80-130", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'OL Percent' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnolshorttimeget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=04#", ":04=", "OverCurrent Max");
            }
        });
        btnolshorttimeset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editolshorttime.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                   // if (cVal >= 150 && cVal <= 700)
                    if (cVal >= 450 && cVal <= 520)
                    {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 04=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "OverCurrent Max");
                    } else {
                       // Toast.makeText(CURRENT_CONTEXT, "'OverCurrent Max' value should be between 150-600", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CURRENT_CONTEXT, "'OverCurrent Max' value should be between 450-520", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'OverCurrent Max' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnautomanget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=05#", ":05=", "AUTO / Manual");
            }
        });
        btnautomanset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean state = switchautoman.isChecked();
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String request = "";
                if (!state) {
                    request = ":SET 05=0000#";
                } else {
                    request = ":SET 05=0001#";
                }
                new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "AUTO / Manual");
            }
        });

        btndrypowersetget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=06#", ":06=", "DRY Power set");
                //new BluetoothCommunicationGetActions().execute(":GETPARA=06#", ":06=", "Min Lux Set");
            }
        });

        getdrypowersetbutton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
              //  new BluetoothCommunicationGetActions().execute(":GETPARA=06#", ":06=", "DRY Power set");
                new BluetoothCommunicationGetActions().execute(":GETPARA=33#", ":33=", "Min Lux Set");
            }
        });

///vikassss
        getdrypowersetbutton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=34#", ":34=", "Max Lux Set");
            }
        });


        btndrypowersetset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editdrypowerset.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                   // if (cVal >= 100 && cVal <= 9999)
                    if (cVal >= 1 && cVal <= 9999)
                    {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 06=";
                      //  String request = ":SET 06=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "DRY Power set");
                       // new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Min Lux Set.");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'DRY Power set' value should be between 0-9999", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(CURRENT_CONTEXT, "'Min Lux Set' value should be between 3-30", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   Toast.makeText(CURRENT_CONTEXT, "'DRY Power set' value should contain a value!", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(CURRENT_CONTEXT, "'Min Lux Set' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setdrypowersetbutton2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editdrypowerset.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                   // if (cVal >= 100 && cVal <= 9999)
                    if (cVal >= 3 && cVal <= 40)
                    {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 33=";
                      //  String request = ":SET 06=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        //new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "DRY Power set");
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Min Lux Set");
                    } else {
                        //Toast.makeText(CURRENT_CONTEXT, "'DRY Power set' value should be between 0-9999", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CURRENT_CONTEXT, "'Min Lux Set' value should be between 3-30", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   // Toast.makeText(CURRENT_CONTEXT, "'DRY Power set' value should contain a value!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CURRENT_CONTEXT, "'Min Lux Set' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        setdrypowersetbutton1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editdrypowerset1.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                   // if (cVal >= 100 && cVal <= 9999)
                    if (cVal >= 41 && cVal <= 300)
                    {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 34=";
                      //  String request = ":SET 06=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        //new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "DRY Power set");
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Max Lux Set");
                    } else {
                        //Toast.makeText(CURRENT_CONTEXT, "'DRY Power set' value should be between 0-9999", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CURRENT_CONTEXT, "'Max Lux Set' value should be between 41-300", Toast.LENGTH_SHORT).show();
                    }
                } else {
                   // Toast.makeText(CURRENT_CONTEXT, "'DRY Power set' value should contain a value!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(CURRENT_CONTEXT, "'Max Lux Set' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btndryrunenablordisableget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=07#", ":07=", "DRY RUN Enable / Disable");
            }
        });

        btndryrunenablordisableset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean state = switchdryrunenablordisable.isChecked();
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String request;
                if (!state) {
                    request = ":SET 07=0000#";
                } else {
                    request = ":SET 07=0001#";
                }
                new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "DRY RUN Enable / Disable");
            }
        });

        btndryrunrestarttimeget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=08#", ":08=", "DRY RUN Restart Time");
            }
        });
        btndryrunrestarttimeset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editdryrunrestarttime.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                   // if (cVal >= 30 && cVal <= 600) {
                    if (cVal >= 15 && cVal <= 600) {//chnage by tarak sir
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 08=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "DRY RUN Restart Time");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'DRY RUN Restart Time' value should be between 30-600", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'DRY RUN Restart Time' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btndryrunrestartenableodisableget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=09#", ":09=", "DRY RUN Restart Enable/Disable");
            }
        });
        btndryrunrestartenableodisableset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean state = switchdryrunrestartenableodisable.isChecked();
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String request = "";
                if (!state) {
                    request = ":SET 09=0000#";
                } else {
                    request = ":SET 09=0001#";
                }
                new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "DRY RUN Restart Enable/Disable");
            }
        });

        btnuvtripsetget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=18#", ":18=", "UV TRIP Setting");
            }
        });
        btnuvtripsetset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = edituvtripset.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                   // if (cVal >= 60 && cVal <= 220) {
                  //  if (cVal >= 140 && cVal <= 260) {////change by tarak sir
                    if (cVal >= 140 && cVal <= 460) {////change by tarak sir
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 18=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "UV TRIP Setting");
                    } else {
                       // Toast.makeText(CURRENT_CONTEXT, "'UV TRIP Setting' value should be between 60-220", Toast.LENGTH_SHORT).show();
                        Toast.makeText(CURRENT_CONTEXT, "'UV TRIP Setting' value should be between 140-260", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'UV TRIP Setting' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnovtripget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=11#", ":11=", "OV TRIP Setting");
            }
        });

        btnovtripset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editovtrip.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 140 && cVal <= 320) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 11=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "OV TRIP Setting");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'OV TRIP Setting' value should be between 140-320", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'OV TRIP Setting' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnstarttimeget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=12#", ":12=", "Start Time");
            }
        });

        btnstarttimeset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editstarttime.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 100 && cVal <= 9999) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 12=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Start Time");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Start Time' value should be between 100-9999", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Start Time' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnsoftstoptimeget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=13#", ":13=", "Soft STOP Time");
            }
        });

        btnsoftstoptimeset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editsoftstoptime.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 100 && cVal <= 9999) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 13=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Soft STOP Time");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Soft STOP Time' value should be between 100-9999", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Soft STOP Time' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnontimeget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=14#", ":14=", "ON Time");
            }
        });

        btnontimeset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editontime.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 0 && cVal <= 600) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 14=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "ON Time");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'ON Time' value should be between 0-600", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'ON Time' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnpumpheadget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=15#", ":15=", "PUMP Head");
            }
        });
        btnpumpheadset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editpumphead.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 1 && cVal <= 1000) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 15=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "PUMP Head");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'PUMP Head' value should be between 1-1000", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'PUMP Head' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnmotorconfigget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=16#", ":16=", "IOL Multiplier");
            }
        });
        btnmotorconfigset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editmotorconfig.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 1 && cVal <= 30) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 16=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "IOL Multiplier");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'IOL Multiplier' value should be between 1-30", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'IOL Multiplier' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btndwelltimeget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=29#", ":29=", "Dwell Time");
            }
        });
        btndwelltimeset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editdwelltime.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 5 && cVal <= 655) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 29=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Dwell Time");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Dwell Time' value should be between 5-655", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Dwell Time' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


      /*  btnresetallenergyget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=18#", ":18=", "Reset All Energy", "10000");
            }
        });

        btnresetallenergyset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean state = switchresetallenergy.isChecked();
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String request = "";
                if (!state) {
                    request = ":SET 18=0000#";
                } else {
                    request = ":SET 18=0001#";
                }
                new BluetoothCommunicationSetActions().execute(request, R.string.COLON_OK_HASH, "Reset All Energy", "10000");
            }
        });*/

        btnunbalancecurrentget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=19#", ":19=", "Unbalance Current");
            }
        });

        btnunbalancecurrentset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editunbalancecurrent.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 10 && cVal <= 50) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 19=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Unbalance Current");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Unbalance Current' value should be between 10-50", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Unbalance Current' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnvoltageunbalget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=20#", ":20=", "Voltage Unbalance");
            }
        });

        btnvoltageunbalset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editvoltageunbal.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 0 && cVal <= 10) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 20=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Voltage Unbalance");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Voltage Unbalance' value should be between 0-10", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Voltage Unbalance' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnmaxstartorstopget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=21#", ":21=", "Maximum Start/Stop per hour");
            }
        });

        btnmaxstartorstopset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String getText = editmaxstartorstop.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 1 && cVal <= 60) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 21=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Maximum Start/Stop per hour");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Maximum Start/Stop per hour' value should be between 1-60", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Maximum Start/Stop per hour' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnsettodefaultget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=22#", ":22=", "Set to Default");
            }
        });
        setsettodefaultbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean state = switchsettodefault.isChecked();
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String request;
                if (!state) {
                    request = ":SET 22=0000#";
                } else {
                    request = ":SET 22=0001#";
                }
                new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Set to Default");
            }
        });


        btnenergylogget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=23#", ":23=", "Energy Log Interval");
            }
        });

        btnenergylogset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editenergylog.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 1 && cVal <= 1000) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 23=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Energy Log Interval");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Energy Log Interval' value should be between 1-1000", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Energy Log Interval' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnoperatingfreqget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=24#", ":24=", "Operating Frequency", "20000");
            }
        });

        btnoperatingfreqset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean state = switoperatingfreqency.isChecked();
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                String request = "";
                if (!state) {
                    request = ":SET 24=0000#";
                } else {
                    request = ":SET 24=0001#";
                }
                new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Operating Frequency", "20000");
            }
        });


        getsensormode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=25#", ":25=", "Sensor Mode");
            }
        });

        setsensormode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = sensormodespinner.getSelectedItem().toString();
                if (getText.length() > 0) {
                    SensorMode_Data data = SensorMode_Data.GetMatchingRecordForTheName(getText);
                    if (data != null) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 25=";
                        request += String.format("%04d", data.getId());
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Sensor Mode");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "Sensor Mode should be selected!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Sensor Mode' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getsensorpolaritybutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=26#", ":26=", "Sensor Polarity");
            }
        });
        setsensorpolaritybutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editsensorpolarity.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 1 && cVal <= 15) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 26=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Sensor Polarity");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Sensor Polarity' value should be between 0-15", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Sensor Polarity' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btngroundfaultcurrentget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=27#", ":27=", "Ground Fault Current");
            }
        });
        btngroundfaultcurrentset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editgroundfaultcurrent.getText().toString();
                if (getText.length() > 0) {
                    float cVal = Float.parseFloat(getText);
                    if (cVal >= 0.1 && cVal <= 10.0) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 27=";
                        request += String.format("%04d", (int) (cVal * 10));
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Ground Fault Current");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Ground Fault Current' value should be between 0.1-10.0 Amps", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Ground Fault Current' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getflowcalibrationbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                new BluetoothCommunicationGetActions().execute(":GETPARA=30#", ":30=", "Flow Calibration");
            }
        });
        setflowcalibrationbutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String getText = editflowcalibration.getText().toString();
                if (getText.length() > 0) {
                    int cVal = Integer.parseInt(getText);
                    if (cVal >= 200 && cVal <= 2000) {
                        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading...");
                        String request = ":SET 30=";
                        request += String.format("%04d", cVal);
                        request += "#";
                        new BluetoothCommunicationSetActions().execute(request, getString(R.string.COLON_OK_HASH), "Flow Calibration");
                    } else {
                        Toast.makeText(CURRENT_CONTEXT, "'Flow Calibration' value should be between 200-2000 Units", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CURRENT_CONTEXT, "'Flow Calibration' value should contain a value!", Toast.LENGTH_SHORT).show();
                }
            }
        });


     //   android.support.v7.app.ActionBar actionBar = getSupportActionBar();
     //   assert actionBar != null;
       // actionBar.setDisplayHomeAsUpEnabled(true);




        imgBackBTNID = (ImageView) findViewById(R.id.imgBackBTNID);
        imgRefreshBTNID = (ImageView) findViewById(R.id.imgRefreshBTNID);
        imgSettingBTNID = (ImageView) findViewById(R.id.imgSettingBTNID);
        txtTopHEadingID = (TextView) findViewById(R.id.txtTopHEadingID);

        imgBackBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgSettingBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(j, 0);
            }
        });

        imgRefreshBTNID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReloadAllData();
            }
        });



    }

    private void initView() {

        clientid = Integer.parseInt(pref.getString("key_clientid", "0")); // if invalid use 0

        if (clientid == 9999) {
            clientid = 0;
        }




        lvlMaximumStartStopperhourMainViewID = (LinearLayout) findViewById(R.id.lvlMaximumStartStopperhourMainViewID);
        lvlPumpVarietyMainViewID = (LinearLayout) findViewById(R.id.lvlPumpVarietyMainViewID);
        lvlFlowCalibrationMainViewID = (LinearLayout) findViewById(R.id.lvlFlowCalibrationMainViewID);
        lvlGroundFaultCurrentMainViewID = (LinearLayout) findViewById(R.id.lvlGroundFaultCurrentMainViewID);
        lvlSensorPolarityMainViewID = (LinearLayout) findViewById(R.id.lvlSensorPolarityMainViewID);
        lvlSensorModeMainViewID = (LinearLayout) findViewById(R.id.lvlSensorModeMainViewID);
        lvlOperatingFrequencyMainViewID = (LinearLayout) findViewById(R.id.lvlOperatingFrequencyMainViewID);
        lvlEnergyLogIntervalMainViewID = (LinearLayout) findViewById(R.id.lvlEnergyLogIntervalMainViewID);
        lvlRatedMainViewID = (LinearLayout) findViewById(R.id.lvlRatedMainViewID);
        lvlOLPercentMainViewID = (LinearLayout) findViewById(R.id.lvlOLPercentMainViewID);
        lvlOverCurrentMaxMainViewID = (LinearLayout) findViewById(R.id.lvlOverCurrentMaxMainViewID);
        lvlAutoManualMainViewID = (LinearLayout) findViewById(R.id.lvlAutoManualMainViewID);
        lvlDryPowerSetMainViewID = (LinearLayout) findViewById(R.id.lvlDryPowerSetMainViewID);
        lvlMaxLuxMainViewID = (LinearLayout) findViewById(R.id.lvlMaxLuxMainViewID);
        lvlMinLuxMainViewID = (LinearLayout) findViewById(R.id.lvlMinLuxMainViewID);
        lvlDRYRUNEnableDMainViewID = (LinearLayout) findViewById(R.id.lvlDRYRUNEnableDMainViewID);
        lvlDRYRUNRestartTimeMainViewID = (LinearLayout) findViewById(R.id.lvlDRYRUNRestartTimeMainViewID);
        lvlDRYRUNRestartEnableDMainViewID = (LinearLayout) findViewById(R.id.lvlDRYRUNRestartEnableDMainViewID);
        lvlUVTRIPSettingMainViewID = (LinearLayout) findViewById(R.id.lvlUVTRIPSettingMainViewID);
        lvlUOVTRIPSettingMainViewID = (LinearLayout) findViewById(R.id.lvlUOVTRIPSettingMainViewID);
        lvlSTARTTimeMainViewID = (LinearLayout) findViewById(R.id.lvlSTARTTimeMainViewID);
        lvlSoftSTOPTimeMainViewID = (LinearLayout) findViewById(R.id.lvlSoftSTOPTimeMainViewID);
        lvlONtimeMainViewID = (LinearLayout) findViewById(R.id.lvlONtimeMainViewID);
        lvlPumpHeadMainViewID = (LinearLayout) findViewById(R.id.lvlPumpHeadMainViewID);
        lvlIOLMultiplierMainViewID = (LinearLayout) findViewById(R.id.lvlIOLMultiplierMainViewID);
        lvlDwellTimeMainViewID = (LinearLayout) findViewById(R.id.lvlDwellTimeMainViewID);
        lvlUnbalanceCurrentMainViewID = (LinearLayout) findViewById(R.id.lvlUnbalanceCurrentMainViewID);
        lvlUnbalanceVoltageMainViewID = (LinearLayout) findViewById(R.id.lvlUnbalanceVoltageMainViewID);
        lvlSettoDefaultMainViewID = (LinearLayout) findViewById(R.id.lvlSettoDefaultMainViewID);


        /*if(clientid > 0)
        {

            lvlMaximumStartStopperhourMainViewID.setVisibility(View.VISIBLE);
            lvlRatedMainViewID.setVisibility(View.VISIBLE);
            lvlOLPercentMainViewID.setVisibility(View.GONE);
            lvlOverCurrentMaxMainViewID.setVisibility(View.GONE);
            lvlAutoManualMainViewID.setVisibility(View.VISIBLE);
            lvlDryPowerSetMainViewID.setVisibility(View.GONE);
            lvlMaxLuxMainViewID.setVisibility(View.GONE);
            lvlMinLuxMainViewID.setVisibility(View.GONE);
            lvlDRYRUNEnableDMainViewID.setVisibility(View.GONE);
            lvlDRYRUNRestartTimeMainViewID.setVisibility(View.GONE);
            lvlDRYRUNRestartEnableDMainViewID.setVisibility(View.GONE);

            lvlUVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlUOVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlSTARTTimeMainViewID.setVisibility(View.VISIBLE);
            lvlSoftSTOPTimeMainViewID.setVisibility(View.VISIBLE);
            lvlONtimeMainViewID.setVisibility(View.VISIBLE);
            lvlPumpHeadMainViewID.setVisibility(View.VISIBLE);
            lvlIOLMultiplierMainViewID.setVisibility(View.GONE);
            lvlDwellTimeMainViewID.setVisibility(View.GONE);
            lvlUnbalanceCurrentMainViewID.setVisibility(View.GONE);
            lvlUnbalanceVoltageMainViewID.setVisibility(View.GONE);
            lvlSettoDefaultMainViewID.setVisibility(View.GONE);

            lvlPumpVarietyMainViewID.setVisibility(View.GONE);
            lvlFlowCalibrationMainViewID.setVisibility(View.GONE);
            lvlGroundFaultCurrentMainViewID.setVisibility(View.GONE);
            lvlSensorPolarityMainViewID.setVisibility(View.VISIBLE);
            lvlSensorModeMainViewID.setVisibility(View.VISIBLE);
            lvlOperatingFrequencyMainViewID.setVisibility(View.VISIBLE);//by santosh sir
            lvlEnergyLogIntervalMainViewID.setVisibility(View.GONE);
            ////set button visibility check
            btnolshorttimeset.setVisibility(View.VISIBLE);
            btnolcurrentset.setVisibility(View.VISIBLE);
            btnratedcurrentset.setVisibility(View.VISIBLE);
            btnpumpset.setVisibility(View.VISIBLE);
            setflowcalibrationbutton.setVisibility(View.VISIBLE);
            btngroundfaultcurrentset.setVisibility(View.VISIBLE);
            setsensorpolaritybutton.setVisibility(View.VISIBLE);
            setsensormode.setVisibility(View.VISIBLE);
            btnoperatingfreqset.setVisibility(View.VISIBLE);
            btnenergylogset.setVisibility(View.VISIBLE);
            setsettodefaultbutton.setVisibility(View.VISIBLE);

            btndwelltimeset.setVisibility(View.VISIBLE);
            btnmotorconfigset.setVisibility(View.VISIBLE);
            btnpumpheadset.setVisibility(View.VISIBLE);
            btnontimeset.setVisibility(View.VISIBLE);
            btnsoftstoptimeset.setVisibility(View.VISIBLE);
            btnstarttimeset.setVisibility(View.VISIBLE);
            btnovtripset.setVisibility(View.VISIBLE);
            btnuvtripsetset.setVisibility(View.VISIBLE);
            btndryrunrestartenableodisableset.setVisibility(View.VISIBLE);
            btndryrunrestarttimeset.setVisibility(View.VISIBLE);
            btndryrunenablordisableset.setVisibility(View.VISIBLE);
            setdrypowersetbutton1.setVisibility(View.VISIBLE);
            setdrypowersetbutton2.setVisibility(View.VISIBLE);
            btndrypowersetset.setVisibility(View.VISIBLE);
            btnautomanset.setVisibility(View.VISIBLE);
            btnmaxstartorstopset.setVisibility(View.VISIBLE);
            btnvoltageunbalset.setVisibility(View.VISIBLE);
            btnunbalancecurrentset.setVisibility(View.VISIBLE);

        }
        else
        {

            lvlRatedMainViewID.setVisibility(View.VISIBLE);
            lvlOLPercentMainViewID.setVisibility(View.GONE);
            lvlMaximumStartStopperhourMainViewID.setVisibility(View.GONE);
            lvlOverCurrentMaxMainViewID.setVisibility(View.GONE);
            lvlAutoManualMainViewID.setVisibility(View.VISIBLE);
            lvlDryPowerSetMainViewID.setVisibility(View.GONE);
            lvlMaxLuxMainViewID.setVisibility(View.GONE);
            lvlMinLuxMainViewID.setVisibility(View.GONE);
            lvlDRYRUNEnableDMainViewID.setVisibility(View.GONE);
            lvlDRYRUNRestartTimeMainViewID.setVisibility(View.GONE);
            lvlDRYRUNRestartEnableDMainViewID.setVisibility(View.GONE);
            lvlUVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlUOVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlSTARTTimeMainViewID.setVisibility(View.GONE);
            lvlSoftSTOPTimeMainViewID.setVisibility(View.GONE);
            lvlONtimeMainViewID.setVisibility(View.VISIBLE);
            lvlPumpHeadMainViewID.setVisibility(View.GONE);
            lvlIOLMultiplierMainViewID.setVisibility(View.GONE);
            lvlDwellTimeMainViewID.setVisibility(View.GONE);
            lvlUnbalanceCurrentMainViewID.setVisibility(View.GONE);
            lvlUnbalanceVoltageMainViewID.setVisibility(View.GONE);
            lvlSettoDefaultMainViewID.setVisibility(View.GONE);

            lvlPumpVarietyMainViewID.setVisibility(View.GONE);
            lvlFlowCalibrationMainViewID.setVisibility(View.GONE);
            lvlGroundFaultCurrentMainViewID.setVisibility(View.GONE);
            lvlSensorPolarityMainViewID.setVisibility(View.VISIBLE);
            lvlSensorModeMainViewID.setVisibility(View.VISIBLE);
            lvlOperatingFrequencyMainViewID.setVisibility(View.GONE);
            lvlEnergyLogIntervalMainViewID.setVisibility(View.GONE);

            ////set button visibility check


            btnolshorttimeset.setVisibility(View.GONE);
            btnolcurrentset.setVisibility(View.GONE);
            btnratedcurrentset.setVisibility(View.VISIBLE);
            btnpumpset.setVisibility(View.GONE);
            setflowcalibrationbutton.setVisibility(View.GONE);
            btngroundfaultcurrentset.setVisibility(View.GONE);
            setsensorpolaritybutton.setVisibility(View.VISIBLE);
            setsensormode.setVisibility(View.VISIBLE);
            btnoperatingfreqset.setVisibility(View.GONE);
            btnenergylogset.setVisibility(View.GONE);
            setsettodefaultbutton.setVisibility(View.GONE);
            btnmaxstartorstopset.setVisibility(View.GONE);
            btndwelltimeset.setVisibility(View.GONE);
            btnmotorconfigset.setVisibility(View.GONE);
            btnpumpheadset.setVisibility(View.GONE);
            btnontimeset.setVisibility(View.VISIBLE);
            btnsoftstoptimeset.setVisibility(View.GONE);
            btnstarttimeset.setVisibility(View.GONE);
            btnovtripset.setVisibility(View.VISIBLE);
            btnuvtripsetset.setVisibility(View.VISIBLE);
            btndryrunrestartenableodisableset.setVisibility(View.GONE);
            btndryrunrestarttimeset.setVisibility(View.GONE);
            btndryrunenablordisableset.setVisibility(View.GONE);
            setdrypowersetbutton1.setVisibility(View.GONE);
            setdrypowersetbutton2.setVisibility(View.GONE);
            btndrypowersetset.setVisibility(View.GONE);
            btnautomanset.setVisibility(View.GONE);
            btnmaxstartorstopset.setVisibility(View.GONE);
            btnvoltageunbalset.setVisibility(View.GONE);
            btnunbalancecurrentset.setVisibility(View.GONE);///chnage by xxxxxxxxxxxxxx

        }*/


        if(clientid > 0)
        {
            lvlMaximumStartStopperhourMainViewID.setVisibility(View.VISIBLE);
            lvlRatedMainViewID.setVisibility(View.VISIBLE);
            lvlOLPercentMainViewID.setVisibility(View.VISIBLE);
            lvlOverCurrentMaxMainViewID.setVisibility(View.VISIBLE);
            lvlAutoManualMainViewID.setVisibility(View.VISIBLE);
            lvlDryPowerSetMainViewID.setVisibility(View.VISIBLE);
            lvlMaxLuxMainViewID.setVisibility(View.VISIBLE);
            lvlMinLuxMainViewID.setVisibility(View.VISIBLE);
            lvlDRYRUNEnableDMainViewID.setVisibility(View.VISIBLE);
            lvlDRYRUNRestartTimeMainViewID.setVisibility(View.VISIBLE);
            lvlDRYRUNRestartEnableDMainViewID.setVisibility(View.VISIBLE);
            lvlUVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlUOVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlSTARTTimeMainViewID.setVisibility(View.VISIBLE);
            lvlSoftSTOPTimeMainViewID.setVisibility(View.VISIBLE);
            lvlONtimeMainViewID.setVisibility(View.VISIBLE);
            lvlPumpHeadMainViewID.setVisibility(View.VISIBLE);
            lvlIOLMultiplierMainViewID.setVisibility(View.VISIBLE);// pareshweerrrrr
            lvlDwellTimeMainViewID.setVisibility(View.VISIBLE);
            lvlUnbalanceCurrentMainViewID.setVisibility(View.VISIBLE);
            lvlUnbalanceVoltageMainViewID.setVisibility(View.VISIBLE);
            lvlSettoDefaultMainViewID.setVisibility(View.VISIBLE);
            lvlPumpVarietyMainViewID.setVisibility(View.VISIBLE);
            lvlFlowCalibrationMainViewID.setVisibility(View.VISIBLE);
            lvlGroundFaultCurrentMainViewID.setVisibility(View.VISIBLE);////endddddd
            lvlSensorPolarityMainViewID.setVisibility(View.VISIBLE);
            lvlSensorModeMainViewID.setVisibility(View.VISIBLE);
            lvlOperatingFrequencyMainViewID.setVisibility(View.VISIBLE);//by santosh sir
            lvlEnergyLogIntervalMainViewID.setVisibility(View.GONE);
            ////set button visibility check
            btnolshorttimeset.setVisibility(View.VISIBLE);
            btnolcurrentset.setVisibility(View.VISIBLE);
            btnratedcurrentset.setVisibility(View.VISIBLE);
            btnpumpset.setVisibility(View.VISIBLE);
            setflowcalibrationbutton.setVisibility(View.VISIBLE);
            btngroundfaultcurrentset.setVisibility(View.VISIBLE);
            setsensorpolaritybutton.setVisibility(View.VISIBLE);
            setsensormode.setVisibility(View.VISIBLE);
            btnoperatingfreqset.setVisibility(View.VISIBLE);
            btnenergylogset.setVisibility(View.VISIBLE);
            setsettodefaultbutton.setVisibility(View.VISIBLE);
            btndwelltimeset.setVisibility(View.VISIBLE);
            btnmotorconfigset.setVisibility(View.VISIBLE);
            btnpumpheadset.setVisibility(View.VISIBLE);
            btnontimeset.setVisibility(View.VISIBLE);
            btnsoftstoptimeset.setVisibility(View.VISIBLE);
            btnstarttimeset.setVisibility(View.VISIBLE);
            btnovtripset.setVisibility(View.VISIBLE);
            btnuvtripsetset.setVisibility(View.VISIBLE);
            btndryrunrestartenableodisableset.setVisibility(View.VISIBLE);
            btndryrunrestarttimeset.setVisibility(View.VISIBLE);
            btndryrunenablordisableset.setVisibility(View.VISIBLE);
            setdrypowersetbutton1.setVisibility(View.VISIBLE);
            setdrypowersetbutton2.setVisibility(View.VISIBLE);
            btndrypowersetset.setVisibility(View.VISIBLE);
            btnautomanset.setVisibility(View.VISIBLE);
            btnmaxstartorstopset.setVisibility(View.VISIBLE);
            btnvoltageunbalset.setVisibility(View.VISIBLE);
            btnunbalancecurrentset.setVisibility(View.VISIBLE);
        }
        else
        {
            lvlRatedMainViewID.setVisibility(View.VISIBLE);
            lvlOLPercentMainViewID.setVisibility(View.VISIBLE);
            lvlMaximumStartStopperhourMainViewID.setVisibility(View.VISIBLE);
            lvlOverCurrentMaxMainViewID.setVisibility(View.VISIBLE);
            lvlAutoManualMainViewID.setVisibility(View.VISIBLE);
            lvlDryPowerSetMainViewID.setVisibility(View.VISIBLE);
            lvlMaxLuxMainViewID.setVisibility(View.VISIBLE);
            lvlMinLuxMainViewID.setVisibility(View.VISIBLE);
            lvlDRYRUNEnableDMainViewID.setVisibility(View.VISIBLE);
            lvlDRYRUNRestartTimeMainViewID.setVisibility(View.VISIBLE);
            lvlDRYRUNRestartEnableDMainViewID.setVisibility(View.VISIBLE);
            lvlUVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlUOVTRIPSettingMainViewID.setVisibility(View.VISIBLE);
            lvlSTARTTimeMainViewID.setVisibility(View.VISIBLE);
            lvlSoftSTOPTimeMainViewID.setVisibility(View.VISIBLE);
            lvlONtimeMainViewID.setVisibility(View.VISIBLE);
            lvlPumpHeadMainViewID.setVisibility(View.VISIBLE);

            lvlIOLMultiplierMainViewID.setVisibility(View.VISIBLE);// pareshweerrrrr
            lvlDwellTimeMainViewID.setVisibility(View.VISIBLE);
            lvlUnbalanceCurrentMainViewID.setVisibility(View.VISIBLE);
            lvlUnbalanceVoltageMainViewID.setVisibility(View.VISIBLE);
            lvlSettoDefaultMainViewID.setVisibility(View.VISIBLE);
            lvlPumpVarietyMainViewID.setVisibility(View.VISIBLE);
            lvlFlowCalibrationMainViewID.setVisibility(View.VISIBLE);
            lvlGroundFaultCurrentMainViewID.setVisibility(View.VISIBLE);////endddddd

            lvlSensorPolarityMainViewID.setVisibility(View.VISIBLE);
            lvlSensorModeMainViewID.setVisibility(View.VISIBLE);
            lvlOperatingFrequencyMainViewID.setVisibility(View.VISIBLE);

            lvlEnergyLogIntervalMainViewID.setVisibility(View.VISIBLE);

            ////set button visibility check

            btnolshorttimeset.setVisibility(View.VISIBLE);
            btnolcurrentset.setVisibility(View.VISIBLE);
            btnratedcurrentset.setVisibility(View.VISIBLE);
            btnpumpset.setVisibility(View.VISIBLE);
            setflowcalibrationbutton.setVisibility(View.VISIBLE);
            btngroundfaultcurrentset.setVisibility(View.VISIBLE);
            setsensorpolaritybutton.setVisibility(View.VISIBLE);
            setsensormode.setVisibility(View.VISIBLE);
            btnoperatingfreqset.setVisibility(View.VISIBLE);
            btnenergylogset.setVisibility(View.VISIBLE);
            setsettodefaultbutton.setVisibility(View.VISIBLE);

            btndwelltimeset.setVisibility(View.VISIBLE);
            btnmotorconfigset.setVisibility(View.VISIBLE);
            btnpumpheadset.setVisibility(View.VISIBLE);
            btnontimeset.setVisibility(View.VISIBLE);
            btnsoftstoptimeset.setVisibility(View.VISIBLE);
            btnstarttimeset.setVisibility(View.VISIBLE);
            btnovtripset.setVisibility(View.VISIBLE);
            btnuvtripsetset.setVisibility(View.VISIBLE);
            btndryrunrestartenableodisableset.setVisibility(View.VISIBLE);
            btndryrunrestarttimeset.setVisibility(View.VISIBLE);
            btndryrunenablordisableset.setVisibility(View.VISIBLE);
            setdrypowersetbutton1.setVisibility(View.VISIBLE);
            setdrypowersetbutton2.setVisibility(View.VISIBLE);
            btndrypowersetset.setVisibility(View.VISIBLE);
            btnautomanset.setVisibility(View.VISIBLE);
            btnmaxstartorstopset.setVisibility(View.VISIBLE);
            btnvoltageunbalset.setVisibility(View.VISIBLE);
            btnunbalancecurrentset.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                ReloadAllData();
                break;
            case R.id.action_settings:
                Intent j = new Intent(Parameters_GetSet_Activity.this, SettingsActivity.class);
                startActivityForResult(j, 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    private void ReloadAllData() {
        Utils.ShowProgressDialog((Activity) CURRENT_CONTEXT, pd, "Loading..(May take few minutes)..");
        BluetoothCommunicationGetActions r1 = new BluetoothCommunicationGetActions();
        r1.HideProgressbar = false;
        r1.execute(":GETPARA=32#", ":32=", "Pump Variety");

        BluetoothCommunicationGetActions r2 = new BluetoothCommunicationGetActions();
        r2.HideProgressbar = false;
        r2.execute(":GETPARA=02#", ":02=", "Rated Current");

        BluetoothCommunicationGetActions r3 = new BluetoothCommunicationGetActions();
        r3.HideProgressbar = false;
        r3.execute(":GETPARA=28#", ":28=", "OL Percent");

        BluetoothCommunicationGetActions r4 = new BluetoothCommunicationGetActions();
        r4.HideProgressbar = false;
        r4.execute(":GETPARA=04#", ":04=", "OverCurrent Max");

        BluetoothCommunicationGetActions r5 = new BluetoothCommunicationGetActions();
        r5.HideProgressbar = false;
        r5.execute(":GETPARA=05#", ":05=", "AUTO / Manual");

        BluetoothCommunicationGetActions r6 = new BluetoothCommunicationGetActions();
        r6.HideProgressbar = false;
        r6.execute(":GETPARA=06#", ":06=", "DRY Power set");

        BluetoothCommunicationGetActions r7 = new BluetoothCommunicationGetActions();
        r7.HideProgressbar = false;
        r7.execute(":GETPARA=07#", ":07=", "DRY RUN Enable / Disable");

        BluetoothCommunicationGetActions r8 = new BluetoothCommunicationGetActions();
        r8.HideProgressbar = false;
        r8.execute(":GETPARA=08#", ":08=", "DRY RUN Restart Time");

        BluetoothCommunicationGetActions r9 = new BluetoothCommunicationGetActions();
        r9.HideProgressbar = false;
        r9.execute(":GETPARA=09#", ":09=", "DRY RUN Restart Enable/Disable");

        BluetoothCommunicationGetActions r18 = new BluetoothCommunicationGetActions();
        r18.HideProgressbar = false;
        r18.execute(":GETPARA=18#", ":18=", "UV TRIP Setting");

        BluetoothCommunicationGetActions r11 = new BluetoothCommunicationGetActions();
        r11.HideProgressbar = false;
        r11.execute(":GETPARA=11#", ":11=", "OV TRIP Setting");


        BluetoothCommunicationGetActions r12 = new BluetoothCommunicationGetActions();
        r12.HideProgressbar = false;
        r12.execute(":GETPARA=12#", ":12=", "Start Time");

        BluetoothCommunicationGetActions r13 = new BluetoothCommunicationGetActions();
        r13.HideProgressbar = false;
        r13.execute(":GETPARA=13#", ":13=", "Soft STOP Time");

        BluetoothCommunicationGetActions r14 = new BluetoothCommunicationGetActions();
        r14.HideProgressbar = false;
        r14.execute(":GETPARA=14#", ":14=", "ON Time");

        BluetoothCommunicationGetActions r15 = new BluetoothCommunicationGetActions();
        r15.HideProgressbar = false;
        r15.execute(":GETPARA=15#", ":15=", "PUMP Head");

        BluetoothCommunicationGetActions r16 = new BluetoothCommunicationGetActions();
        r16.HideProgressbar = false;
        r16.execute(":GETPARA=16#", ":16=", "IOL Multiplier");

        BluetoothCommunicationGetActions r17 = new BluetoothCommunicationGetActions();
        r17.HideProgressbar = false;
        r17.execute(":GETPARA=29#", ":29=", "Dwell Time");

        /*BluetoothCommunicationGetActions r18 = new BluetoothCommunicationGetActions();
        r18.HideProgressbar = false;
        r18.execute(":GETPARA=18#", ":18=", "Reset All Energy", "10000");*/

        BluetoothCommunicationGetActions r19 = new BluetoothCommunicationGetActions();
        r19.HideProgressbar = false;
        r19.execute(":GETPARA=19#", ":19=", "Unbalance Current");

        BluetoothCommunicationGetActions r20 = new BluetoothCommunicationGetActions();
        r20.HideProgressbar = false;
        r20.execute(":GETPARA=20#", ":20=", "Voltage Unbalance");

        BluetoothCommunicationGetActions r21 = new BluetoothCommunicationGetActions();
        r21.HideProgressbar = false;
        r21.execute(":GETPARA=21#", ":21=", "Maximum Start/Stop per hour");


        BluetoothCommunicationGetActions r22 = new BluetoothCommunicationGetActions();
        r22.HideProgressbar = false;
        r22.execute(":GETPARA=22#", ":22=", "Set to Default");


        BluetoothCommunicationGetActions r23 = new BluetoothCommunicationGetActions();
        r23.HideProgressbar = false;
        r23.execute(":GETPARA=23#", ":23=", "Energy Log Interval");

        BluetoothCommunicationGetActions r24 = new BluetoothCommunicationGetActions();
        r24.HideProgressbar = false;
        r24.execute(":GETPARA=24#", ":24=", "Operating Frequency", "20000");

        BluetoothCommunicationGetActions r25 = new BluetoothCommunicationGetActions();
        r25.HideProgressbar = false;
        r25.execute(":GETPARA=25#", ":25=", "Sensor Mode");

        BluetoothCommunicationGetActions r26 = new BluetoothCommunicationGetActions();
        r26.HideProgressbar = false;
        r26.execute(":GETPARA=26#", ":26=", "Sensor Polarity");

        BluetoothCommunicationGetActions r27 = new BluetoothCommunicationGetActions();
        r27.HideProgressbar = false;
        r27.execute(":GETPARA=27#", ":27=", "Ground Fault Current");

        BluetoothCommunicationGetActions r29 = new BluetoothCommunicationGetActions();
        r29.HideProgressbar = false;
        r29.execute(":GETPARA=33#", ":33=", "Min Lux Set");

        BluetoothCommunicationGetActions r30 = new BluetoothCommunicationGetActions();
        r30.HideProgressbar = false;
        r30.execute(":GETPARA=34#", ":34=", "Max Lux Set");

        BluetoothCommunicationGetActions r28 = new BluetoothCommunicationGetActions();
        r28.execute(":GETPARA=30#", ":30=", "Flow Calibration");


    }


    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationSetActions extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        private String response;
        private String condition;
        private Integer timeOut = -1;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            condition = requests[2];
            if (requests.length > 3) {
                timeOut = Integer.parseInt(requests[3]);
            }
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                        try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (btSocket == null) {
                    BluetoothAdapter myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createRfcommSocketToServiceRecord(Utils.MyUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                }
                if (!btSocket.isConnected()) btSocket.connect();//start connection
                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    btSocket.getOutputStream().write(STARTRequest);
                    byte[] readBytes = new byte[50];
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    InputStream iStream = btSocket.getInputStream();
                    int retryCount = 0;
                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (-1 != timeOut) {
                        maxRetryCount = (timeOut / Utils.RETRY_WAIT_TIME);
                    }
                    int available;
                    while ((available = iStream.available()) == 0 && retryCount < maxRetryCount) {
                        retryCount++;
                        Thread.sleep(Utils.RETRY_WAIT_TIME);
                    }
                    if (available <= 0) return false;
                    int bytesRead = iStream.read(readBytes);
                    if (bytesRead > 2) {
                            String str = new String(Utils.Trim(readBytes), StandardCharsets.US_ASCII);
                        if (str.contains(requests[1])) {
                            response = requests[1];
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            Date currentTime = Calendar.getInstance().getTime();
            Resources resources = getResources();
            switch (condition) {
                case "Pump Variety":
                    if (result) {
                        txtpumpresp.setText(response);
                        txtpumpresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtpumptime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtpumpresp.setText(R.string.SET_ERROR);
                        txtpumpresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtpumptime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtpumptime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Rated Current":
                    if (result) {
                        txtratedcurrentresp.setText(response);
                        txtratedcurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtratedcurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtratedcurrentresp.setText(R.string.SET_ERROR);
                        txtratedcurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtratedcurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtratedcurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "OL Percent":
                    if (result) {
                        txtolcurrentresp.setText(response);
                        txtolcurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtolcurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtolcurrentresp.setText(R.string.SET_ERROR);
                        txtolcurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtolcurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtolcurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "OverCurrent Max":
                    if (result) {
                        txtolshorttimeresp.setText(response);
                        txtolshorttimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtolshorttimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtolshorttimeresp.setText(R.string.SET_ERROR);
                        txtolshorttimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtolshorttimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtolshorttimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "AUTO / Manual":
                    if (result) {
                        txtautomanresp.setText(response);
                        txtautomanresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtautomantime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtautomanresp.setText(R.string.SET_ERROR);
                        txtautomanresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtautomantime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtautomantime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "DRY Power set":
                    if (result) {
                        txtdrypowersetresp.setText(response);
                        txtdrypowersetresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtdrypowersettime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtdrypowersetresp.setText(R.string.SET_ERROR);
                        txtdrypowersetresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdrypowersettime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdrypowersettime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                    case "Min Lux Set":
                    if (result) {
                        txtdrypowersetresp2.setText(response);
                        txtdrypowersetresp2.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtdrypowersettime2.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtdrypowersetresp2.setText(R.string.SET_ERROR);
                        txtdrypowersetresp2.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdrypowersettime2.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdrypowersettime2.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                    case "Max Lux Set":
                    if (result) {
                        txtdrypowersetresp1.setText(response);
                        txtdrypowersetresp1.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtdrypowersettime1.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtdrypowersetresp1.setText(R.string.SET_ERROR);
                        txtdrypowersetresp1.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdrypowersettime1.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdrypowersettime1.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "DRY RUN Enable / Disable":
                    if (result) {
                        txtdryrunenablordisableresp.setText(response);
                        txtdryrunenablordisableresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        txtdryrunenablordisabletime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtdryrunenablordisableresp.setText(R.string.SET_ERROR);
                        txtdryrunenablordisableresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdryrunenablordisabletime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdryrunenablordisabletime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "DRY RUN Restart Time":
                    if (result) {
                        txtdryrunrestarttimeresp.setText(response);
                        txtdryrunrestarttimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdryrunrestarttimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtdryrunrestarttimeresp.setText(R.string.SET_ERROR);
                        txtdryrunrestarttimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdryrunrestarttimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdryrunrestarttimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "DRY RUN Restart Enable/Disable":
                    if (result) {
                        txtdryrunrestartenableodisableresp.setText(response);
                        txtdryrunrestartenableodisabletime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdryrunrestartenableodisableresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtdryrunrestartenableodisableresp.setText(R.string.SET_ERROR);
                        txtdryrunrestartenableodisableresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdryrunrestartenableodisabletime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdryrunrestartenableodisabletime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "UV TRIP Setting":
                    if (result) {
                        txtuvtripsetresp.setText(response);
                        txtuvtripsettime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtuvtripsetresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtuvtripsetresp.setText(R.string.SET_ERROR);
                        txtuvtripsetresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtuvtripsettime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtuvtripsettime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "OV TRIP Setting":
                    if (result) {
                        txtovtripresp.setText(response);
                        txtovtriptime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtovtripresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtovtripresp.setText(R.string.SET_ERROR);
                        txtovtripresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtovtriptime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtovtriptime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Start Time":
                    if (result) {
                        txtstarttimeresp.setText(response);
                        txtstarttimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtstarttimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtstarttimeresp.setText(R.string.SET_ERROR);
                        txtstarttimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtstarttimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtstarttimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Soft STOP Time":
                    if (result) {
                        txtsoftstoptimeresp.setText(response);
                        txtsoftstoptimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtsoftstoptimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtsoftstoptimeresp.setText(R.string.SET_ERROR);
                        txtsoftstoptimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsoftstoptimetime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtsoftstoptimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "ON Time":
                    if (result) {
                        txtontimeresp.setText(response);
                        txtontimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtontimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtontimeresp.setText(R.string.SET_ERROR);
                        txtontimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtontimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtontimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "PUMP Head":
                    if (result) {
                        txtpumpheadresp.setText(response);
                        txtpumpheadresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtpumpheadtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtpumpheadresp.setText(R.string.SET_ERROR);
                        txtpumpheadresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtpumpheadtime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtpumpheadtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "IOL Multiplier":
                    if (result) {
                        txtmotorconfigresp.setText(response);
                        txtmotorconfigresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtmotorconfigtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtmotorconfigresp.setText(R.string.SET_ERROR);
                        txtmotorconfigresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtmotorconfigtime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtmotorconfigtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "Dwell Time":
                    if (result) {
                        txtdwellresp.setText(response);
                        txtdwelltime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdwellresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtdwellresp.setText(R.string.SET_ERROR);

                        txtdwellresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdwelltime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdwelltime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                /*case "Reset All Energy":
                    if (result) {
                        txtresetallenergyresp.setText(response);
                        txtresetallenergytime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtresetallenergyresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtresetallenergyresp.setText(R.string.SET_ERROR);
                        txtresetallenergyresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtresetallenergytime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtresetallenergytime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;*/


                case "Unbalance Current":
                    if (result) {
                        txtunbalancecurrentresp.setText(response);
                        txtunbalancecurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtunbalancecurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtunbalancecurrentresp.setText(R.string.SET_ERROR);
                        txtunbalancecurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtunbalancecurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtunbalancecurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "Voltage Unbalance":
                    if (result) {
                        txtvoltageunbalresp.setText(response);
                        txtvoltageunbaltime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtvoltageunbalresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtvoltageunbalresp.setText(R.string.SET_ERROR);
                        txtvoltageunbalresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtvoltageunbaltime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtvoltageunbaltime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Maximum Start/Stop per hour":
                    if (result) {
                        txtmaxstartorstopresp.setText(response);
                        txtmaxstartorstoptime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtmaxstartorstopresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtmaxstartorstopresp.setText(R.string.SET_ERROR);
                        txtmaxstartorstopresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtmaxstartorstoptime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtmaxstartorstoptime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "Set to Default":
                    if (result) {
                        txtsettodefaultresp.setText(response);
                        txtsettodefaultresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtsettodefaulttime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtsettodefaultresp.setText(R.string.SET_ERROR);
                        txtsettodefaultresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsettodefaulttime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtsettodefaulttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "Energy Log Interval":
                    if (result) {
                        txtenergylogresp.setText(response);
                        txtenergylogtime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtenergylogresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtenergylogresp.setText(R.string.SET_ERROR);
                        txtenergylogresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtenergylogtime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtenergylogtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Operating Frequency":
                    if (result) {
                        txtoperatingfreqresp.setText(response);
                        txtoperatingfreqtime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtoperatingfreqresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtoperatingfreqresp.setText(R.string.SET_ERROR);
                        txtoperatingfreqresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtoperatingfreqtime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtoperatingfreqtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Sensor Mode":
                    if (result) {
                        txtsensormoderesp.setText(response);
                        txtsensormodetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtsensormoderesp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtsensormoderesp.setText(R.string.SET_ERROR);
                        txtsensormoderesp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsensormodetime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtsensormodetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Sensor Polarity":
                    if (result) {
                        txtsensorpolarityresp.setText(response);
                        txtsensorpolaritytime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtsensorpolarityresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        txtsensorpolarityresp.setText(R.string.SET_ERROR);
                        txtsensorpolarityresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsensorpolaritytime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtsensorpolaritytime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Ground Fault Current":
                    if (result) {
                        txtgroundfaultcurrentresp.setText(response);
                        txtgroundfaultcurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtgroundfaultcurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtgroundfaultcurrentresp.setText(R.string.G_ERROR);
                        txtgroundfaultcurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtgroundfaultcurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtgroundfaultcurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Flow Calibration":
                    if (result) {
                        txtflowcalibrationresp.setText(response);
                        txtflowcalibrationresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtflowcalibrationtime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        txtflowcalibrationresp.setText(R.string.G_ERROR);
                        txtflowcalibrationresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtflowcalibrationtime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtflowcalibrationtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
            }
            Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BluetoothCommunicationGetActions extends AsyncTask<String, Void, Boolean>  // UI thread
    {
        public boolean HideProgressbar = true;
        private Integer response;
        private String condition;
        private Integer timeOut = -1;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(String... requests) //while the progress dialog is shown, the connection is done in background
        {
            condition = requests[2];
            if (requests.length > 3) {
                timeOut = Integer.parseInt(requests[3]);
            }
            try {
                if (btSocket != null) {
                    if (btSocket.isConnected()) {
                        try {
                            btSocket.close();
                            btSocket = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (btSocket == null) {
                    BluetoothAdapter myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice deposition = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = deposition.createRfcommSocketToServiceRecord(Utils.MyUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                }
                if (!btSocket.isConnected()) btSocket.connect();
                if (btSocket.isConnected()) {
                    byte[] STARTRequest = requests[0].getBytes(StandardCharsets.US_ASCII);
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    btSocket.getOutputStream().write(STARTRequest);
                    byte[] readBytes = new byte[50];
                    try {
                        sleep(200);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    InputStream iStream = btSocket.getInputStream();
                    int retryCount = 0;
                    int maxRetryCount = Utils.MAX_RETRY_COUNT;
                    if (-1 != timeOut) {
                        maxRetryCount = (timeOut / Utils.RETRY_WAIT_TIME);
                    }
                    int available;
                    while ((available = iStream.available()) == 0 && retryCount < maxRetryCount) {
                        retryCount++;
                        Thread.sleep(Utils.RETRY_WAIT_TIME);
                    }
                    if (available <= 0) return false;
                    int bytesRead = iStream.read(readBytes);
                    if (bytesRead > 2) {
                        String str = new String(Utils.Trim(readBytes), StandardCharsets.US_ASCII);
                        if (str.contains(requests[1]) && str.contains("#")) {
                            int startIndx1 = str.indexOf(requests[1]);
                            startIndx1 = startIndx1 + requests[1].length();
                            int endIndx1 = str.indexOf("#", startIndx1);
                            response = Integer.parseInt(str.substring(startIndx1, endIndx1));
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);
            Date currentTime = Calendar.getInstance().getTime();
            switch (condition) {
                case "Pump Variety":
                    if (result) {
                        editpumpvariety.setText(String.valueOf(response));
                        txtpumpresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtpumptime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtpumpresp.setText(R.string.COLON_OK_HASH);
                    } else {
                        editpumpvariety.setText("0");
                        txtpumpresp.setText(R.string.G_ERROR);
                        txtpumpresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtpumptime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtpumptime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Rated Current":
                    if (result) {
                        double f = response / 10.0;
                        editratedcurrent.setText(String.valueOf(f));
                        txtratedcurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtratedcurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtratedcurrentresp.setText(R.string.COLON_OK_HASH);
                    } else {
                        editratedcurrent.setText("0");
                        txtratedcurrentresp.setText(R.string.G_ERROR);
                        txtratedcurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtratedcurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtratedcurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "OL Percent":
                    if (result) {
                        editolcurrent.setText(String.valueOf(response));
                        txtolcurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtolcurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtolcurrentresp.setText(R.string.COLON_OK_HASH);
                    } else {
                        editolcurrent.setText("0");
                        txtolcurrentresp.setText(R.string.G_ERROR);
                        txtolcurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtolcurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtolcurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "OverCurrent Max":
                    if (result) {
                        editolshorttime.setText(String.valueOf(response));
                        txtolshorttimeresp.setText(R.string.COLON_OK_HASH);
                        txtolshorttimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtolshorttimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editolshorttime.setText("0");
                        txtolshorttimeresp.setText(R.string.G_ERROR);
                        txtolshorttimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtolshorttimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtolshorttimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "AUTO / Manual":
                    if (result) {
                        if (response.equals(1)) {
                            switchautoman.setChecked(true);
                            switchautoman.setText("AUTO");

                            txtautomantime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        } else {
                            switchautoman.setChecked(false);
                            switchautoman.setText("MANUAL");
                            txtautomantime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        }
                        txtautomanresp.setText(R.string.COLON_OK_HASH);
                        txtautomanresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        switchautoman.setChecked(false);
                        switchautoman.setText("UN-KNOWN");
                        txtautomanresp.setText(R.string.G_ERROR);
                        txtautomanresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtautomantime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtautomantime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


               case "DRY Power set":
                    if (result) {
                        editdrypowerset.setText(String.valueOf(response));
                        txtdrypowersetresp.setText(R.string.COLON_OK_HASH);
                        txtdrypowersetresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdrypowersettime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editdrypowerset.setText("0");
                        txtdrypowersetresp.setText(R.string.G_ERROR);
                        txtdrypowersetresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdrypowersettime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtdrypowersettime.setText(DateFormat.getDateTimeInstance().format(currentTime));

                    break;
                    case "Min Lux Set":
                    if (result) {
                        editdrypowerset2.setText(String.valueOf(response));
                        txtdrypowersetresp2.setText(R.string.COLON_OK_HASH);
                        txtdrypowersetresp2.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdrypowersettime2.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editdrypowerset2.setText("0");
                        txtdrypowersetresp2.setText(R.string.G_ERROR);
                        txtdrypowersetresp2.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdrypowersettime2.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtdrypowersettime2.setText(DateFormat.getDateTimeInstance().format(currentTime));

                    break;

                    case "Max Lux Set":
                    if (result) {
                        editdrypowerset1.setText(String.valueOf(response));
                        txtdrypowersetresp1.setText(R.string.COLON_OK_HASH);
                        txtdrypowersetresp1.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdrypowersettime1.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editdrypowerset1.setText("0");
                        txtdrypowersetresp1.setText(R.string.G_ERROR);
                        txtdrypowersetresp1.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdrypowersettime1.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtdrypowersettime1.setText(DateFormat.getDateTimeInstance().format(currentTime));

                    break;


                case "DRY RUN Enable / Disable":
                    if (result) {
                        if (response.equals(1)) {
                            switchdryrunenablordisable.setChecked(true);
                            switchdryrunenablordisable.setText("Enabled");

                            txtdryrunenablordisabletime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        } else {
                            switchdryrunenablordisable.setChecked(false);
                            switchdryrunenablordisable.setText("Disabled");
                            txtdryrunenablordisabletime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        }
                        txtdryrunenablordisableresp.setText(R.string.COLON_OK_HASH);
                        txtdryrunenablordisableresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdryrunenablordisabletime.setTextColor(resources.getColor(R.color.colorSucessGreen));


                    } else {
                        switchdryrunenablordisable.setChecked(false);
                        switchdryrunenablordisable.setText("UN-KNOWN");
                        txtdryrunenablordisabletime.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdryrunenablordisableresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdryrunenablordisableresp.setText(R.string.G_ERROR);
                    }
                    txtdryrunenablordisabletime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "DRY RUN Restart Time":
                    if (result) {
                        editdryrunrestarttime.setText(String.valueOf(response));
                        txtdryrunrestarttimeresp.setText(R.string.COLON_OK_HASH);
                        txtdryrunrestarttimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdryrunrestarttimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editdryrunrestarttime.setText("0");
                        txtdryrunrestarttimeresp.setText(R.string.G_ERROR);
                        txtdryrunrestarttimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdryrunrestarttimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdryrunrestarttimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "DRY RUN Restart Enable/Disable":
                    if (result) {
                        if (response.equals(1)) {
                            switchdryrunrestartenableodisable.setChecked(true);
                            switchdryrunrestartenableodisable.setText("Enabled");
                            txtdryrunrestartenableodisabletime.setTextColor(resources.getColor(R.color.colorSucessGreen));


                        } else {
                            switchdryrunrestartenableodisable.setChecked(false);
                            switchdryrunrestartenableodisable.setText("Disabled");
                            txtdryrunrestartenableodisabletime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        }
                        txtdryrunrestartenableodisableresp.setText(R.string.COLON_OK_HASH);
                        txtdryrunrestartenableodisableresp.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        switchdryrunrestartenableodisable.setChecked(false);
                        switchdryrunrestartenableodisable.setText("UN-KNOWN");
                        txtdryrunrestartenableodisableresp.setText(R.string.G_ERROR);
                        txtdryrunrestartenableodisableresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdryrunrestartenableodisabletime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdryrunrestartenableodisabletime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "UV TRIP Setting":
                    if (result) {
                        edituvtripset.setText(String.valueOf(response));
                        txtuvtripsetresp.setText(R.string.COLON_OK_HASH);
                        txtuvtripsetresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtuvtripsettime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        edituvtripset.setText("0");
                        txtuvtripsetresp.setText(R.string.G_ERROR);
                        txtuvtripsettime.setTextColor(resources.getColor(R.color.colorErrorRed));

                        txtuvtripsetresp.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtuvtripsettime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "OV TRIP Setting":
                    if (result) {
                        editovtrip.setText(String.valueOf(response));
                        txtovtripresp.setText(R.string.COLON_OK_HASH);
                        txtovtripresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtovtriptime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editovtrip.setText("0");
                        txtovtripresp.setText(R.string.G_ERROR);
                        txtovtripresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtovtriptime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtovtriptime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Start Time":
                    if (result) {
                        editstarttime.setText(String.valueOf(response));
                        txtstarttimeresp.setText(R.string.COLON_OK_HASH);
                        txtstarttimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtstarttimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editstarttime.setText("0");
                        txtstarttimeresp.setText(R.string.G_ERROR);
                        txtstarttimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtstarttimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtstarttimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Soft STOP Time":
                    if (result) {
                        editsoftstoptime.setText(String.valueOf(response));
                        txtsoftstoptimeresp.setText(R.string.COLON_OK_HASH);
                        txtsoftstoptimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtsoftstoptimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editsoftstoptime.setText("0");
                        txtsoftstoptimeresp.setText(R.string.G_ERROR);
                        txtsoftstoptimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsoftstoptimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtsoftstoptimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "ON Time":
                    if (result) {
                        editontime.setText(String.valueOf(response));
                        txtontimeresp.setText(R.string.COLON_OK_HASH);
                        txtontimeresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtontimetime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editontime.setText("0");
                        txtontimeresp.setText(R.string.G_ERROR);
                        txtontimeresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtontimetime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtontimetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "PUMP Head":
                    if (result) {
                        editpumphead.setText(String.valueOf(response));
                        txtpumpheadresp.setText(R.string.COLON_OK_HASH);
                        txtpumpheadresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtpumpheadtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editpumphead.setText("0");
                        txtpumpheadresp.setText(R.string.G_ERROR);
                        txtpumpheadresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtpumpheadtime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtpumpheadtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "IOL Multiplier":
                    if (result) {
                        editmotorconfig.setText(String.valueOf(response));
                        txtmotorconfigresp.setText(R.string.COLON_OK_HASH);
                        txtmotorconfigresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtmotorconfigtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editmotorconfig.setText("0");
                        txtmotorconfigresp.setText(R.string.G_ERROR);
                        txtmotorconfigresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtmotorconfigtime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtmotorconfigtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Dwell Time":
                    if (result) {
                        editdwelltime.setText(String.valueOf(response));
                        txtdwellresp.setText(R.string.COLON_OK_HASH);
                        txtdwellresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtdwelltime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editdwelltime.setText("0");
                        txtdwellresp.setText(R.string.G_ERROR);
                        txtdwellresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtdwelltime.setTextColor(resources.getColor(R.color.colorErrorRed));

                    }
                    txtdwelltime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
               /* case "Reset All Energy":
                    if (result) {
                        if (response.equals(1)) {
                            switchresetallenergy.setChecked(true);
                            switchresetallenergy.setText("AUTO");
                            txtresetallenergytime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        } else {
                            switchresetallenergy.setChecked(false);
                            switchresetallenergy.setText("MANUAL");
                            txtresetallenergytime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        }
                        txtresetallenergyresp.setText(R.string.COLON_OK_HASH);
                        txtresetallenergyresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtresetallenergytime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        switchresetallenergy.setChecked(false);
                        switchresetallenergy.setText("UN-KNOWN");
                        txtresetallenergyresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtresetallenergytime.setTextColor(resources.getColor(R.color.colorErrorRed));

                        txtresetallenergyresp.setText(R.string.G_ERROR);
                    }
                    txtresetallenergytime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;*/

                case "Unbalance Current":
                    if (result) {
                        editunbalancecurrent.setText(String.valueOf(response));
                        txtunbalancecurrentresp.setText(R.string.COLON_OK_HASH);
                        txtunbalancecurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtunbalancecurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editunbalancecurrent.setText("0");
                        txtunbalancecurrentresp.setText(R.string.G_ERROR);
                        txtunbalancecurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtunbalancecurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtunbalancecurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Voltage Unbalance":
                    if (result) {
                        editvoltageunbal.setText(String.valueOf(response));
                        txtvoltageunbalresp.setText(R.string.COLON_OK_HASH);
                        txtvoltageunbalresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtvoltageunbaltime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        editvoltageunbal.setText("0");
                        txtvoltageunbalresp.setText(R.string.G_ERROR);
                        txtvoltageunbalresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtvoltageunbaltime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtvoltageunbaltime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Maximum Start/Stop per hour":
                    if (result) {
                        editmaxstartorstop.setText(String.valueOf(response));
                        txtmaxstartorstopresp.setText(R.string.COLON_OK_HASH);
                        txtmaxstartorstopresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtmaxstartorstoptime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        editmaxstartorstop.setText("0");
                        txtmaxstartorstopresp.setText(R.string.G_ERROR);
                        txtmaxstartorstopresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtmaxstartorstoptime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtmaxstartorstoptime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "Set to Default":
                    if (result) {
                        if (response.equals(1)) {
                            switchsettodefault.setChecked(true);
                            switchsettodefault.setText("Set all parameters to default");

                            txtsettodefaulttime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        } else {
                            switchsettodefault.setChecked(false);
                            switchsettodefault.setText("No action");
                            txtsettodefaulttime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        }
                        txtsettodefaultresp.setText(R.string.COLON_OK_HASH);
                        txtsettodefaultresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtsettodefaulttime.setTextColor(resources.getColor(R.color.colorSucessGreen));


                    } else {
                        switchsettodefault.setChecked(false);
                        switchsettodefault.setText("UN-KNOWN");
                        txtsettodefaulttime.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsettodefaultresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsettodefaultresp.setText(R.string.G_ERROR);
                    }
                    txtsettodefaulttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;


                case "Energy Log Interval":
                    if (result) {
                        editenergylog.setText(String.valueOf(response));
                        txtenergylogresp.setText(R.string.COLON_OK_HASH);
                        txtenergylogresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtenergylogtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editenergylog.setText("0");
                        txtenergylogresp.setText(R.string.G_ERROR);
                        txtenergylogresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtenergylogtime.setTextColor(resources.getColor(R.color.colorErrorRed));


                    }
                    txtenergylogtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Operating Frequency":
                    if (result) {
                        if (response.equals(1)) {
                            switoperatingfreqency.setChecked(true);
                            switoperatingfreqency.setText("60");
                            txtoperatingfreqtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        } else {
                            switoperatingfreqency.setChecked(false);
                            switoperatingfreqency.setText("50");
                            txtoperatingfreqtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                        }
                        txtoperatingfreqresp.setText(R.string.COLON_OK_HASH);
                        txtoperatingfreqresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtoperatingfreqtime.setTextColor(resources.getColor(R.color.colorSucessGreen));


                    } else {
                        switoperatingfreqency.setChecked(false);
                        switoperatingfreqency.setText("UN-KNOWN");
                        txtoperatingfreqtime.setTextColor(resources.getColor(R.color.colorErrorRed));

                        txtoperatingfreqresp.setTextColor(resources.getColor(R.color.colorErrorRed));

                        txtoperatingfreqresp.setText(R.string.G_ERROR);
                    }
                    txtoperatingfreqtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Sensor Mode":
                    if (result) {
                        SensorMode_Data data = SensorMode_Data.GetMatchingRecordForTheId(response);
                        if (data == null) {
                            sensormodespinner.setSelection(0);
                            txtsensormoderesp.setText(R.string.G_ERROR);
                            txtsensormoderesp.setTextColor(resources.getColor(R.color.colorErrorRed));
                            txtsensormodetime.setTextColor(resources.getColor(R.color.colorErrorRed));
                        } else {
                            sensormodespinner.setSelection(sensorMode_dataArrayAdapter.getPosition(data.getSensor_Name()));
                            txtsensormoderesp.setText(R.string.COLON_OK_HASH);
                            txtsensormoderesp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                            txtsensormodetime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        }
                    } else {
                        sensormodespinner.setSelection(0);
                        txtsensormoderesp.setText(R.string.G_ERROR);
                        txtsensormoderesp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsensormodetime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtsensormodetime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;

                case "Sensor Polarity":
                    if (result) {
                        editsensorpolarity.setText(String.valueOf(response));
                        txtsensorpolarityresp.setText(R.string.COLON_OK_HASH);
                        txtsensorpolarityresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtsensorpolaritytime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editsensorpolarity.setText("0");
                        txtsensorpolarityresp.setText(R.string.G_ERROR);
                        txtsensorpolarityresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtsensorpolaritytime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtsensorpolaritytime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Ground Fault Current":
                    if (result) {
                        double f = response / 10.0;
                        editgroundfaultcurrent.setText(String.valueOf(f));
                        txtgroundfaultcurrentresp.setText(R.string.COLON_OK_HASH);
                        txtgroundfaultcurrentresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtgroundfaultcurrenttime.setTextColor(resources.getColor(R.color.colorSucessGreen));
                    } else {
                        editgroundfaultcurrent.setText("0");
                        txtgroundfaultcurrentresp.setText(R.string.G_ERROR);
                        txtgroundfaultcurrentresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtgroundfaultcurrenttime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtgroundfaultcurrenttime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
                case "Flow Calibration":
                    if (result) {
                        editflowcalibration.setText(String.valueOf(response));
                        txtflowcalibrationresp.setText(R.string.COLON_OK_HASH);
                        txtflowcalibrationresp.setTextColor(resources.getColor(R.color.colorSucessGreen));
                        txtflowcalibrationtime.setTextColor(resources.getColor(R.color.colorSucessGreen));

                    } else {
                        editflowcalibration.setText("0");
                        txtflowcalibrationresp.setText(R.string.G_ERROR);
                        txtflowcalibrationresp.setTextColor(resources.getColor(R.color.colorErrorRed));
                        txtflowcalibrationtime.setTextColor(resources.getColor(R.color.colorErrorRed));
                    }
                    txtflowcalibrationtime.setText(DateFormat.getDateTimeInstance().format(currentTime));
                    break;
            }
            if (HideProgressbar) {
                Utils.HideProgressDialog((Activity) CURRENT_CONTEXT, pd);
            }
        }
    }
}
