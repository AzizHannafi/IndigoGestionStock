package com.example.indigogestionstock.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indigogestionstock.Adapters.PurchaseLineAdapters;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.PurchaseLine;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseLineFragment extends Fragment {
    private RecyclerView recyclerViewPurchaseLine;
    private RecyclerView.LayoutManager layoutManager;
    ClientDynamicsWebService client;
    String No;
    TextView NoOrdrer;
    String noLine;
    List<PurchaseLine> PurchaseLineList= new ArrayList<PurchaseLine>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v=inflater.inflate(R.layout.fragment_purchase_line, container, false);
        NoOrdrer= v.findViewById(R.id.orderno);
        Bundle bundle=this.getArguments();
        noLine=bundle.getString("No");
        NoOrdrer.setText(noLine);
        client= new ClientDynamicsWebService();
        client.getOnePurchaseOrders(noLine).enqueue(new Callback<PurchaseOrders>() {
            @Override
            public void onResponse(Call<PurchaseOrders> call, Response<PurchaseOrders> response) {
                for (int i=0;i<response.body().getPurchLines().size();i++){
                    PurchaseLineList.add(response.body().getPurchLines().get(i));
                }
                recyclerViewPurchaseLine= (RecyclerView) v.findViewById(R.id.recycler_view_purchase_line);
                layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerViewPurchaseLine.setLayoutManager(layoutManager);
                PurchaseLineAdapters PurchaseLineAdapters= new PurchaseLineAdapters(getContext(),PurchaseLineList);
                recyclerViewPurchaseLine.setAdapter(PurchaseLineAdapters);
            }

            @Override
            public void onFailure(Call<PurchaseOrders> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
         return v;
    }
}