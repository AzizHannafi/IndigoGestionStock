package com.example.indigogestionstock.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indigogestionstock.R;

public class DechargementFragment extends Fragment {

    public  static DechargementFragment getInstance(){
        DechargementFragment DechargementFragment=new DechargementFragment();
        return DechargementFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_dechargement, container, false);

        return v;
    }
}