package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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
import com.example.indigogestionstock.Models.SalesLines;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.R;
import com.example.indigogestionstock.UserManager.UserSessionManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreparationCommnadeFragment extends Fragment {
    EditText commande, code, quantity;
    Button btn, btnValidateCmd, btnValidateClient, btnvalidateArticle;
    ImageView btnback, btnCommande, btnsend;
    Spinner spinnerClient;
    CardView cardViewClient, cardArticle, cardPreparer;
    int capteurBtnCommande;
    int capteurBtnArticle;
    int resultOfTables = 0;
    TextView description, btnPreparer;
    Boolean found = false;


    Dialog alertDialog;
    TextView message, titleDialogue;
    ImageView imageDialogue;
    ImageView confirme, cancel;
    LinearLayout LinearLayoutDialogue, LLquntité;

    UserSessionManager session;
    ClientDynamicsWebService client;
    SalesOrder salesOrder;
    List<SalesLines> salesLinesList;
    List<SalesLines> ConfirmesalesLinesList = new ArrayList<>();

    SalesLines salesLines = new SalesLines();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_preparation_commnade, container, false);
        capteurBtnArticle = 0;
        capteurBtnCommande = 0;


        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        //get element of sidebare header
        String id = user.get(UserSessionManager.KEY_ID);
        cardPreparer = v.findViewById(R.id.cardPreparer);
        btnPreparer = v.findViewById(R.id.btnPreparer);
        quantity = v.findViewById(R.id.quantity);
        description = v.findViewById(R.id.description);
        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);
        message = alertDialog.findViewById(R.id.messageError);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);
        confirme = alertDialog.findViewById(R.id.Yes);
        cancel = alertDialog.findViewById(R.id.no);
        LinearLayoutDialogue = alertDialog.findViewById(R.id.linearLayoutDialogue);

        LLquntité = v.findViewById(R.id.LLquntité);
        cardViewClient = v.findViewById(R.id.cardClient);
        cardArticle = v.findViewById(R.id.cardArticle);
        commande = v.findViewById(R.id.commandeNo);
        code = v.findViewById(R.id.qrt);
        btn = v.findViewById(R.id.qr);
        spinnerClient = v.findViewById(R.id.spinnerClient);
        btnCommande = v.findViewById(R.id.btnCommnade);
        btnback = (ImageView) v.findViewById(R.id.btnBack);
        btnvalidateArticle = v.findViewById(R.id.btnvalidateArticle);

        client = new ClientDynamicsWebService();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Clients, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClient.setAdapter(adapter);

        btnValidateCmd = v.findViewById(R.id.btnvalidateCmd);
        btnValidateClient = v.findViewById(R.id.btnvalidateClient);
        btnsend = v.findViewById(R.id.Btnsend);

        btnValidateCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commande.getText().length() == 0) {
                    ErrorAlert("Code à barres est vide", "Vous devez scanner ou saisir un code à barres");
                    LinearLayoutDialogue.setVisibility(View.GONE);
                    alertDialog.show();
                } else {

                    String idCommande = commande.getText().toString();
                    client.getOneSaleOrder(idCommande).enqueue(new Callback<SalesOrder>() {
                        @Override
                        public void onResponse(Call<SalesOrder> call, Response<SalesOrder> response) {
                            if (response.body().getNo().toString().equals("null")) {
                                ErrorAlert("Echec", "Commande introuvable vérifier le code à barres saisies");
                                LinearLayoutDialogue.setVisibility(View.GONE);
                                alertDialog.show();
                                cardViewClient.setVisibility(View.GONE);
                            } else {
                                salesOrder = new SalesOrder();
                                salesLinesList = new ArrayList<>();
                                salesLinesList.clear();
                                ChechedAlert("Commande existante ", "");
                                LinearLayoutDialogue.setVisibility(View.GONE);
                                alertDialog.show();
                                cardViewClient.setVisibility(View.VISIBLE);
                                salesOrder = response.body();
                                salesLinesList = salesOrder.getSalesLines();
                                ConfirmesalesLinesList.clear();
                            }
                        }

                        @Override
                        public void onFailure(Call<SalesOrder> call, Throwable t) {
                            System.out.println(t.getMessage());
                        }
                    });
                }


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capteurBtnArticle = 1;
                scancode();
            }
        });
        btnValidateClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerClient.getSelectedItem().toString().equals(salesOrder.getSell_to_Customer_Name().toString())) {
                    ChechedAlert("Client Verifié", "Le client saisi est conforme au client de la commande");
                    LinearLayoutDialogue.setVisibility(View.GONE);
                    alertDialog.show();
                    cardArticle.setVisibility(View.VISIBLE);
                } else {
                    ErrorAlert("Client non Verifié", "Le client saisi n'est pas conforme au client de la commande");
                    LinearLayoutDialogue.setVisibility(View.GONE);
                    alertDialog.show();
                    cardArticle.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code.getText().toString().length() == 0) {
                    ErrorAlert("Echec", "Vous devez scanner le code a bare de l'Article d'abord");
                    LinearLayoutDialogue.setVisibility(View.GONE);
                    alertDialog.show();
                } else {
                    client.getOneItem(code.getText().toString()).enqueue(new Callback<Item>() {
                        @Override
                        public void onResponse(Call<Item> call, Response<Item> response) {
                            if (response.body().getNo().toString().equals("null")) {
                                ErrorAlert("Echec", "Article introuvale ");
                                LinearLayoutDialogue.setVisibility(View.GONE);
                                alertDialog.show();
                                LLquntité.setVisibility(View.GONE);
                                description.setText("");
                                btnvalidateArticle.setVisibility(View.GONE);
                                // btnPreparer.setVisibility(View.GONE);
                            } else {
                                description.setText(response.body().getDescription().toString());
                                LLquntité.setVisibility(View.VISIBLE);
                                btnvalidateArticle.setVisibility(View.VISIBLE);
                                btnPreparer.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Item> call, Throwable t) {
                            System.out.println(t.getMessage());
                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (salesOrder.getStatus().toString().equals("Released")) {
                    LinearLayoutDialogue.setVisibility(View.INVISIBLE);
                    ErrorAlert("Echec Preparation ", "La Commande est Déjà préparer");
                    alertDialog.show();
                } else if (ConfirmesalesLinesList.size() == salesLinesList.size()) {
                    for (int i = 0; i < salesLinesList.size(); i++) {
                        for (int j = 0; j < ConfirmesalesLinesList.size(); j++) {
                            if (ConfirmesalesLinesList.get(j) == salesLinesList.get(i)) {
                                resultOfTables += 1;
                            }
                            if (resultOfTables == salesLinesList.size()) {
                                client.addToPreparetion(id, commande.getText().toString()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        LinearLayoutDialogue.setVisibility(View.GONE);
                                        ChechedAlert("Preparation réussite", "Cette Commande a été préparer correctement");
                                        alertDialog.show();
                                        System.out.println("Ligne Ajouté");
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        System.out.println(t.getMessage());
                                    }
                                });

                            }
                        }

                    }


                } else {
                    ErrorAlert("Echec", "La commande preparer n'est pas conforme a la commande demender");
                    LinearLayoutDialogue.setVisibility(View.INVISIBLE);
                    alertDialog.show();
                }
            }

        });
        btnCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capteurBtnCommande = 1;
                scancode();
            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, menuGeneralFragment).commit();


            }
        });

        btnvalidateArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                found = false;
                if (quantity.getText().toString().length() == 0) {
                    ErrorAlert("Echec", "Vous devez saisir une quantité");
                    LinearLayoutDialogue.setVisibility(View.GONE);
                    alertDialog.show();
                } else if (checkIfExist() == true) {
                    System.out.println("found it");
                    if (checkIfLiterallyExist()) {
                        if (ConfirmesalesLinesList.size() == 0) {
                            ChechedAlert("Article Verifié", "cet atricle appartient à cette commande et la quantité est conforme à la commande ");
                            ConfirmesalesLinesList.add(getLine());
                            LinearLayoutDialogue.setVisibility(View.GONE);
                            alertDialog.show();
                        } else if (ConfirmesalesLinesList.size() > 0) {
                            if (checkIfExistInConfirmeList() == true) {
                                ErrorAlert("Article déjà scanner", "");
                                LinearLayoutDialogue.setVisibility(View.GONE);
                                alertDialog.show();
                            }
                            else if (checkIfExistInConfirmeList() == false){
                                ChechedAlert("Article Verifié", "cet atricle appartient à cette commande et la quantité est conforme à la commande ");
                                ConfirmesalesLinesList.add(getLine());
                                LinearLayoutDialogue.setVisibility(View.GONE);
                                alertDialog.show();
                            }
                        }
                    } else {
                        System.out.println("article found but quantity not much");
                        ErrorAlert("Article non  Verifié", "cet atricle appartient à cette commande, mais la quantité n'est conforme à la commande ");
                        LinearLayoutDialogue.setVisibility(View.GONE);
                        quantity.setText("");
                        alertDialog.show();
                    }

                } else {
                    System.out.println("article not found");
                    ErrorAlert("Article Invalide", "cet atricle n'appartient pas à cette commande !");
                    code.setText("");
                    description.setText("");
                    quantity.setText("");
                    alertDialog.show();
                }

            }

        });

        btnPreparer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChechedAlert("Preparation", "Vous Voulez valider la preparation du commande?");
                LinearLayoutDialogue.setVisibility(View.VISIBLE);
                alertDialog.show();
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
                if (capteurBtnCommande == 1) {
                    commande.setText(result.getContents());
                    capteurBtnCommande = 0;
                }
                if (capteurBtnArticle == 1) {
                    code.setText(result.getContents());
                    capteurBtnArticle = 0;
                }
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

    public void restArticle() {
        code.setText("");
        description.setText("");
        quantity.setText("");
        LLquntité.setVisibility(View.GONE);
        btnvalidateArticle.setVisibility(View.GONE);

    }

    public boolean checkIfExist() {
        boolean res = false;
        for (int i = 0; i < salesLinesList.size(); i++) {

            if (salesLinesList.get(i).getNo().toString().equals(code.getText().toString())) {

                res = true;

            }
        }
        return res;
    }

    public boolean checkIfLiterallyExist() {
        boolean res = false;
        for (int i = 0; i < salesLinesList.size(); i++) {

            if (salesLinesList.get(i).getNo().toString().equals(code.getText().toString())) {
                if (salesLinesList.get(i).getQuantity().toString().equals(quantity.getText().toString())) {
                    res = true;
                }
            }
        }
        return res;
    }

    public boolean checkIfExistInConfirmeList() {
        boolean res = false;
        for (int i = 0; i < ConfirmesalesLinesList.size(); i++) {

            if (ConfirmesalesLinesList.get(i).getNo().toString().equals(code.getText().toString())) {
                if (ConfirmesalesLinesList.get(i).getQuantity().toString().equals(quantity.getText().toString())) {
                    res = true;
                }
            }
        }
        return res;
    }

    public SalesLines getLine() {
        SalesLines salesLines = new SalesLines();
        for (int i = 0; i < salesLinesList.size(); i++) {

            if (salesLinesList.get(i).getNo().toString().equals(code.getText().toString())) {
                if (salesLinesList.get(i).getQuantity().toString().equals(quantity.getText().toString())) {
                    salesLines = salesLinesList.get(i);
                }
            }
        }
        return salesLines;
    }
}