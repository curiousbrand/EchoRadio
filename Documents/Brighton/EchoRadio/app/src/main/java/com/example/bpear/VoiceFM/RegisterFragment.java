

package com.example.bpear.VoiceFM;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private CoordinatorLayout regisCoordi;
    private ProgressBar progressBar;
private TextView termsofser;
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
        termsofser = v.findViewById(R.id.text_term);
        termsofser.setText(Html.fromHtml(
                "<a href=''> Terms of Service</a>"));
        termsofser.setClickable(true);
        termsofser.setMovementMethod(LinkMovementMethod.getInstance());
        termsofser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Terms of Service ";

                // EULA text
                String text = getString(R.string.eula_string);
                CharSequence styledText = Html.fromHtml(text);


                // Disable orientation changes, to prevent parent activity
                // reinitialization


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle(title)
                        .setMessage(styledText)
                        .setCancelable(false)
                        .setNegativeButton("Close",
                                new Dialog.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // Close the activity as they have declined
                                        // the EULA

                                    }

                                });
                builder.create().show();
            }
            });

        regisCoordi = (CoordinatorLayout) v.findViewById(R.id
                .reg_coor);
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

                   // Toast.makeText(getContext(), "Please fill all form fields.", Toast.LENGTH_LONG).show();
                    Snackbar snackbar4 = Snackbar.make(regisCoordi,"Please fill all fields", Snackbar.LENGTH_LONG);
                    View sb2View = snackbar4.getView();
                    TextView textView2 = sb2View.findViewById(android.support.design.R.id.snackbar_text);
                    textView2.setTextColor(Color.RED);
                    snackbar4.show();
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.screen_area, new LoginFragment())
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
                   // Toast.makeText(getContext(),"Please check the box to continue",Toast.LENGTH_LONG).show();
                    Snackbar snackbar5 = Snackbar.make(regisCoordi,"Checkbox to continue.", Snackbar.LENGTH_LONG);
                    View sb2View = snackbar5.getView();
                    TextView textView2 = sb2View.findViewById(android.support.design.R.id.snackbar_text);
                    textView2.setTextColor(Color.RED);
                    snackbar5.show();
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
        new AppEULA(getActivity()).show();

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
                            Toast.makeText(getContext(), "Account Created", Toast.LENGTH_SHORT).show();
                            updateUI(user);


                           startActivity(new Intent(getActivity(),MainActivity.class));


                        } else {
                            //if Sign in fails, display a message to the user .
                            Log.w("UserWithEmail:Failure", task.getException());
                           // Toast.makeText(getContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show();
                            Snackbar snackbar3 = Snackbar.make(regisCoordi,"Authentication Failed.", Snackbar.LENGTH_LONG);
                            View sb2View = snackbar3.getView();
                            TextView textView2 = sb2View.findViewById(android.support.design.R.id.snackbar_text);
                            textView2.setTextColor(Color.RED);
                            snackbar3.show();
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

