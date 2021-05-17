package com.example.indigogestionstock.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indigogestionstock.Adapters.SalesLineAdapter;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.SalesLines;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalesLinesFragment extends Fragment {
    private RecyclerView recyclerViewSalesLine;
    private RecyclerView.LayoutManager layoutManager;
    TextView lineno;
    ClientDynamicsWebService client;
    ImageView btnback;
    List<SalesLines> listsl = new ArrayList<SalesLines>();
    TextView No;
    String noLine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sales_lines, container, false);
        No = v.findViewById(R.id.line_no);
        Bundle bundle = this.getArguments();
        noLine = bundle.getString("No");
        No.setText(noLine);
        client = new ClientDynamicsWebService();
        client.getOneSaleOrder(noLine).enqueue(new Callback<SalesOrder>() {
            @Override
            public void onResponse(Call<SalesOrder> call, Response<SalesOrder> response) {
                for (int i = 0; i < response.body().getSalesLines().size(); i++) {
                    listsl.add(response.body().getSalesLines().get(i));
                }
                recyclerViewSalesLine = (RecyclerView) v.findViewById(R.id.recycler_view_sales_line);
                layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerViewSalesLine.setLayoutManager(layoutManager);
                SalesLineAdapter salesLineAdapter = new SalesLineAdapter(getContext(), listsl);
                recyclerViewSalesLine.setAdapter(salesLineAdapter);
            }

            @Override
            public void onFailure(Call<SalesOrder> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnback = (ImageView) v.findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalesOrderFragment SalesOrderFragment = new SalesOrderFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, SalesOrderFragment).commit();
            }
        });
        return v;
    }
}