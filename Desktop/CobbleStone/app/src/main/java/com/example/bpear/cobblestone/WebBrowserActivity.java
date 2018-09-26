package com.example.bpear.cobblestone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class WebBrowserActivity extends AppCompatActivity {
    private static final String TAG = "WEB";
    public static final String BLOG_URL = "https://www.google.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_browser);

        WebView webView = (WebView) findViewById(R.id.webview);
        TextView tvError = (TextView) findViewById(R.id.tv_error);


        if (NetworkState.isOnline(this)) {
            tvError.setVisibility(View.GONE);
            webView.loadUrl(BLOG_URL);
            webView.getSettings().setJavaScriptEnabled(true);
        } else {
            webView.setVisibility(View.GONE);
        }

    }
}
