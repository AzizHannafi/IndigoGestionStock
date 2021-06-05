package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indigogestionstock.Adapters.SalesOrderAdapter;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.Models.User;
import com.example.indigogestionstock.PagerAdapter.ViewPagerAdapter;
import com.example.indigogestionstock.R;
import com.example.indigogestionstock.UserManager.UserSessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalesOrderFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView btnback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sales_order, container, false);


        tabLayout=(TabLayout)v.findViewById(R.id.SalesOrderTabLayout);
        viewPager=(ViewPager)v.findViewById(R.id.SalesOrderViewPager);

        ViewPagerAdapter ViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        ViewPagerAdapter.addFragment(ReleasedSalesOrderFragment.getInstance(),"Fermer");
        ViewPagerAdapter.addFragment(OpenSalesOrderFragment.getInstance(),"Ouvert");

        viewPager.setAdapter(ViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        btnback = (ImageView) v.findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, menuGeneralFragment).commit();


            }
        });
        return v;
    }

}