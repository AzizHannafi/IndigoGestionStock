package com.example.indigogestionstock.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indigogestionstock.CaptureAct;
import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.Item;
import com.example.indigogestionstock.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReceptionMpFragment extends Fragment {

    EditText code ,designation,quantite;
    Button btn,btnvalidate ;
    ImageView btnback;
    ClientDynamicsWebService client;
    LinearLayout linearLayout;
    TextView message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =inflater.inflate(R.layout.fragment_reception_mp, container, false);
        btnvalidate=v.findViewById(R.id.btnvalidate);
        code = v.findViewById(R.id.qrt);
        btn = v.findViewById(R.id.qr);
        designation=v.findViewById(R.id.designation);
        quantite=v.findViewById(R.id.quantity);
        linearLayout=v.findViewById(R.id.linearLayoutMessage);
        message=v.findViewById(R.id.message);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scancode();
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

        btnvalidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client= new ClientDynamicsWebService();
                String idItem=code.getText().toString();
                //Toast.makeText(getContext(),code.getText().toString(),Toast.LENGTH_LONG).show();
                client.getOneItem(idItem).enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        Toast.makeText(getContext(),response.body().getInventory().toString(),Toast.LENGTH_LONG).show();
                        designation.setText(response.body().getDescription().toString());
                        quantite.setText(response.body().getInventory().toString());
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                       /* if (code.getText().equals(null)){
                            linearLayout.setVisibility(View.VISIBLE);
                            message.setText("Article vide ou intouvable ");
                        }*/



                    }
                });
            }
        });
        return v;
    }

    public void scancode() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setPrompt("Scanner le code-barres");
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        IntentIntegrator.forSupportFragment(this).initiateScan();

    }

    @Override
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                code.setText(result.getContents());
             } else {
                Toast.makeText(getContext(), "échec" , Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
         }
    }


}