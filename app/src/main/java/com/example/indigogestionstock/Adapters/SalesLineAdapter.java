package com.example.indigogestionstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.Key;
import com.example.indigogestionstock.Models.SalesLines;
import com.example.indigogestionstock.R;


import java.util.List;

public class SalesLineAdapter extends RecyclerView.Adapter<SalesLineAdapter.SalesLineViewHolder>{
    public static Context context;
    List<SalesLines> salesLinesList;
    ClientDynamicsWebService client=new ClientDynamicsWebService();
    Key key=new Key();

    public SalesLineAdapter(Context context,List<SalesLines> salesLinesList) {
        this.context=context;
        this.salesLinesList = salesLinesList;}

    @NonNull
    @Override
    public SalesLineAdapter.SalesLineViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_sales_line_list_item, parent, false);
        SalesLineViewHolder saleslineviewholder= new SalesLineViewHolder(view);
        return saleslineviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull  SalesLineAdapter.SalesLineViewHolder holder, int position) {
        SalesLines sl= salesLinesList.get(position);
        holder.Key.setText(sl.getKey());
        holder.line_No.setText(sl.getLine_No());
        holder.Quantity.setText(sl.getQuantity());
    }

    @Override
    public int getItemCount() {
        return this.salesLinesList.size();
    }

    public class SalesLineViewHolder extends RecyclerView.ViewHolder {
        TextView Key,Quantity,line_No;
        public SalesLineViewHolder(@NonNull View itemView) {
            super(itemView);
            Key=(TextView)itemView.findViewById(R.id.key);
            Quantity=(TextView)itemView.findViewById(R.id.quantity);
            line_No=(TextView)itemView.findViewById(R.id.line_No);
        }
    }
}
