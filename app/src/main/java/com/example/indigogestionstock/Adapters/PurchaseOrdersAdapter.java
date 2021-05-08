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

import com.example.indigogestionstock.Fragments.PurchaseLineFragment;
import com.example.indigogestionstock.Fragments.SalesLinesFragment;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.R;



import java.util.List;

public class PurchaseOrdersAdapter extends RecyclerView.Adapter<PurchaseOrdersAdapter.PurchaseOrdersViewHolder>{
    public static Context context;
    List<PurchaseOrders> PurchaseOrdersList;
    FragmentManager fragmentManager;


    public PurchaseOrdersAdapter(Context context,List<PurchaseOrders> salesOrderList,FragmentManager fragmentManager) {
        this.context=context;
        this.PurchaseOrdersList = salesOrderList;
        this.fragmentManager=fragmentManager;
    }

    public static Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public PurchaseOrdersAdapter.PurchaseOrdersViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_purchase_order_list_item, parent, false);
        PurchaseOrdersViewHolder purchaseOrdersViewHolder= new PurchaseOrdersViewHolder(view);
        return purchaseOrdersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  PurchaseOrdersAdapter.PurchaseOrdersViewHolder holder, int position) {
        PurchaseOrders purchaseOrders=PurchaseOrdersList.get(position);
        holder.no.setText(purchaseOrders.getNo());
        holder.buy_from_Vendor_Name.setText(purchaseOrders.getBuy_from_Vendor_Name());
        holder.buy_from_Vendor_No.setText(purchaseOrders.getBuy_from_Vendor_No());
        holder.posting_Description.setText(purchaseOrders.getPosting_Description());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"order id "+holder.no.getText().toString(),Toast.LENGTH_LONG).show();
                Bundle bundle =new Bundle();
                bundle.putString("No",holder.no.getText().toString());
                PurchaseLineFragment purchaseLineFragment= new PurchaseLineFragment();
                purchaseLineFragment.setArguments(bundle);
                fragmentManager .beginTransaction().replace(R.id.fragment_container, purchaseLineFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.PurchaseOrdersList.size();
    }

    public class PurchaseOrdersViewHolder extends RecyclerView.ViewHolder {
        TextView no,buy_from_Vendor_Name,posting_Description,buy_from_Vendor_No;
        public PurchaseOrdersViewHolder(@NonNull  View itemView) {
            super(itemView);
            no=(TextView)itemView.findViewById(R.id.noPurchaseOrder);
            buy_from_Vendor_Name=(TextView)itemView.findViewById(R.id.VendorName);
            buy_from_Vendor_No=(TextView)itemView.findViewById(R.id.buy_from_Vendor_No);
            posting_Description=(TextView)itemView.findViewById(R.id.posting_Description);
        }
    }
}
