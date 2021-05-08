package com.example.indigogestionstock.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.indigogestionstock.PagerAdapter.ViewPagerAdapter;
import com.example.indigogestionstock.R;
import com.google.android.material.tabs.TabLayout;


public class ConsommationDeclarationFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView btnback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v= inflater.inflate(R.layout.fragment_consommation_declaration, container, false);

        tabLayout=(TabLayout)v.findViewById(R.id.ConsomationDeclarationTabLayout);
        viewPager=(ViewPager)v.findViewById(R.id.ConsomationDeclarationViewPager);

        ViewPagerAdapter ViewPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        ViewPagerAdapter.addFragment(ConsomationFragment.getInstance(),"Consaommation");
        ViewPagerAdapter.addFragment(DeclarationFragment.getInstance(),"Déclaration");

        viewPager.setAdapter(ViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        btnback=(ImageView)v.findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,menuGeneralFragment).commit();


            }
        });
        return v;
    }
}