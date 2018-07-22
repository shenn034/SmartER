package com.example.shen.smarter;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map extends Fragment implements OnMapReadyCallback {
    View vMapUnit;
    GoogleMap map;
    String mAddress, mPostcode;
    String user;
    String lat, lng;
    String allAddress;
    List<String> addressList = new ArrayList<String>();
    List<String> geoList = new ArrayList<String>();
    List<Double> lngList = new ArrayList<Double>();
    List<Double> latList = new ArrayList<Double>();
    List<String> hourlyList = new ArrayList<String>();
    List<String> dailyList = new ArrayList<String>();
    JSONArray array;
    String type;
    ProgressDialog progressDialog;
    Spinner spinner;
    String hourlyData;
    String dailyData;
    JSONArray hourlyArray;
    JSONArray dailyArray;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMapUnit = inflater.inflate(R.layout.fragment_map, container, false);
        spinner = vMapUnit.findViewById(R.id.usageSpinner);
        setUsageSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        map.clear();
                        for (int i = 0; i < geoList.size(); i++) {
                            try {

                                JSONObject myObject = new JSONObject(geoList.get(i));
                                JSONArray jsonArray = myObject.getJSONArray("results");
                                lat = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                                lng = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
                                double doubleLat = Double.parseDouble(lat);
                                double doubleLng = Double.parseDouble(lng);
                                latList.add(doubleLat);
                                lngList.add(doubleLng);
                                LatLng latLng = new LatLng(doubleLat, doubleLng);
                                Double daily = Double.parseDouble(dailyList.get(i));
                                if (daily > 21) {
                                    map.addMarker(new MarkerOptions().position(latLng).title("Usage").snippet("Daily: " + dailyList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                }
                                else {
                                    map.addMarker(new MarkerOptions().position(latLng).title("Usage").snippet("Daily: " + dailyList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Toast.makeText(getActivity(),"daily",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        map.clear();
                        for (int i = 0; i < geoList.size(); i++) {
                            try {

                                JSONObject myObject = new JSONObject(geoList.get(i));
                                JSONArray jsonArray = myObject.getJSONArray("results");
                                lat = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                                lng = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
                                double doubleLat = Double.parseDouble(lat);
                                double doubleLng = Double.parseDouble(lng);
                                latList.add(doubleLat);
                                lngList.add(doubleLng);
                                LatLng latLng = new LatLng(doubleLat, doubleLng);
                                Double hourly = Double.parseDouble(hourlyList.get(i));
                                if (hourly >= 1.5) {
                                    map.addMarker(new MarkerOptions().position(latLng).title("Usage").snippet("Hourly: " + hourlyList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                                }
                                else {
                                    map.addMarker(new MarkerOptions().position(latLng).title("Usage").snippet("Hourly: " + hourlyList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Toast.makeText(getActivity(),"hourly",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Map");
        progressDialog = new ProgressDialog(getActivity());
        user = ((Home) getActivity()).getUser();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Loading allAddress..");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                allAddress = RestClient.getAllAddress();
                hourlyData = RestClient.getHourlyUsage();
                dailyData = RestClient.getDailyUsage();
                try {
                    array = new JSONArray(allAddress);
                    hourlyArray = new JSONArray(hourlyData);
                    dailyArray = new JSONArray(dailyData);
                    for (int i = 0; i < array.length(); i++) {
                        String a = array.getJSONObject(i).get("address").toString() + "," + array.getJSONObject(i).get("postcode").toString();
                        String b = hourlyArray.getJSONObject(i).get("hourly").toString();
                        String c = dailyArray.getJSONObject(i).get("daily").toString();
                        addressList.add(a);
                        hourlyList.add(b);
                        dailyList.add(c);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                new GetCoordinates().execute();


            }


        }.execute();



        return vMapUnit;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setZoomGesturesEnabled(true);
        map.getUiSettings().setScrollGesturesEnabled(true);
        LatLng MELBOURNE = new LatLng(-37.877379, 145.045024);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(MELBOURNE, 15.0f));
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter((Home) getActivity()));
    }


    public void setUsageSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);


                return v;
            }



        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("daily");
        adapter.add("hourly");


        spinner.setAdapter(adapter);

    }

    public void fetchAddress() {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                mAddress = RestClient.getAddress(user);
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
                mPostcode = RestClient.getPostCode(user);
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                return;
            }


        }.execute();

    }

    public void fetchAllAddress() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                allAddress = RestClient.getAllAddress();
                try {
                    array = new JSONArray(allAddress);
                    for (int i = 0; i < array.length(); i++) {
                        String a = array.getJSONObject(i).get("address").toString() + "," + array.getJSONObject(i).get("postcode").toString();
                        addressList.add(a);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {


            }


        }.execute();
    }

    public class GetCoordinates extends AsyncTask<Void, Void, Void> {
        String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading geoAddress..");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < addressList.size(); i ++) {
                try {

                    HttpDataHandler http = new HttpDataHandler();
                    String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + addressList.get(i) + "&key=AIzaSyCGcKOYWRzxsQCMM2VhSaCSBeUwjUId9-A";
                    response = http.getHTTPData(url);
                    geoList.add(response);

                } catch (Exception ex) {

                }

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            for (int i = 0; i < geoList.size(); i++) {
                try {

                    JSONObject myObject = new JSONObject(geoList.get(i));
                    JSONArray jsonArray = myObject.getJSONArray("results");
                    lat = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lat").toString();
                    lng = jsonArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").get("lng").toString();
                    double doubleLat = Double.parseDouble(lat);
                    double doubleLng = Double.parseDouble(lng);
                    latList.add(doubleLat);
                    lngList.add(doubleLng);
                    LatLng latLng = new LatLng(doubleLat, doubleLng);
                    Double daily = Double.parseDouble(dailyList.get(i));
                    if (daily > 21) {
                        map.addMarker(new MarkerOptions().position(latLng).title("Usage").snippet("Daily: " + dailyList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    }
                    else {
                        map.addMarker(new MarkerOptions().position(latLng).title("Usage").snippet("Daily: " + dailyList.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }


    }








}
