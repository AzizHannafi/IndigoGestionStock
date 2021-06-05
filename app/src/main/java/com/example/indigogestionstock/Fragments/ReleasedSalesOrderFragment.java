package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indigogestionstock.Adapters.SalesOrderAdapter;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.Models.User;
import com.example.indigogestionstock.R;
import com.example.indigogestionstock.UserManager.UserSessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReleasedSalesOrderFragment extends Fragment {
    private RecyclerView recyclerViewSalesOrder;
    private RecyclerView.LayoutManager layoutManager;

    ClientDynamicsWebService client;
    public User userInfo = new User();

    UserSessionManager session;
    //String locationCode="null" ;
    Dialog alertDialog;
    TextView message, titleDialogue;
    ImageView imageDialogue;

    EditText searchBar;
    ImageView searchIcon;


    //Context context;
    List<SalesOrder> listso = new ArrayList<SalesOrder>();

    public static ReleasedSalesOrderFragment getInstance() {
        ReleasedSalesOrderFragment ReleasedSalesOrderFragment = new ReleasedSalesOrderFragment();
        return ReleasedSalesOrderFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_released_sales_order, container, false);

        searchBar = v.findViewById(R.id.search_bar);
        searchIcon = v.findViewById(R.id.searchIncon);

        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        //get element of sidebare header
        String id = user.get(UserSessionManager.KEY_ID);

        recyclerViewSalesOrder = (RecyclerView) v.findViewById(R.id.recyclerViewsalesOrderRelease);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewSalesOrder.setLayoutManager(layoutManager);

        client = new ClientDynamicsWebService();
        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);
        message = alertDialog.findViewById(R.id.messageError);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);

        getOrders(id);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBar.getText().length()==0){
                    getOrders(id);
                }else {
                    client.getOneReleasedSalesOrder(searchBar.getText().toString()).enqueue(new Callback<List<SalesOrder>>() {
                        @Override
                        public void onResponse(Call<List<SalesOrder>> call, Response<List<SalesOrder>> response) {
                            listso = response.body();

                            // layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

                            SalesOrderAdapter salesOrderAdapter = new SalesOrderAdapter(getContext(), listso, getFragmentManager());
                            recyclerViewSalesOrder.setAdapter(salesOrderAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<SalesOrder>> call, Throwable t) {

                        }
                    });
                }

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

    public void getOrders(String id) {

        client.getUserByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userInfo = response.body();
                client.GetAllReleasedSalesOrder().enqueue(new Callback<List<SalesOrder>>() {
                    @Override
                    public void onResponse(Call<List<SalesOrder>> call, Response<List<SalesOrder>> response) {
                        listso = response.body();
                        if (listso.size() == 0) {
                            ErrorAlert("Pas de commande", "Cet intropot ne possède pas des commandes pour le moment");
                            alertDialog.show();
                        } else {
                            // recyclerViewSalesOrder = (RecyclerView) v.findViewById(R.id.recyclerViewsalesOrderRelease);

                            //recyclerViewSalesOrder.setLayoutManager(layoutManager);
                            SalesOrderAdapter salesOrderAdapter = new SalesOrderAdapter(getContext(), listso, getFragmentManager());
                            recyclerViewSalesOrder.setAdapter(salesOrderAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SalesOrder>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}