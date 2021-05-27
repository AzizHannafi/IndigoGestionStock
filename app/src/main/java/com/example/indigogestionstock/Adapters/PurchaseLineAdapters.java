package com.example.indigogestionstock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.indigogestionstock.Models.PurchaseLine;
import com.example.indigogestionstock.R;



import java.util.List;

public class PurchaseLineAdapters extends RecyclerView.Adapter<PurchaseLineAdapters.PurchaseLineViewHolder>{
    public static Context context;
    List<PurchaseLine> PurchaseLineList;
    public PurchaseLineAdapters(Context context,List<PurchaseLine> PurchaseLineList) {
        this.context=context;
        this.PurchaseLineList = PurchaseLineList;
    }

    public static Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public PurchaseLineAdapters.PurchaseLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_purchase_line_list_item, parent, false);
        PurchaseLineViewHolder PurchaseLineViewHolder= new PurchaseLineViewHolder(view);
        return PurchaseLineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  PurchaseLineAdapters.PurchaseLineViewHolder holder, int position) {
        PurchaseLine pl = PurchaseLineList.get(position);
        holder.Key.setText(pl.getKey());
        holder.type.setText(pl.getType());
        holder.document_No.setText(pl.getDocument_No());
        holder.line_No.setText(pl.getLine_No());
        holder.unit_of_Measure.setText(pl.getUnit_of_Measure());
        holder.unit_of_Measure_Code.setText(pl.getUnit_of_Measure_Code());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"item no "+pl.getDescription(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.PurchaseLineList.size();
    }

    public class PurchaseLineViewHolder extends RecyclerView.ViewHolder {
        TextView Key,type,document_No,line_No,unit_of_Measure,unit_of_Measure_Code;
        public PurchaseLineViewHolder(@NonNull  View itemView) {
            super(itemView);
            Key=(TextView)itemView.findViewById(R.id.key);
            type=(TextView)itemView.findViewById(R.id.Type);
            document_No=(TextView)itemView.findViewById(R.id.document_No);
            line_No=(TextView)itemView.findViewById(R.id.line_No);
            unit_of_Measure=(TextView)itemView.findViewById(R.id.unit_of_Measure);
            unit_of_Measure_Code=(TextView)itemView.findViewById(R.id.unitof_Measure_Code);
        }
    }
}
