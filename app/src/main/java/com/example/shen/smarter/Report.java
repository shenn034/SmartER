package com.example.shen.smarter;

import android.os.AsyncTask;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;

public class Report extends Fragment {
    View vReportUnit;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem pie, bar, line;
    PagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vReportUnit = inflater.inflate(R.layout.fragment_report, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Report");

        tabLayout = vReportUnit.findViewById(R.id.tablayout);
        pie = vReportUnit.findViewById(R.id.pie);
        bar = vReportUnit.findViewById(R.id.bar);
        line = vReportUnit.findViewById(R.id.line);
        viewPager = vReportUnit.findViewById(R.id.viewPager);
        pagerAdapter = new PageAdapter(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return vReportUnit;
    }

}
