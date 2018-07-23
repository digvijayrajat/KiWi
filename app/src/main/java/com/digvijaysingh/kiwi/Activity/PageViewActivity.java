package com.digvijaysingh.kiwi.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.digvijaysingh.kiwi.R;

import java.net.MalformedURLException;
import java.net.URL;


public class PageViewActivity extends AppCompatActivity {
    WebView wb;
    private ImageView ivClose;




    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_view);
        BindViews();
    }

    private void BindViews() {
        wb=(WebView)findViewById(R.id.webView);
        ivClose=findViewById(R.id.ivClose);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wb.getSettings().setBuiltInZoomControls(false);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
        wb.setWebViewClient(new HelloWebViewClient());
        try {
            wb.loadUrl(String.valueOf(new URL(getIntent().getStringExtra("url"))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setListners();
    }




    private void setListners() {
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
