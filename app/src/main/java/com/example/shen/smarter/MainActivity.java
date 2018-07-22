package com.example.shen.smarter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import me.drakeet.materialdialog.MaterialDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView registerLink;
    private Button loginButton;
    private EditText userNameText, passwordText;
    private boolean isUsernameExsist = false;
    private boolean isPasswordCorrect = true;
    private JsonArray credential = new JsonArray();
    private String validationResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerLink = (TextView) findViewById(R.id.registerTextView);
        loginButton = (Button) findViewById(R.id.loginButton);
        userNameText = (EditText) findViewById(R.id.editUserNameText);
        passwordText = (EditText) findViewById(R.id.editPasswordText);
        loginButton.setOnClickListener(this);
        getSupportActionBar().hide();
        checkInputs();
        textOnTouch();

    }


    public void registerUser() {
        Intent registerPage = new Intent(this, Register.class);
        startActivity(registerPage);
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton) {
            validationResult = "";
            final ProgressDialog p;
            p = new ProgressDialog(this);
            p.setCancelable(false);
            p.setMessage("Please wait...");
            new AsyncTask<Void, Void, String>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    p.show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    validationResult = RestClient.validateUser(userNameText.getText().toString().trim(),md5(passwordText.getText().toString().trim()));
                    return validationResult;
                }

                @Override
                protected void onPostExecute(String s) {
                    switch (validationResult){
                        case ("noUser") : notPassValidationUsername();
                                          break;
                        case ("true") : passValidation();
                                        break;
                        case ("false") : notPassValidationPassword();
                            break;

                    }
                    p.dismiss();

                }
            }.execute();
        }

    }

    public void passValidation() {
        String user = userNameText.getText().toString();
        Intent mIntent = new Intent(this, Home.class);
        mIntent.putExtra("user",user);
        startActivity(mIntent);
    }

    public void notPassValidationUsername() {

        final MaterialDialog materialdialognow = new MaterialDialog(this);
        materialdialognow.setTitle("Login Failed")
                .setMessage("Username has not been registered!")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (materialdialognow != null)
                            materialdialognow.dismiss();
                    }
                });
        materialdialognow.show();
    }



    public void notPassValidationPassword() {
        final MaterialDialog materialdialognow = new MaterialDialog(this);
        materialdialognow.setTitle("Login Failed")
                .setMessage("Password is wrong!")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (materialdialognow != null)
                            materialdialognow.dismiss();
                    }
                });
        materialdialognow.show();
    }

    //function to check the user inputs, if the inputs are null, then disable the login button
    public void checkInputs() {
        if (userNameText.getText().toString().trim().length() == 0 ||
                passwordText.getText().toString().trim().length() == 0) {
            loginButton.setAlpha(.4f);
            loginButton.setClickable(false);
        }
        userNameText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (userNameText.getText().toString().trim().length() == 0 ||
                        passwordText.getText().toString().trim().length() == 0) {
                    loginButton.setAlpha(.4f);
                    loginButton.setClickable(false);
                } else {
                    loginButton.setAlpha(1f);
                    loginButton.setClickable(true);
                }
            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (passwordText.getText().toString().trim().length() == 0 ||
                        userNameText.getText().toString().trim().length() == 0) {
                    loginButton.setAlpha(.4f);
                    loginButton.setClickable(false);
                } else {
                    loginButton.setAlpha(1f);
                    loginButton.setClickable(true);
                }
            }
        });

    }


    //function to change the link color when pressed or released
    public void textOnTouch() {
        registerLink.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    registerLink.setTextColor(Color.parseColor("#FF0073AF"));
                    registerUser();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    registerLink.setTextColor(Color.parseColor("#FF0097E6"));
                }
                return true;
            }
        });
    }

    //hash password
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
