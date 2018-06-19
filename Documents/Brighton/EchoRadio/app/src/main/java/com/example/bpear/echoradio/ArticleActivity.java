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
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class ArticleActivity extends AppCompatActivity {
    private Toolbar articalToolbar;
    private Button tokenButt;
    private Snackbar snackbar;
    private static final String TAG = "Article";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articalToolbar = findViewById(R.id.article_toolbar);
        articalToolbar.setTitle("Articles");

        tokenButt = findViewById(R.id.tokenId);
        tokenButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG,"Token: " + token);
                Toast.makeText(ArticleActivity.this, token, Toast.LENGTH_LONG).show();
            }
        });

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
