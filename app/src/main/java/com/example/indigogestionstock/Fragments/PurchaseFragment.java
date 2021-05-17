package com.example.indigogestionstock.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.indigogestionstock.Adapters.PurchaseOrdersAdapter;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseFragment extends Fragment {
    private RecyclerView recyclerViewSalesOrder;
    private RecyclerView.LayoutManager layoutManager;
    ClientDynamicsWebService client;
    ImageView btnback;
    List<PurchaseOrders> listPo = new ArrayList<PurchaseOrders>();
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_purchase, container, false);
        client= new ClientDynamicsWebService();
        client.getAllPurchaseOrders().enqueue(new Callback<List<PurchaseOrders>>() {
            @Override
            public void onResponse(Call<List<PurchaseOrders>> call, Response<List<PurchaseOrders>> response) {
                listPo= response.body();
                recyclerViewSalesOrder= (RecyclerView)v.findViewById(R.id.recyclerViewPurchaseOrder);
                layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerViewSalesOrder.setLayoutManager(layoutManager);
                PurchaseOrdersAdapter purchaseOrdersAdapter= new PurchaseOrdersAdapter(getContext(),listPo,getFragmentManager());
                recyclerViewSalesOrder.setAdapter(purchaseOrdersAdapter);
            }


            @Override
            public void onFailure(Call<List<PurchaseOrders>> call, Throwable t) {

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
        return v;
    }
}