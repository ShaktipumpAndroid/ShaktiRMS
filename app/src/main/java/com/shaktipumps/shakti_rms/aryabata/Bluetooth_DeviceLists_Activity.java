package com.shaktipumps.shakti_rms.aryabata;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.rilixtech.materialfancybutton.MaterialFancyButton;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.ActivitySelectionDataWay;
import com.shaktipumps.shakti_rms.aryabata.adapters.BluetoothDetailsAdapter;
import com.shaktipumps.shakti_rms.aryabata.classes.BluetoothDetails;

import java.util.ArrayList;
import java.util.Set;




public class Bluetooth_DeviceLists_Activity extends AppCompatActivity {
    public static String EXTRA_ADDRESS = "device_address";
    public static String EXTRA_NAME = "device_name";
    Context c;
    Button btnPaired;
    MaterialFancyButton materialFancybtnPaired;
    private Toolbar toolbar;

    private ImageView imgBackBTNID;
    private ImageView imgLogoutBTNID;
    private TextView txtTopHEadingID;

    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    private BluetoothAdapter myBluetooth = null;
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            TextView btname = (TextView) v.findViewById(R.id.btname);
            TextView btmac = (TextView) v.findViewById(R.id.btmac);

            String address = btmac.getText().toString();
            String name = btname.getText().toString();

            Intent i = new Intent(Bluetooth_DeviceLists_Activity.this, Dashboard_Activity.class);

            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Pref", 0);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(EXTRA_ADDRESS, address);
            editor.putString(EXTRA_NAME, name);
            editor.apply();

            i.putExtra(EXTRA_ADDRESS, address);
            i.putExtra(EXTRA_NAME, name);
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_device_screen);

        try {

            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();


            myBluetooth = BluetoothAdapter.getDefaultAdapter();

            if (myBluetooth == null) {
                Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();
                finish();
            } else if (!myBluetooth.isEnabled()) {
                Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBTon, 1);
            }
            c = getApplicationContext();
            pairedDevicesList();


            materialFancybtnPaired = (MaterialFancyButton) findViewById(R.id.button);
            materialFancybtnPaired.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pairedDevicesList();

                }
            });

            imgBackBTNID = (ImageView) findViewById(R.id.imgBackBTNID);
            imgLogoutBTNID = (ImageView) findViewById(R.id.imgLogoutBTNID);
            txtTopHEadingID = (TextView) findViewById(R.id.txtTopHEadingID);

            imgBackBTNID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            imgLogoutBTNID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editor.putString("key_login", "N");
                    editor.putString("key_OTP", "9999");
                    editor.putString("key_mobile_number", "9999999999");

                    editor.putString("key_otp_for_user", "9999");
                    editor.putString("key_mparentid", "9999");
                    editor.putString("key_muserid","9999");
                    // editor.putString("key_clientid","9999");
                    editor.putString("key_clientid","0");
                    editor.putString("key_login_username", "Invalid User");
                    editor.putString("key_clientid_for_map","9999");
                    editor.putString("key_clientid_for_data_report","9999");

                    SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("user_comm_option", "");
                    editor.apply();
                    SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "0");
                    Snackbar snackbar = Snackbar.make(v, "Logout Successfully!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Intent intent = new Intent(getApplicationContext(), ActivitySelectionDataWay.class);
                    startActivity(intent);
                    finish();
                }
            });

        /*    android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setDisplayHomeAsUpEnabled(true);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void pairedDevicesList() {
        Set<BluetoothDevice> pairedDevices = myBluetooth.getBondedDevices();
        ArrayList<BluetoothDetails> list = new ArrayList<>();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice bt : pairedDevices) {
                BluetoothDetails details = new BluetoothDetails();
                details.setBluetoothName(bt.getName());
                details.setBluetoothAddress(bt.getAddress());
                list.add(details);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<BluetoothDetails> adapter = new BluetoothDetailsAdapter(c, list);
        ListView listV = (ListView) findViewById(R.id.listView);
        listV.setAdapter(adapter);
        listV.setOnItemClickListener(myListClickListener);

    }
}
