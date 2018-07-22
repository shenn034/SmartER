package com.example.shen.smarter;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LineFragment extends Fragment {
    View vLineFragment;
    Spinner spinner;
    String resid;
    CombinedChart combinedChart;
    String defaultDate = "2018-03-14";
    String data,data2,data3;
    int date[] = {12,13,14};
    JSONArray jsonArray,jsonArray2,jsonArray3;
    List<Float> allData = new ArrayList<>();
    List<Float> allTemData = new ArrayList<>();
    List<Integer> recordHour = new ArrayList<>();
    List<Entity> lineEntryList = new ArrayList<>();
    CombinedData combinedData;
    LineData lineData;
    BarData barData;


    public LineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vLineFragment = inflater.inflate(R.layout.fragment_line, container, false);
        spinner = vLineFragment.findViewById(R.id.usageSpinner);
        combinedChart = vLineFragment.findViewById(R.id.lineChart);
        resid = ((Home) getActivity()).getResid();
        setUsageSpinner();
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        combinedChart.getAxisLeft().setDrawGridLines(false);
        combinedChart.getAxisRight().setDrawGridLines(false);
        combinedChart.getXAxis().setDrawGridLines(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        lineEntryList = new ArrayList<>();
                        allData = new ArrayList<>();
                        recordHour = new ArrayList<>();
                        allTemData = new ArrayList<>();
                        combinedData = new CombinedData();
                        lineData = new LineData();
                        barData = new BarData();

                        setUpHourlyLineChart();

                        Toast.makeText(getActivity(),"hourly",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        lineEntryList = new ArrayList<>();
                        allData = new ArrayList<>();
                        allTemData = new ArrayList<>();
                        combinedData = new CombinedData();
                        lineData = new LineData();
                        barData = new BarData();
                        setUpDailyLineChart();

                        Toast.makeText(getActivity(),"daily",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


        return vLineFragment;
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

    private void setUpDailyLineChart(){

        final ArrayList<BarEntry> entities = new ArrayList<>();
        final ArrayList<Entry> entities2 = new ArrayList<>();


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

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
                    allTemData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("averageTemperature").toString()));
                    allTemData.add(Float.parseFloat(jsonArray2.getJSONObject(0).get("averageTemperature").toString()));
                    allTemData.add(Float.parseFloat(jsonArray3.getJSONObject(0).get("averageTemperature").toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                for (int i = 0; i < allData.size(); i ++){
                    entities.add(new BarEntry(date[i], allData.get(i)));
                    entities2.add(new Entry(date[i],allTemData.get(i)));
                }
                LineDataSet lineDataSet = new LineDataSet(entities2,"Daily Temp");
                BarDataSet barDataSet = new BarDataSet(entities,"Daily Usage");
                lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSet.setValueTextSize(10f);
                barDataSet.setColors(ColorTemplate.rgb("#FF0097E6"));
                barDataSet.setValueTextSize(10f);
                lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                lineData.addDataSet(lineDataSet);
                barData.addDataSet(barDataSet);







                combinedData.setData(lineData);
                combinedData.setData(barData);
                combinedChart.setData(combinedData);
                combinedChart.getXAxis().setLabelCount(allData.size(),true);
                combinedChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


                combinedChart.animateY(1000);

                combinedChart.invalidate();


            }
        }.execute();
    }




    private void setUpHourlyLineChart() {
        final ArrayList<BarEntry> entities = new ArrayList<>();
        final ArrayList<Entry> entities2 = new ArrayList<>();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected Void doInBackground(Void... voids) {
                data = RestClient.getAllHourlyUsage(resid,defaultDate,"hourly");
                return null;
            }



            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                try {
                    jsonArray = new JSONArray(data);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i <jsonArray.length(); i++) {
                    try {
                        allData.add(Float.parseFloat(jsonArray.getJSONObject(i).get("hourlyUsage").toString()));
                        allTemData.add(Float.parseFloat(jsonArray.getJSONObject(i).get("temperature").toString()));
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
                    entities.add(new BarEntry(recordHour.get(i),allData.get(i)));
                    entities2.add(new Entry(recordHour.get(i),allTemData.get(i)));
                }
                LineDataSet lineDataSet = new LineDataSet(entities2,"Hourly Temp");
                BarDataSet barDataSet = new BarDataSet(entities,"Hourly Usage");
                lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                lineDataSet.setValueTextSize(10f);
                barDataSet.setColors(ColorTemplate.rgb("#FF0097E6"));
                barDataSet.setValueTextSize(10f);
                lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                barDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
                lineData.addDataSet(lineDataSet);
                barData.addDataSet(barDataSet);


                combinedData.setData(lineData);
                combinedData.setData(barData);
                combinedChart.setData(combinedData);
                combinedChart.getXAxis().setLabelCount(allData.size(),true);
                combinedChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                combinedChart.getAxisLeft().setAxisMinValue(10);
                combinedChart.getAxisRight().setAxisMinValue(0);


                combinedChart.animateY(1000);

                combinedChart.invalidate();

            }
        }.execute();
    }

}
