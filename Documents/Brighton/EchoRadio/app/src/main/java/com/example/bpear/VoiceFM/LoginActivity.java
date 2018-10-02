

package com.example.bpear.VoiceFM;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;



import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;




/**
 * A login screen that offers login via email/password.
 */


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup,btnLogin;
    private TextView btnReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);


        inputEmail = (EditText) findViewById(R.id.logEmail);
        inputPassword = (EditText) findViewById(R.id.logpassword);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        btnSignup = findViewById(R.id.create_ccount);
        btnLogin = (Button) findViewById(R.id.email_sign_in_button);
        btnReset = findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.screen_area, new LoginFragment())
                .addToBackStack(null)
                .commit();

       /* btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             startActivity(new Intent(LoginActivity.this, Register.class));
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.screen_area, new RegisterFragment() );
                        ft.addToBackStack(null);
                        ft.commit();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
//                getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, new ForgotpasswordFragment())
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
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
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
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}*/

    }
}