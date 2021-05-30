package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.indigogestionstock.Models.PurchaseLine;
import com.example.indigogestionstock.Models.PurchaseOrders;
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


public class ReceptionMpFragment extends Fragment {
    LinearLayout  LinearLayoutDialogue;
    Dialog alertDialog;

    int capteurBtnCommande;
    int capteurBtnArticle;
    EditText code, designation, quantite, commande, fournissuer;
    Button btn, btnvalidate, btnCommande, btnValidateCommande, btnReceptionner, btnSend, btnRejet;
    ImageView btnback,confirme, cancel;
    ClientDynamicsWebService client;
    LinearLayout Linearlayoutdesignation;
    TextView message, titleDialogue;
    ImageView imageDialogue;

    UserSessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reception_mp, container, false);
        session = new UserSessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();
        //get element of sidebare header
        String id = user.get(UserSessionManager.KEY_ID);


        capteurBtnArticle = 0;
        capteurBtnCommande = 0;
        client = new ClientDynamicsWebService();
        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);
        message = alertDialog.findViewById(R.id.messageError);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);
        confirme = alertDialog.findViewById(R.id.Yes);
        cancel = alertDialog.findViewById(R.id.no);
        LinearLayoutDialogue=alertDialog.findViewById(R.id.linearLayoutDialogue);


        btnvalidate = v.findViewById(R.id.btnvalidate);
        code = v.findViewById(R.id.qrt);
        btn = v.findViewById(R.id.qr);
        btnCommande = v.findViewById(R.id.btnCommande);
        designation = v.findViewById(R.id.designation);
        quantite = v.findViewById(R.id.quantité);
        commande = v.findViewById(R.id.commande);
        btnback = (ImageView) v.findViewById(R.id.btnBack);
        Linearlayoutdesignation = v.findViewById(R.id.LinearLayoutDesignation);
        btnValidateCommande = v.findViewById(R.id.btnvalidateCmd);
        fournissuer = v.findViewById(R.id.fournissuer);
        btnReceptionner = v.findViewById(R.id.btnReceptionner);
        btnSend = v.findViewById(R.id.send);
        btnRejet = v.findViewById(R.id.btnRejet);

        titleDialogue.setTextColor(Color.rgb(178, 34, 34));
        message.setTextColor(Color.rgb(178, 34, 34));
        imageDialogue.setBackgroundResource(R.drawable.alert);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idItem = code.getText().toString();
                client.getOneItem(idItem).enqueue(new Callback<Item>() {
                    @Override
                    public void onResponse(Call<Item> call, Response<Item> response) {
                        if (response.body() != null) {
                            if (response.body().getNo().toString().equals("null")) {
                                ErrorAlert("Echec", "Article intouvable");
                                Linearlayoutdesignation.setVisibility(View.INVISIBLE);
                                alertDialog.show();
                            } else {
                                Linearlayoutdesignation.setVisibility(View.VISIBLE);
                                designation.setText(response.body().getDescription().toString());
                            }
                        } else if (code.getText().length() == 0) {
                            ErrorAlert("Echec", "Code a bare est vide ");
                            Linearlayoutdesignation.setVisibility(View.INVISIBLE);
                            alertDialog.show();
                        } else if (quantite.getText().length() == 0) {
                            ErrorAlert("Echec", "Vous devez ajouter la quantité le l'article");
                            alertDialog.show();
                            //resetAlert();
                        }
                    }

                    @Override
                    public void onFailure(Call<Item> call, Throwable t) {

                    }
                });
            }
        });
        btnRejet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((commande.getText().toString().length() == 0) || (code.getText().toString().length() == 0)) {
                    ErrorAlert("Echec", "Vous devez saisir le code a barre de la commande et de l'article");
                    alertDialog.show();
                } else {

                    client.addToReject(commande.getText().toString(), code.getText().toString(),id).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            ErrorAlert("Ajouter a la liste de rejet", "Cette commande a été ajouter a la liste des rejet");
                            alertDialog.show();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

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

        btnvalidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commande.getText().length() == 0) {
                    ErrorAlert("Echec", "Vous devez scanner le code a bare de la commande d'abord");
                    alertDialog.show();
                    btnReceptionner.setVisibility(View.GONE);
                    //resetAlert();
                } else if (quantite.getText().length() == 0) {
                    ErrorAlert("Echec", "Vous devez ajouter la quantité le l'article");
                    alertDialog.show();
                    btnReceptionner.setVisibility(View.GONE);
                    //resetAlert();
                } else {
                    client.getOnePurchaseOrders(commande.getText().toString()).enqueue(new Callback<PurchaseOrders>() {
                        @Override
                        public void onResponse(Call<PurchaseOrders> call, Response<PurchaseOrders> response) {
                            List<PurchaseLine> purchaseLinestab = response.body().getPurchLines();
                            String quantiteTheorique = null;
                            for (int i = 0; i < purchaseLinestab.size(); i++) {
                                if (purchaseLinestab.get(i).getItemNo().equals(code.getText().toString())) {
                                    quantiteTheorique = purchaseLinestab.get(i).getQuantity().toString();
                                }
                            }
                            if ((Integer.parseInt(quantiteTheorique)) == (Integer.parseInt(quantite.getText().toString()))) {
                                ChechedAlert("Article Vérifié", "La quantité a expédié est conforme avec la quantité reçue");
                                alertDialog.show();
                                btnReceptionner.setVisibility(View.VISIBLE);
                                btnRejet.setVisibility(View.INVISIBLE);
                                //resetAlert();
                            } else {
                                ErrorAlert("Echec", "La quantité a expédié n'est pas conforme avec la quantité reçue");
                                alertDialog.show();
                                btnReceptionner.setVisibility(View.GONE);
                                btnRejet.setVisibility(View.VISIBLE);
                                //resetAlert();
                            }
                        }

                        @Override
                        public void onFailure(Call<PurchaseOrders> call, Throwable t) {
                            System.out.println(t.getMessage());
                        }
                    });
                }

            }


        });
        btnValidateCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.getOnePurchaseOrders(commande.getText().toString()).enqueue(new Callback<PurchaseOrders>() {
                    @Override
                    public void onResponse(Call<PurchaseOrders> call, Response<PurchaseOrders> response) {
                        if (response.body() != null) {
                            if (response.body().getNo().toString().equals("null")) {
                                ErrorAlert("Echec", "Commande intouvable");
                                //Linearlayoutdesignation.setVisibility(View.INVISIBLE);
                                fournissuer.setText("");
                                alertDialog.show();
                            } else {
                                //Toast.makeText(getContext(), response.body().getInventory().toString(), Toast.LENGTH_LONG).show();
                                //Linearlayoutdesignation.setVisibility(View.VISIBLE);
                                fournissuer.setText(response.body().getBuy_from_Vendor_Name().toString());
                            }
                        } else if (code.getText().length() == 0) {
                            ErrorAlert("Echec", "Code a bare est vide ");
                            fournissuer.setText("");
                            //Linearlayoutdesignation.setVisibility(View.INVISIBLE);
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PurchaseOrders> call, Throwable t) {

                    }
                });
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
                LinearLayoutDialogue.setVisibility(View.GONE);
                if (commande.getText().length() == 0) {
                    ErrorAlert("Echec", "Vous devez scanner une commande");
                    alertDialog.show();
                } else if (code.getText().length() == 0) {
                    ErrorAlert("Echec", "Vous devez scanner un article");
                    alertDialog.show();
                } else if (quantite.getText().length() == 0) {
                    ErrorAlert("Echec", "Vous devez ajouter la quantité le l'article");
                    alertDialog.show();
                } else {
                    client.getOnePurchaseOrders(commande.getText().toString()).enqueue(new Callback<PurchaseOrders>() {
                        @Override
                        public void onResponse(Call<PurchaseOrders> call, Response<PurchaseOrders> response) {

                            if (response.body().getStatus().toString().equals("Released")) {
                                ChechedAlert("Déjà réceptionner", "Vous posedez déjà cette commande ");
                                alertDialog.show();

                            } else {
                                //ChechedAlert("Réception réussite", "La quantité a expédié est conforme avec quantité reçue");
                                alertDialog.dismiss();
                                client.updateStatus(response.body().getNo().toString(), "1").enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        System.out.println("Status updated");
                                        client.addtoReception(commande.getText().toString(),
                                                              code.getText().toString(),
                                                              id).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                System.out.println("Ligne ajouter ");
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                System.out.println(t.getMessage());
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        System.out.println(t.getMessage());
                                    }
                                });
                                //resetAlert();
                            }
                        }

                        @Override
                        public void onFailure(Call<PurchaseOrders> call, Throwable t) {
                            System.out.println(t.getMessage().toString());
                        }
                    });
                }

            }
        });
        btnReceptionner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorAlert("Receptionner","Vous éte sur vous voulez receptionner cette commande");
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

}