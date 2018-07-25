/*
package com.example.bpear.echoradio;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


*/
/**
 * A simple {@link Fragment} subclass.
 *//*

public class ArticleFragment extends Fragment {
    private Toolbar articalToolbar;
    private Button tokenButt;
    private Snackbar snackbar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Article";
    public static final String BLOG_URL = "http://www.echodailynews.com/category/world/";

    public ArticleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_article, container, false);
        articalToolbar = v.findViewById(R.id.article_toolbar);
        articalToolbar.setTitle("Articles");
        mAuth = FirebaseAuth.getInstance();
        WebView webView = (WebView) v.findViewById(R.id.webview);
        TextView tvError = (TextView) v.findViewById(R.id.tv_error);


        if (NetworkState.isOnline(getContext())) {
            tvError.setVisibility(View.GONE);
            webView.loadUrl(BLOG_URL);
            webView.getSettings().setJavaScriptEnabled(true);
        } else {
            webView.setVisibility(View.GONE);
        }

        BottomNavigationView navigation = (BottomNavigationView) v.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        return v;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_radio:
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    return true;
                case R.id.navigation_articles:
                    return true;
                case R.id.sign_out:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.screen_area, new LoginFragment())
                                .addToBackStack(null)
                                .commit();
                        getActivity().finish();
                    }
                }
            };
            return false;
        }

    };
};

*/
