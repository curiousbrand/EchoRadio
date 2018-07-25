

package com.example.bpear.echoradio;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;




public class RegisterFragment extends Fragment {

    private Button createaccnt, signin;
    private EditText emailField;
    private EditText passwordField;
    private EditText nameField;
    private CheckBox checkBox;
    private ProgressBar progressBar;
    String Name;
    boolean EditTextStatus;
    //START declaring_auth
    private FirebaseAuth mAuth;
    // END declaring_auth

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        // Views
        emailField = v.findViewById(R.id.Email);
        passwordField = v.findViewById(R.id.createPassword);
        nameField = v.findViewById(R.id.fullName);
        progressBar = v.findViewById(R.id.progressBar);
        createaccnt = v.findViewById(R.id.createAccount);
        signin = v.findViewById(R.id.signIn);
        checkBox = v.findViewById(R.id.checkboxTerms);


        //START initialize auth
        mAuth = FirebaseAuth.getInstance();

        createaccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calling method to check EditText is empty or no status.
                CheckEditTextIsEmptyOrNot();
                checkBoxAgreed();

                // If EditText is true then this block with execute.
                if (EditTextStatus) {

                    // If EditText is not empty than UserRegistrationFunction method will call.

                    UserRegister();


                }
                // If EditText is false then this block with execute.
                else {

                    Toast.makeText(getContext(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.screen_area1, new LoginFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
        return v;
    }

    private void checkBoxAgreed() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createaccnt.setEnabled(true);
                } else {
                    createaccnt.setEnabled(false);
                    Toast.makeText(getContext(),"Please check the box to continue",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //STart check user
    @Override
    public void onStart() {
        super.onStart();
        //check if user is signed in (non null and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
//        new AppEULA(this).show();

    }



    public void UserRegister() {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailField.getText().toString(), passwordField.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            //Sign in success, update UI with the signed in users information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Account Created",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);


                           startActivity(new Intent(getActivity(),MainActivity.class));


                        } else {
                            //if Sign in fails, display a message to the user .
                            Log.w("UserWithEmail:Failure", task.getException());
                            Toast.makeText(getContext(), "Authentication Failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


    public void CheckEditTextIsEmptyOrNot() {

        // Getting name and email from EditText and save into string variables.
        String email = emailField.getText().toString().trim();
        String passwrd = passwordField.getText().toString().trim();
        String fllname = nameField.getText().toString().trim();


        if (TextUtils.isEmpty(email)) {
            emailField.setError("Please Enter A Valid Email");
        }

        if (TextUtils.isEmpty(passwrd) || passwrd.length() < 5) {
            passwordField.setError("Please Enter 5 or more characters");
        }
        if (TextUtils.isEmpty(fllname) || fllname.length() > 25) {
            nameField.setError("Empty or Name is too long");
        } else {

            EditTextStatus = true;
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

        }
    }

}

