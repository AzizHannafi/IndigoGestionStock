package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.graphics.Color;
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

import com.example.indigogestionstock.Adapters.PurchaseOrdersAdapter;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.Models.User;
import com.example.indigogestionstock.R;
import com.example.indigogestionstock.UserManager.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PurchaseFragment extends Fragment {
    private RecyclerView recyclerViewSalesOrder;
    private RecyclerView.LayoutManager layoutManager;
    ClientDynamicsWebService client;
    ImageView btnback;
    public User userInfo = new User();
    UserSessionManager session;
    //String locationCode="null" ;
    Dialog alertDialog;
    TextView message, titleDialogue;
    ImageView imageDialogue;
    List<PurchaseOrders> listPo = new ArrayList<PurchaseOrders>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_purchase, container, false);
        client = new ClientDynamicsWebService();
        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        //get element of sidebare header
        String id = user.get(UserSessionManager.KEY_ID);


        client = new ClientDynamicsWebService();
        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);
        message = alertDialog.findViewById(R.id.messageError);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);

        client.getUserByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userInfo = response.body();
                client.getAllPurchaseOrders(userInfo.getLocationCode().toString()).enqueue(new Callback<List<PurchaseOrders>>() {
                    @Override
                    public void onResponse(Call<List<PurchaseOrders>> call, Response<List<PurchaseOrders>> response) {
                        listPo = response.body();
                        if (listPo.size() == 0) {
                            ErrorAlert("Pas de commande", "Cet intropot ne possède pas des commandes pour le moment");
                            alertDialog.show();
                        } else {
                            recyclerViewSalesOrder = (RecyclerView) v.findViewById(R.id.recyclerViewPurchaseOrder);
                            layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            recyclerViewSalesOrder.setLayoutManager(layoutManager);
                            PurchaseOrdersAdapter purchaseOrdersAdapter = new PurchaseOrdersAdapter(getContext(), listPo, getFragmentManager());
                            recyclerViewSalesOrder.setAdapter(purchaseOrdersAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PurchaseOrders>> call, Throwable t) {
                        Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });


        btnback = (ImageView) v.findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, menuGeneralFragment).commit();
            }
        });
        return v;
    }

    public void ErrorAlert(String title, String bodyAlert) {
        titleDialogue.setText(title);
        titleDialogue.setTextColor(Color.rgb(178, 34, 34));
        message.setText(bodyAlert);
        message.setTextColor(Color.rgb(178, 34, 34));
        imageDialogue.setBackgroundResource(R.drawable.alert);
    }
}