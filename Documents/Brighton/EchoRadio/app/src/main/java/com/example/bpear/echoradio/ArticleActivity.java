package com.example.bpear.echoradio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import static java.security.AccessController.getContext;

public class ArticleActivity extends AppCompatActivity {
    private Toolbar articalToolbar;
    private Button tokenButt;
    private Snackbar snackbar;
    private static final String TAG = "Article";
    public static final String BLOG_URL = "http://www.echodailynews.com/category/world/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articalToolbar = findViewById(R.id.article_toolbar);
        articalToolbar.setTitle("Articles");

        WebView webView = (WebView) findViewById(R.id.webview);
        TextView tvError = (TextView) findViewById(R.id.tv_error);


        if (NetworkState.isOnline(this)) {
            tvError.setVisibility(View.GONE);
            webView.loadUrl(BLOG_URL);
            webView.getSettings().setJavaScriptEnabled(true);
        } else {
            webView.setVisibility(View.GONE);
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_radio:
                    startActivity(new Intent(ArticleActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_articles:
                    finish();
                    return true;
            }
            return false;
        }
    };
}
