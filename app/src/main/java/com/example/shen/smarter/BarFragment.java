package com.example.shen.smarter;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BarFragment extends Fragment {
    Spinner spinner;
    ProgressDialog p;
    View vBarFragment;
    String username;
    BarChart barChart;
    String resid;
    String defaultDate = "2018-03-14";
    String data,data2,data3;
    int date[] = {12,13,14};
    JSONArray jsonArray,jsonArray2,jsonArray3;
    List<Float> allData = new ArrayList<>();
    List<Integer> recordHour = new ArrayList<>();
    List<BarEntry> barEntryList = new ArrayList<>();
    public BarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vBarFragment = inflater.inflate(R.layout.fragment_bar, container, false);
        p = new ProgressDialog(getActivity());
        p.setCancelable(false);
        p.setMessage("Loading Data...");
        // Inflate the layout for this fragment
        spinner = vBarFragment.findViewById(R.id.usageSpinner);

        username = ((Home) getActivity()).getUser();
        barChart = vBarFragment.findViewById(R.id.barChart);
        resid = ((Home) getActivity()).getResid();
        setUsageSpinner();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        barEntryList = new ArrayList<>();
                        allData = new ArrayList<>();
                        recordHour = new ArrayList<>();
                        setUpHourlyBarChart();

                        Toast.makeText(getActivity(),"hourly",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        barEntryList = new ArrayList<>();
                        allData = new ArrayList<>();
                        setUpDailyBarChart();

                        Toast.makeText(getActivity(),"daily",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        return vBarFragment;

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
        adapter.add("hourly");
        adapter.add("daily");


        spinner.setAdapter(adapter);

    }

    private void setUpDailyBarChart(){


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                p.show();
            }
            @Override
            protected Void doInBackground(Void... voids) {

                data = RestClient.getAllHourlyUsage(resid,"2018-03-12","daily");
                data2 = RestClient.getAllHourlyUsage(resid,"2018-03-13","daily");
                data3 = RestClient.getAllHourlyUsage(resid,"2018-03-14","daily");
                return null;
            }



            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                try {
                    jsonArray = new JSONArray(data);
                    jsonArray2 = new JSONArray(data2);
                    jsonArray3 = new JSONArray(data3);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    try {
                        allData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("dailyUsage").toString()));
                        allData.add(Float.parseFloat(jsonArray2.getJSONObject(0).get("dailyUsage").toString()));
                        allData.add(Float.parseFloat(jsonArray3.getJSONObject(0).get("dailyUsage").toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                for (int i = 0; i < allData.size(); i ++){
                    barEntryList.add(new BarEntry(date[i],allData.get(i)));
                }
                BarDataSet dataSet = new BarDataSet(barEntryList,"Daily Usage");
                dataSet.setColors(ColorTemplate.rgb("#FF0097E6"));
                dataSet.setValueTextSize(10f);
                BarData data2 = new BarData(dataSet);

                barChart.setData(data2);

                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getDescription().setEnabled(false);
                barChart.getXAxis().setLabelCount(allData.size(),true);
                barChart.getXAxis().setDrawGridLines(false);
                barChart.getAxisLeft().setDrawGridLines(false);
                barChart.getAxisRight().setEnabled(false);
                barChart.getAxisRight().setDrawGridLines(false);
                barChart.setFitBars(true);

                barChart.animateY(1000);

                barChart.invalidate();



                p.dismiss();
            }
        }.execute();
    }




    private void setUpHourlyBarChart() {




        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                p.show();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                data = RestClient.getAllHourlyUsage(resid,defaultDate,"hourly");
                return null;
            }



            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                p.dismiss();
                try {
                    jsonArray = new JSONArray(data);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i <jsonArray.length(); i++) {
                    try {
                        allData.add(Float.parseFloat(jsonArray.getJSONObject(i).get("hourlyUsage").toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        recordHour.add((Integer.parseInt(jsonArray.getJSONObject(i).get("recordhour").toString()))*10/10);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                    for (int i = 0; i < recordHour.size(); i ++){
                    barEntryList.add(new BarEntry(recordHour.get(i),allData.get(i)));
                }
                BarDataSet dataSet = new BarDataSet(barEntryList,"Hourly Usage");
                dataSet.setColors(ColorTemplate.rgb("#FF0097E6"));
                dataSet.setValueTextSize(10f);
                BarData data2 = new BarData(dataSet);

                barChart.setData(data2);

                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getDescription().setEnabled(false);
                barChart.getXAxis().setLabelCount(recordHour.size(),true);
                barChart.getXAxis().setDrawGridLines(false);
                barChart.getAxisRight().setDrawGridLines(false);
                barChart.getAxisLeft().setDrawGridLines(false);
                barChart.getAxisRight().setEnabled(false);
                barChart.setFitBars(true);


                barChart.animateY(1000);

                barChart.invalidate();
                p.dismiss();


            }
        }.execute();
    }
}
