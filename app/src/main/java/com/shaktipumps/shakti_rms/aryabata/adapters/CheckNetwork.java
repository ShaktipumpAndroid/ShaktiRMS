package com.shaktipumps.shakti_rms.aryabata.adapters;

import android.os.AsyncTask;

import java.net.InetAddress;

public class CheckNetwork extends AsyncTask<String, Void, Boolean> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.co.in"); //You can replace it with your domain
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
