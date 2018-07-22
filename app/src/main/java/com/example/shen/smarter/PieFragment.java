package com.example.shen.smarter;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Templates;

import me.drakeet.materialdialog.MaterialDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class PieFragment extends Fragment implements View.OnClickListener{
    String appliance[] = {"aircon", "fridge","washing"} ;
    List<Float> allData = new ArrayList<>();
    String data;
    JSONArray jsonArray;
    String username;
    ImageView imageView;
    Calendar calendar;
    String defaultDate = "2018-03-14";
    PieChart pieChart;
    View vPieFragment;
    int mYear, mMonth, mDay;
    TextView choosenDate;
    String userid;
    String resid;
    ProgressDialog p;
    List<PieEntry> pieEntryList;

    public PieFragment() {
        // Required empty public constructor
        p = null;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        p = new ProgressDialog(getActivity());
        p.setCancelable(false);
        p.setMessage("Loading Data...");
        // Inflate the layout for this fragment
        vPieFragment = inflater.inflate(R.layout.fragment_pie, container, false);
        username = ((Home) getActivity()).getUser();

        imageView = (ImageView) vPieFragment.findViewById(R.id.imageView);
        pieChart = (PieChart) vPieFragment.findViewById(R.id.pieChart);
        choosenDate = vPieFragment.findViewById(R.id.calendar);
        calendar = Calendar.getInstance();
        imageView.setOnClickListener(this);
        resid = ((Home) getActivity()).getResid();
        setUpPieChart();


        choosenDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!choosenDate.getText().equals(defaultDate)) {
                    if (choosenDate.getText().toString().equals("2018-03-13") || choosenDate.getText().toString().equals("2018-03-14")
                            || choosenDate.getText().toString().equals("2018-03-12")){

                        pieEntryList = new ArrayList<>();
                        allData = new ArrayList<>();
                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                p.show();
                            }

                            @Override
                            protected Void doInBackground(Void... voids) {
                                data = RestClient.getDailyForUser(resid, choosenDate.getText().toString());
                                return null;
                            }


                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                try {
                                    jsonArray = new JSONArray(data);

                                    allData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("airUsage").toString()));
                                    allData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("fridgeUsage").toString()));
                                    allData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("washingUsage").toString()));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                for (int i = 0; i < 3; i++) {
                                    pieEntryList.add(new PieEntry(allData.get(i), appliance[i]));
                                }
                                PieDataSet dataSet = new PieDataSet(pieEntryList, "Daily Usage");
                                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                ;
                                dataSet.setValueTextSize(20f);
                                dataSet.setValueFormatter(new PercentFormatter());
                                PieData data2 = new PieData(dataSet);

                                pieChart.setData(data2);
                                pieChart.setUsePercentValues(true);


                                pieChart.animateY(1000);

                                pieChart.invalidate();


                                p.dismiss();
                            }
                        }.execute();
                    }
                    else {
                        final MaterialDialog materialdialognow = new MaterialDialog(getActivity());
                        materialdialognow.setTitle("Choose another date!")
                                .setMessage("No data on this date, will cause error!")
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
            }
        });

        return vPieFragment;
    }

    private void setUpPieChart() {
        pieEntryList = new ArrayList<>();
        choosenDate.setText(defaultDate);


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                p.show();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                data = RestClient.getDailyForUser(resid,defaultDate);
                return null;
            }



            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    jsonArray = new JSONArray(data);

                    allData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("airUsage").toString()));
                    allData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("fridgeUsage").toString()));
                    allData.add(Float.parseFloat(jsonArray.getJSONObject(0).get("washingUsage").toString()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 3; i ++){
                    pieEntryList.add(new PieEntry(allData.get(i),appliance[i]));
                }
                PieDataSet dataSet = new PieDataSet(pieEntryList,"Daily Usage");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextSize(20f);
                dataSet.setValueFormatter(new PercentFormatter());
                PieData data2 = new PieData(dataSet);
                pieChart.setData(data2);
                pieChart.animateY(1000);
                pieChart.setUsePercentValues(true);

                pieChart.invalidate();


                p.dismiss();
            }
        }.execute();
    }

    public String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateFormat = format.format(date);
        String dateAfterFormate = dateFormat + "+10:00";
        return dateAfterFormate;

    }

    @Override
    public void onClick(View v) {


        new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker v, int year,
                                          int month, int day) {
                        // TODO Auto-generated method stub
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                        choosenDate.setText(new StringBuilder()
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




