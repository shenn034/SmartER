package com.example.shen.smarter;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.app.WallpaperColors;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shen.smarter.models.Credentials;
import com.example.shen.smarter.models.DBStructure;
import com.example.shen.smarter.models.Eusage;
import com.example.shen.smarter.models.Resident;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import me.drakeet.materialdialog.MaterialDialog;


public class Fragment_home extends Fragment {
    View vHome;
    TextView username, cityname, datefield, weatherfield, imagefield, desfield, testfield;
    Typeface weatherFont;
    String userNameFrom, userId;
    String fName;
    String address, postCode;
    String lng, lat;
    Button cheatButton;
    protected DBManager dbManager;
    boolean hasWorked;
    int workTime;
    int countWashing, countAir;
    Eusage eusage;
    String a ;
    JSONArray array;
    Resident resident  = new Resident();
    String fridgeUsage,airconUsage,washingUsage,temperature;
    TextView peakHour;
    ImageView picture;
    int records;
    ArrayList<DBStructure> recordsList;
    private DBManager mDBManager;
    DBStructure dbStructure;

    //Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            if (records != 24) {
//                generateDataByButton();
//                Toast.makeText(getActivity(), "add automatically", Toast.LENGTH_SHORT).show();
//                handler.postDelayed(this, 1000);
//
//            }
//            else {
//                handler.removeCallbacks(runnable);
//                Toast.makeText(getActivity(), "24 records are posted!", Toast.LENGTH_LONG).show();
//            }
//        }
//    };





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        userNameFrom = ((Home) getActivity()).getUser();
        userId = "201801";
        Calendar rightNow = Calendar.getInstance();
        vHome = inflater.inflate(R.layout.fragment_home, container, false);
        ((Home) getActivity()).getSupportActionBar().setTitle("SmartER");
        fetchFname();
        fetchAddress();
        fetchPostCode();
        records = 0;
        fridgeUsage = "0.0";
        washingUsage = "0.0";
        airconUsage = "0.0";
        double fridge = Double.parseDouble(fridgeUsage);
        double wash = Double.parseDouble(washingUsage);
        double air = Double.parseDouble(airconUsage);
        mDBManager = new DBManager(getContext());


        workTime =(int)Math.round((int)((Math.random()*3)*10)/10);
        countAir = 0;
        countWashing = 0;
        peakHour = (TextView)vHome.findViewById(R.id.peakHour);
        picture = (ImageView) vHome.findViewById(R.id.picture);

        final int recordhour = rightNow.get(Calendar.HOUR_OF_DAY);
        hasWorked = false;
        weatherFont = Typeface.createFromAsset(getContext().getAssets(), "weathericons-regular-webfont.ttf");
        username = (TextView) vHome.findViewById(R.id.username);
        cityname = (TextView) vHome.findViewById(R.id.cityname);
        datefield = (TextView) vHome.findViewById(R.id.datefield);
        weatherfield = (TextView) vHome.findViewById(R.id.weatherfield);
        imagefield = (TextView) vHome.findViewById(R.id.weatherIcon);
        desfield = (TextView) vHome.findViewById(R.id.desfield);
        cheatButton = (Button) vHome.findViewById(R.id.add);
        testfield = (TextView) vHome.findViewById(R.id.testfield2);
        if (recordhour >= 0 && recordhour <= 22) {
            peakHour.setText("Your current record hour is: " + recordhour + "\n" + "You are in the peak hour!" + "\n" +"Your wasing machine work time is "+ workTime + "\n"+ "Your current usage is: " + (air+wash+fridge));
            if ((air+fridge+wash) > 1.5){
                picture.setImageResource(R.drawable.not_well);
            }
            else {
                picture.setImageResource(R.drawable.do_well);
            }

        }
        else {
            peakHour.setText("Your current record hour is: " + recordhour + "\n" + "You are not in the peak hour!" +"Your wasing machine work time is "+ workTime + "\n"+ "\n" + "Your current usage is: " + (air+wash+fridge));
            picture.setImageResource(R.drawable.not_in_peak);
        }

        // to start the handler
        //handler.postDelayed(runnable,1000);


        cheatButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (recordhour == 0) {
                    workTime = (int) (Math.random() * 3);
                    countAir = 0;
                    countWashing = 0;
                }
                if (records != 24) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();

                        }

                        @Override
                        protected Void doInBackground(Void... voids) {
                            generateDataByButton();

                            return null;
                        }


                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            final MaterialDialog materialdialognow = new MaterialDialog(getActivity());
                            materialdialognow.setTitle("Done")
                                    .setMessage("Data created!")
                                    .setPositiveButton("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (materialdialognow != null)
                                                materialdialognow.dismiss();
                                        }
                                    });
                            materialdialognow.show();

                        }
                    }.execute();
                }
                else {
                    final MaterialDialog materialdialognow = new MaterialDialog(getActivity());
                    materialdialognow.setTitle("Error")
                            .setMessage("24 records maximum per day!")
                            .setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (materialdialognow != null)
                                        materialdialognow.dismiss();
                                }
                            });
                    materialdialognow.show();
                }


            }
        });

        new GetCoordinates().execute();


        return vHome;
    }

    public void fetchFname() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                fName = RestClient.getFname(userNameFrom);
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                username.setText(fName);
            }


        }.execute();

    }

    public void fetchAddress() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                address = RestClient.getAddress(userNameFrom);
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                return;
            }


        }.execute();

    }

    public void fetchPostCode() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                postCode = RestClient.getPostCode(userNameFrom);
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                return;
            }


        }.execute();

    }

    public class GetCoordinates extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog((Home) getActivity());


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading Data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try {

                HttpDataHandler http = new HttpDataHandler();
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "," + postCode + "&key=AIzaSyCGcKOYWRzxsQCMM2VhSaCSBeUwjUId9-A";
                response = http.getHTTPData(url);
                return response;
            } catch (Exception ex) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                WeatherApi.placeIdTask asyncTask = new WeatherApi.placeIdTask(new WeatherApi.AsyncResponse() {
                    public void processFinish(String weather_city, String weather_temperature, String weather_updatedOn, String weather_des, String weather_iconText, String sun_rise) {

                        cityname.setText(weather_city);
                        datefield.setText(weather_updatedOn);
                        weatherfield.setText(weather_temperature);
                        desfield.setText(weather_des);
                        imagefield.setText(Html.fromHtml(weather_iconText));
                        imagefield.setTypeface(weatherFont);

                    }
                });
                asyncTask.execute(lat, lng); //  asyncTask.execute("Latitude", "Longitude")
                progressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAddress() {
        return address;
    }

    public String getPostCode() {
        return postCode;
    }





    public void generateDataByButton() {
        Calendar rightNow = Calendar.getInstance();
        String usageid;
        final String resid;
        final String date;

        records++;



        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        String dateFormat = format.format(Calendar.getInstance().getTime());
        final int recordhour = rightNow.get(Calendar.HOUR_OF_DAY);
        date = dateFormat + "+10:00";

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... voids) {
                a = RestClient.findByUserId(userId);
                try {
                    array = new JSONArray(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    resident = new Resident(Integer.parseInt(userId), array.getJSONObject(0).get("fname").toString(), array.getJSONObject(0).get("lname").toString(),
                            array.getJSONObject(0).get("dob").toString(), array.getJSONObject(0).get("address").toString(), Integer.parseInt(array.getJSONObject(0).get("postcode").toString()),
                            array.getJSONObject(0).get("email").toString(), array.getJSONObject(0).get("mobile").toString(), Integer.parseInt(array.getJSONObject(0).get("numberofres").toString()),
                            array.getJSONObject(0).get("provider").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                temperature = weatherfield.getText().toString() + ".0";
                double d = (double)(Math.random()*2 +3);


                fridgeUsage = String.valueOf((double)Math.round((double)Math.round(d*10)/10)*10/100);

                // function to generate washing machine usages
                if (recordhour >= 6 && (recordhour + workTime) <= 21) {
                    if (countWashing == workTime) {
                        washingUsage = "0.0";
                    } else {
                        if (!hasWorked) {
                            int i = (int)(Math.random()*10);
                            if (i >= 5) {
                                washingUsage = "0.0";
                            } else {

                                washingUsage = String.valueOf((double)Math.round((double)Math.round((double)((Math.random() + 1) * 3)*10)/10)*10/100);
                                countWashing++;
                                hasWorked = true;
                            }
                        } else {
                            washingUsage = String.valueOf((double)Math.round((double)Math.round((double)((Math.random() + 1) * 3)*10)/10)*10/100);
                            countWashing++;
                        }
                    }

                } else {
                    washingUsage = "0.0";
                }


                //function to generate airCon usage
                if (recordhour > 6 && recordhour < 23) {
                    if (Integer.parseInt(weatherfield.getText().toString()) > 20) {
                        if (countAir != 10) {
                            int i = (int) (Math.random()*10);
                            {
                                if (i >= 7) {
                                    airconUsage = "0.0";
                                } else {
                                    airconUsage = String.valueOf(((double)Math.round((double)((Math.random() + 1))*10)/10));
                                    countAir++;
                                }
                            }
                        }

                    } else {
                        airconUsage = "0.0";
                    }

                } else {
                    airconUsage = "0.0";
                }

                // set Eusage

                if (records ==24){
                    recordsList = new ArrayList<>(mDBManager.getAllReocords().values());
                    for (DBStructure dbStructure: recordsList){
                        eusage = new Eusage();
                        eusage.setUsageId(dbStructure.getUsageId());
                        eusage.setAirconUsage(dbStructure.getAirconUsage());
                        eusage.setDate(dbStructure.getDate());
                        eusage.setFridgeUsage(dbStructure.getFridgeUsage());
                        eusage.setRecordHour(dbStructure.getRecordHour());
                        eusage.setWashingUsage(dbStructure.getWashingUsage());
                        eusage.setTemperature(dbStructure.getTemperature());
                        eusage.setResid(resident);
                        RestClient.createEusage(eusage);
                    }

                    mDBManager.clearAll();



                }
                else
                {
                    dbStructure = new DBStructure();
                    dbStructure.setUsageId(Integer.parseInt(userId + recordhour + records));
                    dbStructure.setDate(date);
                    dbStructure.setFridgeUsage(Double.parseDouble(fridgeUsage));
                    dbStructure.setRecordHour(recordhour);
                    dbStructure.setWashingUsage(Double.parseDouble(washingUsage));
                    dbStructure.setTemperature(Double.parseDouble(temperature));
                    mDBManager.addUsage(dbStructure);
                 //save records to database
                }




                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

//                testfield.setText(eusage.getResid().getResid() +"/"+ eusage.getUsageId() +"/"+ eusage.getDate() +"/"+ eusage.getAirconUsage() + "/" +
//                        eusage.getFridgeUsage() +"/"+ eusage.getWashingUsage() +"/" + eusage.getTemperature() +"/"+ workTime);
                if (recordhour >= 9 && recordhour <= 22) {
                    peakHour.setText("Your current record hour is: " + recordhour + "\n" + "You are in the peak hour!" +"\n" + "Your wasing machine work time is "+ workTime + "\n" + "Your current usage is: " + (Double.parseDouble(fridgeUsage) + Double.parseDouble(washingUsage) + Double.parseDouble(airconUsage)));
                    if (( Double.parseDouble(fridgeUsage) + Double.parseDouble(washingUsage) + Double.parseDouble(airconUsage)) > 1.5){
                        picture.setImageResource(R.drawable.not_well);
                    }
                    else {
                        picture.setImageResource(R.drawable.do_well);
                    }
                }
                else {
                    peakHour.setText("Your current record hour is: " + recordhour + "\n" + "You are not in the peak hour!" + "Your wasing machine work time is "+ workTime + "\n" + "\n" + "Your current usage is: " + (Double.parseDouble(fridgeUsage) + Double.parseDouble(washingUsage) + Double.parseDouble(airconUsage)));
                    picture.setImageResource(R.drawable.not_in_peak);
                }




            }
        }.execute();

    }



}






