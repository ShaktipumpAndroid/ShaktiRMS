package com.shaktipumps.shakti_rms.aryabata;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth_response;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;
import com.shaktipumps.shakti_rms.aryabata.splash_demo.Config;
import com.shaktipumps.shakti_rms.aryabata.splash_demo.DefaultIntro;

import cz.msebera.android.httpclient.Header;


public class Login_Activity extends AppCompatActivity {
    Button b1;
    EditText txtUsername;
    EditText txtPassword;
    ProgressDialog pd;

    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen1);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        txtUsername = (EditText) findViewById(R.id.email);
        txtPassword = (EditText) findViewById(R.id.password);
        Intent intent = getIntent();


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Config.FLAG, Context.MODE_PRIVATE);
                if (sharedPreferences.getBoolean(Config.FLAG, true)) {
                    startActivity(new Intent(Login_Activity.this, DefaultIntro.class));
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    e.putBoolean(Config.FLAG, false);
                    e.apply();
                }
            }
        });

        t.start();


        pd = new ProgressDialog(Login_Activity.this);
        pd.setCancelable(false);
        c = getApplicationContext();

        SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
        String userdata = sharedPref.getString("user_auth", "");
        if (userdata.length() > 0) {
            String username = sharedPref.getString("user_auth_email", "");
            String userPassword = sharedPref.getString("user_auth_password", "");
            Intent i = new Intent(c, Login_Authentication.class);
            i.putExtra("user_auth_email", username);
            i.putExtra("user_auth_password", userPassword);
            startActivity(i);
        }


        b1 = (Button) findViewById(R.id.loginbutton);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                ShowProgressbar();
                try {
                    Boolean hasInternet = Utils.IsConnectedNHasInternet(c);
                    if (!hasInternet) {
                        CloseProgressbar();
                        Snackbar snackbar = Snackbar.make(v, "No Internet!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        return;
                    }

                    String username = txtUsername.getText().toString();
                    String password = txtPassword.getText().toString();

                    if (username.length() <= 0) {
                        CloseProgressbar();
                        Toast.makeText(c, "Username Should not be empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.length() <= 0) {
                        CloseProgressbar();
                        Toast.makeText(c, "Password Should not be empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get
                            (

                                    Utils.adminAppURL + "/User_Auth?Userid=" + username + "&Password=" + password,
                                    //Utils.adminAppURL + "/api/User_Auth/GetUser_AuthWithUserDetails/" + username + "/" + password,
                                    new TextHttpResponseHandler() {
                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                            CloseProgressbar();
                                            Toast.makeText(c, "Technical Error(1032), Contact Administrator", Toast.LENGTH_LONG).show();
                                        }

                                        @SuppressLint("HardwareIds")
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                            User_auth user_auth = User_auth_response.parseJSONForSingle(responseString);
                                            if (user_auth == null) {
                                                CloseProgressbar();
                                                Toast.makeText(c, "Invalid User Credentials/Inactive User", Toast.LENGTH_LONG).show();
                                            } else {
                                                SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                user_auth.setMobileSerialNumber(Build.SERIAL);
                                                Gson gson = new Gson();
                                                String modifiedString = gson.toJson(user_auth);
                                                editor.putString("user_auth", modifiedString);
                                                editor.putString("user_auth_email", user_auth.getEmailId());
                                                editor.putString("user_auth_password", user_auth.getPassword());
                                                editor.apply();
                                                AsyncHttpClient client2 = new AsyncHttpClient();
                                                client2.post
                                                        (
                                                                Utils.adminAppURL + "/SetUser_Serial?id=" + user_auth.getId()+ "&serial=" + Build.SERIAL,
                                                                //Utils.adminAppURL + "/api/User_Auth/SetUser_Serial/" + user_auth.getId() + "/" + Build.SERIAL,
                                                                new TextHttpResponseHandler() {
                                                                    @Override
                                                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                                        CloseProgressbar();
                                                                        Toast.makeText(c, "Technical Error(1031), Contact Administrator", Toast.LENGTH_LONG).show();
                                                                    }

                                                                    @Override
                                                                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                                                        CloseProgressbar();
                                                                        if (responseString.equals("true")) {
                                                                            Intent i = new Intent(c, Options_Activity.class);
                                                                            startActivity(i);
                                                                        } else {
                                                                            Toast.makeText(c, "Technical Error(1030), Contact Administrator", Toast.LENGTH_LONG).show();
                                                                        }
                                                                    }
                                                                });
                                            }
                                        }
                                    });

                } catch (Exception ec) {
                    Toast.makeText(c, "Technical Error(1029), Contact Administrator", Toast.LENGTH_LONG).show();
                    ec.printStackTrace();
                }
            }
        });
    }


    private void ShowProgressbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.setMessage("Logging In...");
                pd.show();
            }
        });
    }

    private void CloseProgressbar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.hide();
            }
        });
    }


    @Override
    public void onBackPressed() {
       // exit();
        finish();
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
}
