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
import com.example.indigogestionstock.Models.Key;
import com.example.indigogestionstock.Models.PurchaseLine;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.Models.Rejet;
import com.example.indigogestionstock.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RejetFragment extends Fragment {
    LinearLayout LinearLayoutDesignation, LinearLayoutDialogue;
    EditText code, commande, designation;
    Button btn, btnCommande, btnvalidateCmd;
    int capteurBtnCommande;
    int capteurBtnArticle;
    Dialog alertDialog;
    TextView message, titleDialogue;
    ImageView imageDialogue, confirme, cancel,delete;
    ClientDynamicsWebService client;

    public static RejetFragment getInstance() {
        RejetFragment RejetFragment = new RejetFragment();
        return RejetFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        client = new ClientDynamicsWebService();
        capteurBtnCommande = 0;
        capteurBtnArticle = 0;
        View v = inflater.inflate(R.layout.fragment_rejet, container, false);
        code = v.findViewById(R.id.qrt);
        btn = v.findViewById(R.id.qr);
        btnCommande = v.findViewById(R.id.btnCommande);
        commande = v.findViewById(R.id.commande);
        btnvalidateCmd = v.findViewById(R.id.btnvalidateCmd);
        delete=v.findViewById(R.id.delete);


        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);
        message = alertDialog.findViewById(R.id.messageError);
        confirme = alertDialog.findViewById(R.id.Yes);
        cancel = alertDialog.findViewById(R.id.no);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);
        LinearLayoutDialogue=alertDialog.findViewById(R.id.linearLayoutDialogue);

        designation = v.findViewById(R.id.designation);
        LinearLayoutDesignation = v.findViewById(R.id.LinearLayoutDesignation);
        btnCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capteurBtnCommande = 1;
                scancode();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capteurBtnArticle = 1;
                scancode();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 LinearLayoutDialogue.setVisibility(View.VISIBLE);
                 ErrorAlert("Supprimer","Vous etes sur vous voulez supprimer la commande ?");
                 alertDialog.show();
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
                client.getOnePurchaseOrders(commande.getText().toString()).enqueue(new Callback<PurchaseOrders>() {
                    @Override
                    public void onResponse(Call<PurchaseOrders> call, Response<PurchaseOrders> response) {
                        if (response.body().getNo().equals("Null")){
                            LinearLayoutDialogue.setVisibility(View.GONE);
                            ErrorAlert("Echec","Commande introuvale");
                            alertDialog.show();
                        }else {
                            Key key= new Key();
                            key.setKey(response.body().getKey());
                            client.deletePurchaseOrder(key).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    LinearLayoutDialogue.setVisibility(View.GONE);
                                    ChechedAlert("Suppression ","Commande supprimer  avec succés");
                                    alertDialog.show();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    System.out.println(t.getMessage());
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<PurchaseOrders> call, Throwable t) {

                    }
                });
            }
        });
        btnvalidateCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((commande.getText().toString().length() == 0) || (code.getText().toString().length() == 0)) {
                    ErrorAlert("Echec", "Vous devez saisir le code a barre de la commande et de l'article");
                    LinearLayoutDesignation.setVisibility(View.INVISIBLE);
                    alertDialog.show();

                } else {
                    LinearLayoutDesignation.setVisibility(View.VISIBLE);
                    Rejet rejet = new Rejet(commande.getText().toString(), code.getText().toString());
                    client.getOneRejet(rejet).enqueue(new Callback<Rejet>() {
                        @Override
                        public void onResponse(Call<Rejet> call, Response<Rejet> response) {
                            if ((response.body().getIdCommande().toString().equals(commande.getText().toString())) &&
                                    response.body().getIDItem().toString().equals(code.getText().toString())) {
                                client.getOnePurchaseOrders(commande.getText().toString()).enqueue(new Callback<PurchaseOrders>() {
                                    @Override
                                    public void onResponse(Call<PurchaseOrders> call, Response<PurchaseOrders> response) {
                                        if(response.body().getNo().toString().equals("null")){
                                            ErrorAlert("Commande Introuvable","c'est commande n'est plus disponible!!! ");
                                            alertDialog.show();
                                        }else{
                                            List<PurchaseLine> purchaseLinestab = response.body().getPurchLines();
                                            String Designation = null;
                                            for (int i = 0; i < purchaseLinestab.size(); i++) {
                                                if (purchaseLinestab.get(i).getItemNo().equals(code.getText().toString())) {
                                                    Designation = purchaseLinestab.get(i).getDescription().toString();
                                                    System.out.println("Designation" + Designation);
                                                    designation.setText(Designation.trim());
                                                    delete.setVisibility(View.VISIBLE);

                                                }

                                            }
                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<PurchaseOrders> call, Throwable t) {
                                        System.out.println(t.getMessage());
                                    }
                                });
                            } else {
                                ErrorAlert("Echec", "Article introuvalble dans la liste des rejets");
                                delete.setVisibility(View.INVISIBLE);
                                LinearLayoutDesignation.setVisibility(View.INVISIBLE);
                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Rejet> call, Throwable t) {
                            System.out.println(t.getMessage());
                        }
                    });
                }
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
                    commande.setText(result.getContents().trim());
                    capteurBtnCommande = 0;
                }
                if (capteurBtnArticle == 1) {
                    code.setText(result.getContents().trim());
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

}