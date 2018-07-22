package com.example.shen.smarter;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shen.smarter.models.Credentials;
import com.example.shen.smarter.models.Resident;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    EditText fName, dob,username, password, passwordConfirm, lname, address, postcode, mobile, email;
    Spinner NORspinner, providerSpinner;
    String NOR;
    String provider;
    int resid;
    boolean isUsername,isPassword, isPasswordConfirm, isLname, isFname, isAddress, isPostcode, isMobile, isEmail,isSpinner;
    Calendar calendar;
    int mYear, mMonth, mDay;
    Button registerButton;
    Credentials credentials;
    Resident resident;
    String regisDate;
    String backendUsername;
    boolean isUserExsist;
    DatePicker mDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fName = (EditText)findViewById(R.id.editFnameText);
        dob = (EditText)findViewById(R.id.editDOBText);
        username = (EditText)findViewById(R.id.editUsernameText);
        password = (EditText)findViewById(R.id.editPasswordText);
        passwordConfirm = (EditText)findViewById(R.id.editPasswordConfirmText);
        lname = (EditText)findViewById(R.id.editLnameText);
        address = (EditText)findViewById(R.id.editAddressText);
        postcode = (EditText)findViewById(R.id.editPostcodeText);
        mobile = (EditText)findViewById(R.id.editMobileText);
        email = (EditText)findViewById(R.id.editEmailText);
        NORspinner = (Spinner)findViewById(R.id.NORspinner);
        registerButton = (Button)findViewById(R.id.registerButton);
        providerSpinner = (Spinner)findViewById(R.id.providerSpinner);
        setNORSpinner();
        setProviderSpinner();
        NORspinner.setOnItemSelectedListener(this);
        providerSpinner.setOnItemSelectedListener(this);
        calendar = Calendar.getInstance();
        mDatePicker = new DatePicker(this);
        mDatePicker.setMaxDate(System.currentTimeMillis());
        setDobText();
        registerButton.setOnClickListener(this);
        resident = new Resident();
        credentials = new Credentials();
        validateInputs();
        isUsername = false;
        isPasswordConfirm = false;
        isPassword = false;
        isLname = false;
        isFname = false;
        isAddress = false;
        isPostcode = false;
        isMobile = false;
        isEmail = false;
        isSpinner = false;
        isUserExsist = false;
        registerButton.setAlpha(.4f);
        registerButton.setClickable(false);
        backendUsername = "";

    }

    //function to set spinner of number of residents
    public void setNORSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("1");
        adapter.add("2");
        adapter.add("3");
        adapter.add("4");
        adapter.add("5");
        adapter.add("Number Of Residents");

        NORspinner.setAdapter(adapter);
        NORspinner.setSelection(adapter.getCount()); //display hint
    }


    //function to set spinner of providers
    public void setProviderSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("AGL");
        adapter.add("Origin Energy");
        adapter.add("Select an Energy Provider");

        providerSpinner.setAdapter(adapter);
        providerSpinner.setSelection(adapter.getCount()); //display hint
    }



    //set the datepicker
    public void setDobText(){
        dob.setInputType(InputType.TYPE_NULL);
        dob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(Register.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker v, int year,
                                                      int month, int day) {
                                    // TODO Auto-generated method stub
                                    mYear = year;
                                    mMonth = month;
                                    mDay = day;
                                    dob.setText(new StringBuilder()
                                            .append(mYear)
                                            .append("-")
                                            .append((mMonth + 1) < 10 ? "0"
                                                    + (mMonth + 1) : (mMonth + 1))
                                            .append("-")
                                            .append((mDay < 10) ? "0" + mDay : mDay));
                                }
                            }, calendar.get(Calendar.YEAR), calendar
                            .get(Calendar.MONTH), calendar
                            .get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }

    public void validateInputs(){

            fName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (fName.getText().toString().trim().length() == 0) {
                        fName.setError("Cannot be blank");
                        isFname = false;
                    }
                    else {isFname = true;}
                    if (isUsername && isPasswordConfirm && isLname && isFname  && isAddress && isPostcode && isMobile && isEmail ){
                        registerButton.setAlpha(1f);
                        registerButton.setClickable(true);
                    }
                    else {
                        registerButton.setAlpha(.4f);
                        registerButton.setClickable(false);
                    }
                }
            });

            username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (username.getText().toString().trim().length() == 0) {
                        username.setError("Cannot be blank");
                        isUsername = false;

                    }
                    else {
                        isUsername = true;
                    }
                    if (isUsername && isPasswordConfirm && isLname && isFname  && isAddress && isPostcode && isMobile && isEmail ){
                        registerButton.setAlpha(1f);
                        registerButton.setClickable(true);
                    }
                    else {
                        registerButton.setAlpha(.4f);
                        registerButton.setClickable(false);
                    }
                }
            });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (password.getText().toString().trim().length() <= 5) {
                    password.setError("At least 6 chars required!");
                    isPassword = false;

                }
                else {
                    isPassword = true;
                }
                if (isUsername && isPasswordConfirm && isLname && isFname  && isAddress && isPostcode && isMobile && isEmail ){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }
            }
        });

        passwordConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (passwordConfirm.getText().toString().trim().length() == 0) {
                    passwordConfirm.setError("Cannot be blank");
                    isPasswordConfirm = false;

                }
                else {
                    isPasswordConfirm = true;
                }
                if (!passwordConfirm.getText().toString().equals(password.getText().toString()))
                {
                    passwordConfirm.setError("Passwords are different!");
                    isPasswordConfirm =false;
                }
                else {
                    isPasswordConfirm = true;
                }
                if (isUsername && isPasswordConfirm && isLname && isFname && isAddress && isPostcode && isMobile && isEmail){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }
            }
        });

        lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (lname.getText().toString().trim().length() == 0) {
                    lname.setError("Cannot be blank");
                    isLname = false;

                }
                else {
                    isLname = true;
                }
                if (isUsername && isPasswordConfirm && isLname && isFname  && isAddress && isPostcode && isMobile && isEmail){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }
            }
        });

        mobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (mobile.getText().toString().trim().length() != 11) {
                    mobile.setError("11 numbers required!");
                    isMobile = false;

                }
                else {
                    isMobile = true;
                }
                if (isUsername && isPasswordConfirm && isLname && isFname  && isAddress && isPostcode && isMobile && isEmail){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }
            }
        });

        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (address.getText().toString().trim().length() == 0) {
                    address.setError("Cannot be blank");
                    isAddress= false;

                }
                else {
                    isAddress = true;
                }
                if (isUsername && isPasswordConfirm && isLname && isFname  && isAddress && isPostcode && isMobile && isEmail){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (email.getText().toString().trim().length() == 0) {
                    email.setError("Cannot be blank");
                    isEmail = false;

                }
                else {
                    Pattern pattern = Pattern.compile("^.+@.+\\..+$");
                    Matcher matcher = pattern.matcher(email.getText().toString());
                    if (matcher.matches()){isEmail = true;}
                    else{
                        email.setError("Invalid email address");
                        isEmail = false;}
                }
                if (isUsername && isPasswordConfirm && isLname && isFname  && isAddress && isPostcode && isMobile && isEmail ){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }

            }
        });

        postcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                if (postcode.getText().toString().trim().length() != 4) {
                    postcode.setError("4 numbers required!");
                    isPostcode = false;

                }
                else {
                    isPostcode = true;
                }
                if (isUsername && isPasswordConfirm && isLname && isFname && isAddress && isPostcode && isMobile && isEmail ){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }
                if (NOR.length() < 3 && provider.length() < 16){
                    registerButton.setAlpha(1f);
                    registerButton.setClickable(true);
                }
                else {
                    registerButton.setAlpha(.4f);
                    registerButton.setClickable(false);
                }
            }
        });



    }

    public void validateUser() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return RestClient.findByUsername("a");
            }
        };
    }

    //function to return the values selected by the spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          NOR = NORspinner.getSelectedItem().toString();
          provider = providerSpinner.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        regisDate = formatDate(Calendar.getInstance().getTime());
        createResident();
        credentials.setDate(regisDate);
        credentials.setPassword(md5(password.getText().toString().trim()));
        credentials.setUsername(username.getText().toString().trim());
        final ProgressDialog p;
        p = new ProgressDialog(this);
        p.setCancelable(false);
        p.setMessage("Registering...");

        new AsyncTask<Void, Void, String>() {
            String a;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                p.show();
            }

            @Override
            protected String doInBackground(Void... params) {


                String getid = RestClient.getResid();
                a = RestClient.findByUsername(username.getText().toString());
                resid = Integer.parseInt(getid) + 1;

                if (a.equals("[]")){
                    resident.setResid(resid);
                    RestClient.createResidents(resident);
                    credentials.setResid(resident);
                    RestClient.createCredentials(credentials);
                }
                else {
                    return "done";
                }

                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                if (a.equals("[]")){
                    isUsername = true;
                    Toast.makeText(getApplicationContext(),"Registration succeed!",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(getApplicationContext(),"Username Exsists!",Toast.LENGTH_LONG).show();
                    isUserExsist = true;
                    isUsername =false;
                }
                p.dismiss();

            }
        }.execute();
        if ( isUserExsist){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }



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

    //function to format the  date
    public String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateFormat = format.format(date);
        String dateAfterFormate = dateFormat + "+10:00";
        return dateAfterFormate;

    }

    public void createResident(){
        String formateDate = dob.getText().toString().trim() + "T" + "00:00:00" + "+10:00";
        resident.setAddress(address.getText().toString().trim());
        resident.setDob(formateDate);
        resident.setEmail(email.getText().toString().trim());
        resident.setFname(fName.getText().toString().trim());
        resident.setLname(lname.getText().toString().trim());
        resident.setNor(Integer.parseInt(NOR));
        resident.setMobile(mobile.getText().toString().trim());
        resident.setPostcode(Integer.parseInt(postcode.getText().toString()));
        resident.setProvider(provider);
    }

}


