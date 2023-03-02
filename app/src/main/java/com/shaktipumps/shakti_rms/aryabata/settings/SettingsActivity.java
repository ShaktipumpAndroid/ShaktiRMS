package com.shaktipumps.shakti_rms.aryabata.settings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.shaktipumps.shakti_rms.BuildConfig;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.aryabata.Dashboard_Activity;
import com.shaktipumps.shakti_rms.aryabata.Login_Activity;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth;
import com.shaktipumps.shakti_rms.aryabata.adapters.User_auth_response;
import com.shaktipumps.shakti_rms.aryabata.adapters.Utils;


public class SettingsActivity extends AppCompatPreferenceActivity {
    Context c;
    SharedPreferences sharedPref;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MainPreferenceFragment mainPreferenceFragment = new MainPreferenceFragment();
        c = getApplicationContext();
        pd = new ProgressDialog(SettingsActivity.this);
        pd.setCancelable(false);
        mainPreferenceFragment.c = c;
        getFragmentManager().beginTransaction().replace(android.R.id.content, mainPreferenceFragment).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settingsmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_refresh:
                Boolean hasInternet = Utils.IsConnectedNHasInternet(c);
                if (!hasInternet) {
                    CloseProgressbar();
                    Toast.makeText(c, "No Internet!", Toast.LENGTH_SHORT).show();
                    break;
                }
                ShowProgressbar("Re-loading your settings.");
                sharedPref = c.getSharedPreferences("Pref", 0);
                String user_auth_string = sharedPref.getString("user_auth", "");
                User_auth user_auth = User_auth_response.parseJSONForSingle(user_auth_string);
                String username = user_auth.getEmailId();
                String password = user_auth.getPassword();
                AsyncHttpClient client = new AsyncHttpClient();
                client.get
                        (
                                Utils.adminAppURL + "/User_Auth?Userid=" + username + "&Password=" + password,
                                //Utils.adminAppURL + "/api/User_Auth/GetUser_AuthWithUserDetails/" + username + "/" + password,
                                new TextHttpResponseHandler() {
                                    @Override
                                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                                        CloseProgressbar();
                                        Toast.makeText(c, "Technical Error(1032), Contact Administrator", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("user_auth", responseString);
                                        editor.apply();
                                        CloseProgressbar();
                                        finish();
                                        Intent i = new Intent(SettingsActivity.this, SettingsActivity.class);
                                        startActivityForResult(i, 0);
                                    }
                                }
                        );
                break;
            case R.id.action_export:
                Toast.makeText(c, "Action Disabled", Toast.LENGTH_LONG).show();

                break;
            case R.id.action_import:
                Toast.makeText(c, "Action Disabled", Toast.LENGTH_LONG).show();

                break;
            case android.R.id.home:
                super.onBackPressed();
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {

        Intent i = new Intent(SettingsActivity.this, Dashboard_Activity.class);
        startActivity(i);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void ShowProgressbar(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.setMessage(msg);
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

    public static class MainPreferenceFragment extends PreferenceFragment {
        Context c;
        SharedPreferences sharedPref;

        public static void sendFeedback(Context context) {
            String body = null;
            try {
                body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                        Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                        "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@aryabhatta.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
            intent.putExtra(Intent.EXTRA_TEXT, body);
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            sharedPref = c.getSharedPreferences("Pref", 0);
            String user_auth_string = sharedPref.getString("user_auth", "");
            final User_auth user_auth = User_auth_response.parseJSONForSingle(user_auth_string);

            Preference app_version = findPreference("app_version");
            app_version.setSummary(BuildConfig.VERSION_NAME);

            Preference device_serial_number = findPreference("device_serial_number");
            device_serial_number.setSummary(user_auth.getMobileSerialNumber());

            Preference emailId = findPreference("email_id");
            emailId.setSummary(user_auth.getEmailId());

            Preference mobile_number = findPreference("mobile_number");
            mobile_number.setSummary(user_auth.getMobileNumber());

            SwitchPreference sw_action = (SwitchPreference) findPreference("sw_action");
            sw_action.setChecked(false);

            SwitchPreference sw_set_parameters = (SwitchPreference) findPreference("sw_set_parameters");
            sw_set_parameters.setChecked(false);

            SwitchPreference sw_view_parameters = (SwitchPreference) findPreference("sw_view_parameters");
            sw_view_parameters.setChecked(false);

            SwitchPreference sw_view_logs = (SwitchPreference) findPreference("sw_view_logs");
            sw_view_logs.setChecked(false);

            sw_action.setChecked(user_auth.isPerformActions());
            sw_set_parameters.setChecked(user_auth.isSetParameters());
            sw_view_parameters.setChecked(user_auth.isViewParameters());
            sw_view_logs.setChecked(user_auth.isViewLogs());

            if (Utils.IsConnectedNHasInternet(c)) {
                Preference change_Password = findPreference("change_Password");
                change_Password.setEnabled(false);
                change_Password.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(final Preference preference) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(preference.getContext());
                        builder.setCancelable(false);
                        builder.setMessage("Are you sure that you want to reset your password?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(preference.getContext());
                                builder2.setCancelable(false);
                                builder2.setMessage("You can't log-in into your app until the administartor approves your request?");
                                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AsyncHttpClient client = new AsyncHttpClient();
                                        client.post
                                                (
                                                        Utils.adminAppURL + "/RequestUserPasswordChange?id=" + user_auth.getId(),
                                                        //Utils.adminAppURL + "/api/User_Auth/RequestUserPasswordChange/" + user_auth.getId(),
                                                        new TextHttpResponseHandler() {
                                                            @Override
                                                            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                                                                Toast.makeText(c, "Technical Error(1033), Contact Administrator", Toast.LENGTH_LONG).show();
                                                            }

                                                            @Override
                                                            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                                                                if (responseString.equals("true")) {
                                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                                    editor.clear();
                                                                    editor.apply();
                                                                    Toast.makeText(c, "Your Request for change of password has been submitted successfully to the Administrator, and you can't use your app until it is approved!", Toast.LENGTH_LONG).show();
                                                                    Intent intent = new Intent(c, Login_Activity.class);
                                                                    startActivity(intent);
                                                                } else {
                                                                    Toast.makeText(c, "Technical Error(1034), Contact Administrator", Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });

                                    }
                                });
                                builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alert2 = builder2.create();
                                alert2.show();
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
                        return false;
                    }
                });
            }

            String user_comm_option = sharedPref.getString("user_comm_option", "");

            final CheckBoxPreference connect_bluetooth = (CheckBoxPreference) findPreference("connect_bluetooth");
            final CheckBoxPreference connect_usb = (CheckBoxPreference) findPreference("connect_usb");

            if (user_comm_option.length() > 0) {
                if (user_comm_option.equals("B")) {
                    connect_bluetooth.setChecked(true);
                    connect_usb.setChecked(false);
                } else if (user_comm_option.equals("U")) {
                    connect_usb.setChecked(true);
                    connect_bluetooth.setChecked(false);
                }
            }

            connect_bluetooth.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    if (((Boolean) o)) {
                        connect_usb.setChecked(false);
                        editor.putString("user_comm_option", "B");
                    } else {
                        editor.putString("user_comm_option", "");
                    }
                    editor.apply();
                    return true;
                }
            });


            connect_usb.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    if (((Boolean) o)) {
                        connect_bluetooth.setChecked(false);
                        editor.putString("user_comm_option", "U");
                    } else {
                        editor.putString("user_comm_option", "");
                    }
                    editor.apply();
                    return true;
                }
            });


            Preference feedback = findPreference("sendmail");

            feedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity());
                    return true;
                }
            });

        }

    }
}
