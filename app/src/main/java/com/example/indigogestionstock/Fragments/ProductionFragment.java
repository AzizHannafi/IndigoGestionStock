package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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

public class ProductionFragment extends Fragment {
    EditText code ;
    Button btn ;


    Button btnnValidate;
    Dialog alertDialog;
    TextView message, titleDialogue;
    TextView description, stock, UnitéDeMesure;
    ImageView imageDialogue;
    ClientDynamicsWebService client;
    LinearLayout LLdescription, LLStock, LLUM;

    public  static ProductionFragment getInstance(){
        ProductionFragment ProductionFragment =new ProductionFragment();
        return ProductionFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v =inflater.inflate(R.layout.fragment_production, container, false);


        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);
        btnnValidate = v.findViewById(R.id.btnvalidateCmd);
        message = alertDialog.findViewById(R.id.messageError);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);
        client = new ClientDynamicsWebService();
        description = v.findViewById(R.id.description);
        stock = v.findViewById(R.id.stock);
        UnitéDeMesure = v.findViewById(R.id.UnitéDeMesure);
        LLdescription = v.findViewById(R.id.LLdescription);
        LLStock = v.findViewById(R.id.LLStock);
        LLUM = v.findViewById(R.id.LLUM);

        code = v.findViewById(R.id.qrt);
        btn = v.findViewById(R.id.qr);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scancode();
            }
        });

        btnnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.GetBilanSalesOneItem(code.getText().toString()).enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        if (code.getText().length() == 0) {
                            ErrorAlert("Code à barres est vide", "Vous devez scanner ou saisir un code à barres");
                            alertDialog.show();
                            LLdescription.setVisibility(View.GONE);
                            LLStock.setVisibility(View.GONE);
                            LLUM.setVisibility(View.GONE);

                        } else if (response.body().getNo().toString().equals("null")) {
                            ErrorAlert("Echec", "Article introuvable vérifier le code à barres saisies");
                            alertDialog.show();
                            LLdescription.setVisibility(View.GONE);
                            LLStock.setVisibility(View.GONE);
                            LLUM.setVisibility(View.GONE);
                        }else {
                            LLdescription.setVisibility(View.VISIBLE);
                            LLStock.setVisibility(View.VISIBLE);
                            LLUM.setVisibility(View.VISIBLE);
                            description.setText(response.body().getDescription());
                            stock.setText(response.body().getInventory());
                            UnitéDeMesure.setText(response.body().getBase_Unit_of_Measure());
                            ChechedAlert("Bilan","Vous avez vendu "+response.body().getInventory()+"unité(es) de l'article '"+response.body().getDescription()+"'");
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {

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

    public void ChechedAlert(String title, String bodyAlert) {
        titleDialogue.setText(title);
        titleDialogue.setTextColor(Color.rgb(32, 178, 170));
        message.setText(bodyAlert);
        message.setTextColor(Color.rgb(32, 178, 170));
        imageDialogue.setBackgroundResource(R.drawable.check);
    }

    public void ErrorAlert(String title, String bodyAlert) {
        titleDialogue.setText(title);
        titleDialogue.setTextColor(Color.rgb(178, 34, 34));
        message.setText(bodyAlert);
        message.setTextColor(Color.rgb(178, 34, 34));
        imageDialogue.setBackgroundResource(R.drawable.alert);
    }

}