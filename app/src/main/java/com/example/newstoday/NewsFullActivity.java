package com.example.newstoday;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsFullActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);
        webView= findViewById(R.id.webview);


        String url= getIntent().getStringExtra("url");

        WebSettings webSettings= webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else
        super.onBackPressed();
    }
}