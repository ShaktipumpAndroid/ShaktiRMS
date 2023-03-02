package com.shaktipumps.shakti_rms.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shaktipumps.shakti_rms.GlobalClass.Constant;
import com.shaktipumps.shakti_rms.GlobalClass.SharedPreferencesUtil;
import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.activity.LoginActivity;
import com.shaktipumps.shakti_rms.activity.MainActivity;

import java.util.Map;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. AUTHModelData messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. AUTHModelData
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        System.out.println("remoteMessage.getFrom()==>>"+remoteMessage.getFrom().toString());
        System.out.println("remoteMessage.getData()==>>"+remoteMessage.getData());


        Map<String,String> data = remoteMessage.getData();
        if (data != null) {
           // String value = data.get("your_key");
            String typevalue = data.get("type");
            String textvalue = data.get("text");
            String titlevalue = data.get("title");

            sendNotification(textvalue, titlevalue);


            try {
                if(typevalue.equalsIgnoreCase("1"))
                {
                    editor.putString("key_login", "N");
                    editor.putString("key_OTP", "9999");
                    editor.putString("key_mobile_number", "9999999999");

                    editor.putString("key_otp_for_user", "9999");
                    editor.putString("key_mparentid", "9999");
                    editor.putString("key_muserid", "9999");
                    // editor.putString("key_clientid","9999");
                    editor.putString("key_clientid", "0");
                    editor.putString("key_login_username", "Invalid User");
                    editor.putString("key_clientid_for_map", "9999");
                    editor.putString("key_clientid_for_data_report", "9999");
                    SharedPreferencesUtil.setData(getApplicationContext(), Constant.CHECK_APP_DEVICE_TYPE, "0");

    //              editor.putString("key_time_zone_city","Invalid" );
    //              editor.putString("key_time_zone_short","Invalid");
    //              editor.putString("key_time_zone_long", "Invalid");
    //              editor.putString("key_time_zone_change", "Invalid");

                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    //Intent intent = new Intent(MainActivity.this, ActivitySelectionDataWay.class);
                    startActivity(intent);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Post your own Notification using NotificationCompat.Builder
            // or send the information to your UI
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true)
            {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.

        System.out.println("remoteMessage.getNotification()==>>"+remoteMessage.getNotification());
      //  if (remoteMessage.getNotification() != null)
        /*if (remoteMessage.getNotification() != null)
        {

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }*/
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    /*private void scheduleJob() {
        // [START dispatch_job]
        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance().beginWith(work).enqueue();
        // [END dispatch_job]
    }*/

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }


    private void sendNotification(String textMSG, String textName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
       // Uri sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.inflicted);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.load_blue)
                        //.setContentTitle(getString(R.string.app_name))
                        .setContentTitle(textName)
                        .setContentText(textMSG)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        //.setSound(sound)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }



}