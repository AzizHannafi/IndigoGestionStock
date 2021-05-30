package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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


public class TransfertFragment extends Fragment {
    Dialog alertDialog;
    ClientDynamicsWebService client;
    EditText code;
    Button btn, btnTransférer;
   // ImageView btnback;
    TextView message, titleDialogue;
    ImageView imageDialogue, btnsend;
    LinearLayout LLdescription, LLRayonActuel, LLRayonTransfert;
    TextView description, rayonActuel;
    Spinner spinnerRayon;
    public static TransfertFragment getInstance() {
        TransfertFragment TransfertFragment = new TransfertFragment();
        return TransfertFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_transfert, container, false);
        code = v.findViewById(R.id.qrt);
        btn = v.findViewById(R.id.qr);
        btnTransférer = v.findViewById(R.id.btnTransférer);
        btnsend = v.findViewById(R.id.send);
        description = v.findViewById(R.id.description);
        rayonActuel = v.findViewById(R.id.rayonActuel);
        spinnerRayon = v.findViewById(R.id.spinnerRayon);
        LLRayonTransfert = v.findViewById(R.id.LLRayonTransfert);

        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);
        message = alertDialog.findViewById(R.id.messageError);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);

        LLdescription = v.findViewById(R.id.LLdescription);
        LLRayonActuel = v.findViewById(R.id.LLRayonActuel);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Rayon, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRayon.setAdapter(adapter);

        client = new ClientDynamicsWebService();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scancode();
            }
        });
/*
        btnback = (ImageView) v.findViewById(R.id.btnBack);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, menuGeneralFragment).commit();


            }
        });*/

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.getOneItemWithShelf(code.getText().toString()).enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        if (code.getText().length() == 0) {
                            ErrorAlert("Code à barres est vide", "Vous devez scanner ou saisir un code à barres");
                            alertDialog.show();
                            LLdescription.setVisibility(View.GONE);
                            LLRayonActuel.setVisibility(View.GONE);
                            LLRayonTransfert.setVisibility(View.GONE);

                        } else if (response.body().getNo().toString().equals("null")) {
                            ErrorAlert("Echec", "Article introuvable vérifier le code à barres saisies");
                            alertDialog.show();
                            LLdescription.setVisibility(View.GONE);
                            LLRayonActuel.setVisibility(View.GONE);
                            LLRayonTransfert.setVisibility(View.GONE);
                        } else {
                            LLdescription.setVisibility(View.VISIBLE);
                            LLRayonActuel.setVisibility(View.VISIBLE);
                            LLRayonTransfert.setVisibility(View.VISIBLE);
                            description.setText(response.body().getDescription().toString());
                            rayonActuel.setText(response.body().getShelf_No());


                        }
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

        btnTransférer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(spinnerRayon.getSelectedItem().toString());
                client.getOneItemWithShelf(code.getText().toString()).enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        if (code.getText().length() == 0) {
                            ErrorAlert("Code à barres est vide", "Vous devez scanner ou saisir un code à barres");
                            alertDialog.show();
                            LLdescription.setVisibility(View.GONE);
                            LLRayonActuel.setVisibility(View.GONE);
                            LLRayonTransfert.setVisibility(View.GONE);

                        } else if (response.body().getNo().toString().equals("null")) {
                            ErrorAlert("Echec", "Article introuvable vérifier le code à barres saisies");
                            alertDialog.show();
                            LLdescription.setVisibility(View.GONE);
                            LLRayonActuel.setVisibility(View.GONE);
                            LLRayonTransfert.setVisibility(View.GONE);
                        } else {
                            LLdescription.setVisibility(View.VISIBLE);
                            LLRayonActuel.setVisibility(View.VISIBLE);
                            LLRayonTransfert.setVisibility(View.VISIBLE);
                            String idEmplacement=spinnerRayon.getSelectedItem().toString();
                            client.updateEmplacement(code.getText().toString(),idEmplacement).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    ChechedAlert("Transfert Réussi", "l'article " + code.getText().toString() + " a été transférer avec sucsés");
                                    alertDialog.show();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                code.setText(result.getContents());
            } else {
                Toast.makeText(getContext(), "échec", Toast.LENGTH_LONG).show();
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