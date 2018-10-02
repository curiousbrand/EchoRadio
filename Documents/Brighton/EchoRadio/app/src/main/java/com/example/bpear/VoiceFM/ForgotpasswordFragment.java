
package com.example.bpear.VoiceFM;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotpasswordFragment extends Fragment {

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    public ForgotpasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forgotpassword, container, false);

        inputEmail = (EditText) v.findViewById(R.id.email);
        btnReset = (Button) v.findViewById(R.id.btn_reset_password);
        btnBack = (Button) v.findViewById(R.id.btn_back);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id
                .coordinatorLayout);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.screen_area, new LoginFragment())
                .addToBackStack(null)
                .commit();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                  //  Toast.makeText(getActivity(), "Enter your registered email ", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar2 = Snackbar.make(coordinatorLayout,"Email cannot be empty.", Snackbar.LENGTH_LONG);
                    View sb2View = snackbar2.getView();
                    TextView textView2 = sb2View.findViewById(android.support.design.R.id.snackbar_text);
                    textView2.setTextColor(Color.RED);
                    snackbar2.show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   // Toast.makeText(getActivity(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar4 = Snackbar
                                            .make(coordinatorLayout, "An email has been sent with further instructions.", Snackbar.LENGTH_LONG);
                                    View sb4View = snackbar4.getView();
                                    TextView txt4View = sb4View.findViewById(android.support.design.R.id.snackbar_text);
                                    txt4View.setTextColor(Color.WHITE);
                                    snackbar4.show();

                                } else {
                                    // Toast.makeText(getActivity(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(coordinatorLayout, "We couldn’t find a user with that email address.", Snackbar.LENGTH_LONG);

                                    // Changing action button text color
                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.RED);
                                    snackbar.show();
                                }


                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
        return v;
    }
}



