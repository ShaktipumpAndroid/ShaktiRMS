package com.shaktipumps.shakti_rms.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
//import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.other.AppCrashReport;
import com.shaktipumps.shakti_rms.other.AppEnvironment;
import com.shaktipumps.shakti_rms.other.AppPreference;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class PaymentStartActivityPayu extends AppCompatActivity {

    public static final String TAG = "MainActivity : ";
    private boolean isDisableExitConfirmation = false;
    private String userMobile, userEmail;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private AppPreference mAppPreference;
    private AppCompatRadioButton radio_btn_theme_purple, radio_btn_theme_pink, radio_btn_theme_green, radio_btn_theme_grey;

    private Button payNowButton;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;

    private String mPlanAmt, mPlanDESC, mPlanID, mPlanDuration;
    private String mUserID, mUsername;
    private int clientid;

    private SharedPreferences pref;
    private SharedPreferences.Editor editorrms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_start_payu);
        mAppPreference = new AppPreference();

        pref = this.getSharedPreferences("MyPref", 0);
        editorrms = pref.edit();

        mUserID = pref.getString("key_muserid", "invalid_muserid");
        mUsername = pref.getString("key_login_username", "key_login_username");
        // check org client id
        clientid = Integer.parseInt(pref.getString("key_clientid", "0")); // if invalid use 0

        mPlanAmt = getIntent().getStringExtra("mPlanAmt");
        mPlanDESC = getIntent().getStringExtra("mPlanDESC");
        mPlanID = getIntent().getStringExtra("mPlanID");
        mPlanDuration = getIntent().getStringExtra("mPlanDuration");

        /*
        *  editor.putString("key_login_username", mInfoArray[3]);
                        editor.putString("key_mobile_number", mInfoArray[4]);
                        editor.putString("key_muserid", mInfoArray[1]);
                        editor.putString("key_mparentid", mInfoArray[2]);
                        editor.putString("key_clientid", mInfoArray[5]);*/

      //  mAppPreference.setOverrideResultScreen(false);
        //selectSandBoxEnv();
      //  selectProdEnv();
    }


    private void selectProdEnv() {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((AppCrashReport) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);
              /*  editor = settings.edit();
                editor.putBoolean("is_prod_env", true);
                editor.apply();

                if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
                    logoutBtn.setVisibility(View.VISIBLE);
                } else {
                    logoutBtn.setVisibility(View.GONE);
                }
*/
                setupCitrusConfigs();
            }
        }, AppPreference.MENU_DELAY);
    }

    /**
     * This function sets the mode to SANDBOX in Shared Preference
     */
    private void selectSandBoxEnv() {
       // ((AppCrashReport) getApplication()).setAppEnvironment(AppEnvironment.SANDBOX);
       /* editor = settings.edit();
        editor.putBoolean("is_prod_env", false);
        editor.apply();

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);

        }*/
      //  setupCitrusConfigs();
    }


    private void setupCitrusConfigs() {
        AppEnvironment appEnvironment = ((AppCrashReport) getApplication()).getAppEnvironment();
        if (appEnvironment == AppEnvironment.PRODUCTION) {
            Toast.makeText(PaymentStartActivityPayu.this, "Environment Set to Production", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PaymentStartActivityPayu.this, "Environment Set to SandBox", Toast.LENGTH_SHORT).show();
        }

      //  launchPayUMoneyFlow();
    }

    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Shaktipumps");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle(NewSolarVFD.DEVICE_NUMBER_PAYMENT);

        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(mPlanAmt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = System.currentTimeMillis() + "";
        //String txnId = "TXNID720431525261327973";
        String phone = "9009555317";
        String productName = NewSolarVFD.DEVICE_NUMBER_PAYMENT;
        String firstName = mUsername;
        String email = "vikas.gothi@shaktipumps.com";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        AppEnvironment appEnvironment = ((AppCrashReport) getApplication()).getAppEnvironment();
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
          //  mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
            //    generateHashFromServer(mPaymentParams);

            /*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */
          //  mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

          /*  if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,PaymentStartActivityPayu.this, R.style.AppTheme_blue,mAppPreference.isOverrideResultScreen());
                //PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,PaymentStartActivityPayu.this, AppPreference.selectedTheme,mAppPreference.isOverrideResultScreen());
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,PaymentStartActivityPayu.this, R.style.AppTheme_blue, mAppPreference.isOverrideResultScreen());
            }
*/
            finish();
        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
         //   payNowButton.setEnabled(true);
        }
    }

    /**
     * Thus function calculates the hash for transaction
     *
     * @param paymentParam payment params of transaction
     * @return payment params along with calculated merchant hash
     */
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = ((AppCrashReport) getApplication()).getAppEnvironment();
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

}
