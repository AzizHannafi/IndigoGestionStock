package com.example.indigogestionstock.Fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.indigogestionstock.R;

public class MenuGeneralFragment extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v=inflater.inflate(R.layout.fragment_menu_general, container, false);

         CardView consommatioDeclaration,
                 receptionMp,
                 Transfert,
                 rengementpalette,
                 PreparationCommnade,
                 gestionQualite,
                 chargementCamion;

         consommatioDeclaration=(CardView)v.findViewById(R.id.consommatioDeclaration);
         receptionMp=(CardView)v.findViewById(R.id.receptionMp);
         Transfert=(CardView)v.findViewById(R.id.Transfert);
         rengementpalette=(CardView)v.findViewById(R.id.rengementpalette);
         PreparationCommnade=(CardView)v.findViewById(R.id.PreparationCommnade);
         gestionQualite=(CardView)v.findViewById(R.id.gestionQualite);
         chargementCamion=(CardView)v.findViewById(R.id.chargementCamion);

         consommatioDeclaration.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ConsommationDeclarationFragment consommationDeclarationFragment =new ConsommationDeclarationFragment();
                 getFragmentManager().beginTransaction().replace(R.id.fragment_container, consommationDeclarationFragment).commit();
             }
         });
        receptionMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceptionMpFragment receptionMpFragment =new ReceptionMpFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, receptionMpFragment).commit();
            }
        });
        Transfert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransfertFragment transfertFragment =new TransfertFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, transfertFragment).commit();
            }
        });
        rengementpalette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RengementPaletteFragment rengementPaletteFragment =new RengementPaletteFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, rengementPaletteFragment).commit();
            }
        });
        PreparationCommnade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreparationCommnadeFragment preparationCommnadeFragment =new PreparationCommnadeFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, preparationCommnadeFragment).commit();
            }
        });
        gestionQualite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionQualiteFragment gestionQualiteFragment =new GestionQualiteFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, gestionQualiteFragment).commit();
            }
        });
        chargementCamion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChargementCamionFragment chargementCamionFragment =new ChargementCamionFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, chargementCamionFragment).commit();
            }
        });

         return v;
    }


}