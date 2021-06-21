package com.example.indigogestionstock.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.User;
import com.example.indigogestionstock.R;
import com.example.indigogestionstock.UserManager.UserSessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuGeneralFragment extends Fragment {
    UserSessionManager session;
    ClientDynamicsWebService client;
    Dialog alertDialog;
    TextView message, titleDialogue;
    ImageView imageDialogue;
    public int postUser = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu_general, container, false);

        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.error_message);

        message = alertDialog.findViewById(R.id.messageError);
        imageDialogue = alertDialog.findViewById(R.id.imageDialogue);
        titleDialogue = alertDialog.findViewById(R.id.titleErrorMessage);
        client = new ClientDynamicsWebService();
        session = new UserSessionManager(getContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //get element of sidebare header
        String id = user.get(UserSessionManager.KEY_ID);

        client.getUserByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                postUser = Integer.parseInt(response.body().getPostUser());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        CardView consommatioDeclaration,
                receptionMp,
                rengementpalette,
                PreparationCommnade,
                Stat,
                gestionQualite;


        consommatioDeclaration = (CardView) v.findViewById(R.id.consommatioDeclaration);
        receptionMp = (CardView) v.findViewById(R.id.receptionMp);
        Stat = (CardView) v.findViewById(R.id.stat);

        rengementpalette = (CardView) v.findViewById(R.id.rengementpalette);
        PreparationCommnade = (CardView) v.findViewById(R.id.PreparationCommnade);
        gestionQualite = (CardView) v.findViewById(R.id.gestionQualite);
        //  chargementCamion = (CardView) v.findViewById(R.id.chargementCamion);

        consommatioDeclaration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postUser == 3) {
                    ConsommationDeclarationFragment consommationDeclarationFragment = new ConsommationDeclarationFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, consommationDeclarationFragment).addToBackStack("tag").commit();
                } else {
                    ErrorAlert("Non authorisé", "Vous n'êtes pas autorisé à accéder à cette fonctionnalité");
                    alertDialog.show();
                }


            }
        });
        receptionMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postUser == 1) {
                    ReceptionMpFragment receptionMpFragment = new ReceptionMpFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, receptionMpFragment).addToBackStack("tag").commit();
                } else {
                    ErrorAlert("Non authorisé", "Vous n'êtes pas autorisé à accéder à cette fonctionnalité");
                    alertDialog.show();
                }


            }
        });

        rengementpalette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postUser == 2) {
                    RengementPaletteFragment rengementPaletteFragment = new RengementPaletteFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, rengementPaletteFragment).addToBackStack("tag").commit();
                } else {
                    ErrorAlert("Non authorisé", "Vous n'êtes pas autorisé à accéder à cette fonctionnalité");
                    alertDialog.show();
                }

            }
        });
        PreparationCommnade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postUser == 1) {
                    PreparationCommnadeFragment preparationCommnadeFragment = new PreparationCommnadeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, preparationCommnadeFragment).addToBackStack("tag").commit();
                }else {
                    ErrorAlert("Non authorisé", "Vous n'êtes pas autorisé à accéder à cette fonctionnalité");
                    alertDialog.show();
                }
            }

        });
        gestionQualite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postUser == 3) {
                    GestionQualiteFragment gestionQualiteFragment = new GestionQualiteFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, gestionQualiteFragment).addToBackStack("tag").commit();
                }else {
                    ErrorAlert("Non authorisé", "Vous n'êtes pas autorisé à accéder à cette fonctionnalité");
                    alertDialog.show();
                }

            }
        });

        Stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postUser == 3) {
                    StatFragment statFragment = new StatFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, statFragment).addToBackStack( "tag" ).commit();

                }
                else {
                    ErrorAlert("Non authorisé", "Vous n'êtes pas autorisé à accéder à cette fonctionnalité");
                    alertDialog.show();
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


}