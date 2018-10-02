
package com.example.bpear.VoiceFM;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ArticleActivity extends AppCompatActivity {
    private Toolbar articalToolbar;
    private Button tokenButt;
    private Snackbar snackbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Article";
    public static final String BLOG_URL = "http://www.echodailynews.com/category/world/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        articalToolbar = findViewById(R.id.article_toolbar);
        articalToolbar.setTitle("Articles");
        mAuth = FirebaseAuth.getInstance();
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
                    return true;
                case R.id.sign_out:
                    AlertDialog.Builder builder = new AlertDialog.Builder(ArticleActivity.this);

                    builder.setTitle("Logout");
                    builder.setMessage("Are you sure you want to logout?");

                    builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    android.support.v7.app.AlertDialog dialog = builder.show();
            }

            // this listener will be called when there is change in firebase user session
            FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        // user auth state is changed - user is null
                        // launch login activity
                        startActivity(new Intent(ArticleActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            };
            return false;
        }

    };

}

