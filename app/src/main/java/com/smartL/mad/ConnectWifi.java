package com.smartL.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smartL.mad.recyclerView.MainDevicesHome;

public class ConnectWifi extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_wifi);
        Toast.makeText(ConnectWifi.this, "Make sure your mobile connect to smart device's wifi hotspot", Toast.LENGTH_LONG).show();

        getSupportActionBar().hide();
        getWindow().setBackgroundDrawableResource(R.drawable.gradientcolor);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        webView = (WebView) findViewById(R.id.wificon);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.loadUrl("http://192.168.4.1");

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                webView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

            }

//            @Override
//            public void onLoadResource(WebView view, String url) {
//                super.onLoadResource(view, url);
//                webView.setVisibility(View.INVISIBLE);
//                progressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(ConnectWifi.this, "Make sure your mobile connect to smart device's wifi hotspot", Toast.LENGTH_LONG).show();
//            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent(this, MainDevicesHome.class));
        finish();
    }
}
