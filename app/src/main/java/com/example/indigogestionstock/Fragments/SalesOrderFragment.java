package com.example.indigogestionstock.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.indigogestionstock.Adapters.SalesOrderAdapter;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalesOrderFragment extends Fragment {

    private RecyclerView recyclerViewSalesOrder;
    private RecyclerView.LayoutManager layoutManager;
    ClientDynamicsWebService client;
    Button btnPurchaseOrder;
    ImageView btnback;

    //Context context;
    List<SalesOrder> listso = new ArrayList<SalesOrder>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_sales_order, container, false);

        client= new ClientDynamicsWebService();
        client.getAllSalesOrder().enqueue(new Callback<List<SalesOrder>>() {
            @Override
            public void onResponse(Call<List<SalesOrder>> call, Response<List<SalesOrder>> response) {
                listso= response.body();
                recyclerViewSalesOrder= (RecyclerView) v.findViewById(R.id.recyclerViewsalesOrder);
                layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerViewSalesOrder.setLayoutManager(layoutManager);
                SalesOrderAdapter salesOrderAdapter= new SalesOrderAdapter(getContext(),listso,getFragmentManager());
                recyclerViewSalesOrder.setAdapter(salesOrderAdapter);
            }

            @Override
            public void onFailure(Call<List<SalesOrder>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        btnback=(ImageView)v.findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,menuGeneralFragment).commit();


            }
        });

        return  v;
    }
}