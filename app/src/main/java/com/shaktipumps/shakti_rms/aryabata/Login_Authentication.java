package com.shaktipumps.shakti_rms.aryabata;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth_response;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;


public class Login_Authentication extends AppCompatActivity {
    Button buttonauth;
    EditText txtPassword;
    TextView txtUsername;
    TextView txtForgetPassword;
    Context c;
    ProgressDialog pd;


    String Username;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_authentication_screen1);

        c = getApplicationContext();

//        byte[] bytes = new byte[]{0x01, (byte) 0x80};
//        int i = Utils.ToInt16(bytes, 0);
//        int k = Utils.ToInt16Reverse(bytes, 0);

//        Data_Parameters data_parameters = Data_Parameters.ParseObject(new byte[]{0x03,0x2E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x7B, (byte)0xD4, 0x7A, 0x43, (byte)0xE1, 0x7A, 0x47, 0x42, 0x6E, 0x12, 0x03, 0x3B, 0x00, 0x00, 0x00, 0x00, (byte)0xEB, 0x11, (byte)0x83, 0x42, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x3F});

        txtPassword = (EditText) findViewById(R.id.auth_password);
        txtUsername = (TextView) findViewById(R.id.auth_email);
        buttonauth = (Button) findViewById(R.id.authlogin);
        txtForgetPassword = (TextView) findViewById(R.id.forgotpass);
        txtForgetPassword.setVisibility(View.GONE);

        Username = getIntent().getStringExtra("user_auth_email");
        Password = getIntent().getStringExtra("user_auth_password");

        pd = new ProgressDialog(Login_Authentication.this);
        pd.setCancelable(false);

        txtUsername.setText(Username);


        buttonauth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String cPassword = txtPassword.getText().toString();
                if (cPassword.length() <= 0) {
                    Toast.makeText(c, "Password Required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!cPassword.equals(Password)) {
                    Toast.makeText(c, "Invalid Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(c, Options_Activity.class);
                startActivity(i);
            }
        });

        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean hasInternet = Utils.IsConnectedNHasInternet(c);
                if (!hasInternet) {
                    Toast.makeText(c, "No Internet!", Toast.LENGTH_LONG).show();
                    return;
                }

                final SharedPreferences sharedPref = c.getSharedPreferences("Pref", 0);
                String user_auth_string = sharedPref.getString("user_auth", "");
                final User_auth user_auth = User_auth_response.parseJSONForSingle(user_auth_string);

                AlertDialog.Builder builder = new AlertDialog.Builder(Login_Authentication.this);
                builder.setCancelable(false);
                builder.setMessage("Are you sure that you want to reset your password?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.ShowProgressDialog(Login_Authentication.this, pd, "Submitting your request...");
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post
                                (
                                        Utils.adminAppURL + "/RequestUserPasswordChange?id=" + user_auth.getId(),
                                        //Utils.adminAppURL + "/api/User_Auth/RequestUserPasswordChange/" + user_auth.getId(),
                                        new TextHttpResponseHandler() {
                                            @Override
                                            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                                                Utils.HideProgressDialog(Login_Authentication.this, pd);
                                                Toast.makeText(c, "Technical Error(1033), Contact Administrator", Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                                                if (responseString.equals("true")) {
                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.clear();
                                                    editor.apply();
                                                    Utils.HideProgressDialog(Login_Authentication.this, pd);
                                                    Toast.makeText(c, "Forget password request submitted successfully to the Administrator, and you can't use your old password to Log-in!", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(c, Login_Activity.class);
                                                    startActivity(intent);
                                                } else {
                                                    Utils.HideProgressDialog(Login_Authentication.this, pd);
                                                    Toast.makeText(c, "Technical Error(1034), Contact Administrator", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
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
        });
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
}
