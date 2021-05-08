package com.example.indigogestionstock.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indigogestionstock.Fragments.SalesLinesFragment;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.R;

import java.util.List;

public class SalesOrderAdapter extends RecyclerView.Adapter<SalesOrderAdapter.SalesOrderViewHolder> {
    public static Context context;
    List<SalesOrder> salesOrderList;
    FragmentManager fragmentManager;

    public SalesOrderAdapter(Context context, List<SalesOrder> salesOrderList,FragmentManager fragmentManager) {
        this.context=context;
        this.salesOrderList=salesOrderList;
        this.fragmentManager=fragmentManager;
    }

    public static Context getContext() {
        return context;
    }
    @NonNull
    @Override
    public SalesOrderAdapter.SalesOrderViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_sales_order_list_item, parent, false);
        SalesOrderViewHolder salesOrderViewHolder= new SalesOrderViewHolder(view);
        return salesOrderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SalesOrderAdapter.SalesOrderViewHolder holder, int position) {
        SalesOrder salesOrder=salesOrderList.get(position);
        holder.no.setText(salesOrder.getNo());
        holder.sell_to_Customer_Name.setText(salesOrder.getSell_to_Customer_Name());
        holder.location_Code.setText(salesOrder.getLocation_Code());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"order id "+holder.no.getText().toString(),Toast.LENGTH_LONG).show();
                Bundle bundle =new Bundle();
                bundle.putString("No",holder.no.getText().toString());
                SalesLinesFragment salesLinesFragment= new SalesLinesFragment();
                salesLinesFragment.setArguments(bundle);
                fragmentManager .beginTransaction().replace(R.id.fragment_container, salesLinesFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.salesOrderList.size();
    }

    public class SalesOrderViewHolder extends RecyclerView.ViewHolder {
        TextView no,sell_to_Customer_Name,location_Code;
        public SalesOrderViewHolder(@NonNull  View itemView) {
            super(itemView);
            no=(TextView)itemView.findViewById(R.id.no);
            sell_to_Customer_Name=(TextView)itemView.findViewById(R.id.sell_to_Customer_Name);
            location_Code=(TextView)itemView.findViewById(R.id.location_Code);
        }
    }
}
