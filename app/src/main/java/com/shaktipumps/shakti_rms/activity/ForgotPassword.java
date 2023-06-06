package com.shaktipumps.shakti_rms.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shaktipumps.shakti_rms.R;
import com.shaktipumps.shakti_rms.webservice.NewSolarVFD;

public class ForgotPassword extends AppCompatActivity {
    private Toolbar mToolbar;
    Context mContext ;
    WebView simpleWebView;

    WebView webView;

    SwipeRefreshLayout swipe;
    ProgressBar progressBar;
    Toolbar toolbar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mToolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        mContext = this;


//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(R.string.action_forgotPassword);



//        simpleWebView = (WebView) findViewById(R.id.simpleWebView);
//
//
//
//        try {
//            simpleWebView.setWebViewClient(new MyWebViewClient());
//            String url = NewSolarVFD.FORGOT_PASSWORD;
//            simpleWebView.getSettings().setJavaScriptEnabled(true);
//            simpleWebView.loadUrl(url); // load a web page in a web view
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }


        progressBar = (ProgressBar) findViewById(R.id.awv_progressBar);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        LoadWeb();

        progressBar.setMax(100);
        progressBar.setProgress(1);


        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {


                progressBar.setProgress(progress);
            }
        });


        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }


            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                webView.loadUrl("file:///android_asset/error.html");
            }

            public void onLoadResource(WebView view, String url) { //Doesn't work
                //swipe.setRefreshing(true);
            }

            public void onPageFinished(WebView view, String url) {

                //Hide the SwipeReefreshLayout
                progressBar.setVisibility(View.GONE);
                swipe.setRefreshing(false);
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

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    public void LoadWeb() {

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setAppCacheEnabled(true);
        webView.loadUrl(NewSolarVFD.FORGOT_PASSWORD);
        swipe.setRefreshing(true);
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

}
