

package com.example.bpear.echoradio;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {


    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnLogin;
    private TextView btnSignup, btnReset;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        }

        // set the view now


        inputEmail = (EditText) v.findViewById(R.id.logEmail);
        inputPassword = (EditText) v.findViewById(R.id.logpassword);
        progressBar = (ProgressBar) v.findViewById(R.id.login_progress);
        btnSignup = v.findViewById(R.id.create_ccount);
        btnLogin = (Button) v.findViewById(R.id.email_sign_in_button);
        btnReset = v.findViewById(R.id.btn_reset_password);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.screen_area1, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.screen_area, new ForgotpasswordFragment())
//                        .addToBackStack(null)
//                        .commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Enter Email Address");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Enter Password");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(getActivity(), getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                            }
                        });
            }
        });
        return v;
    }
}



