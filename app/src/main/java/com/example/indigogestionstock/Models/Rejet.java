package com.example.indigogestionstock.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rejet {
    @SerializedName("idCommande")
    @Expose
    public String idCommande;

    @SerializedName("iditem")
    @Expose
    public String IDItem;


    public Rejet() {
        super();
    }

    public Rejet(String idCommande,String IDItem) {
        super();
        this.idCommande = idCommande;
        this.IDItem= IDItem;
    }

    public String getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    public String getIDItem() {
        return IDItem;
    }

    public void setIDItem(String iDItem) {
        IDItem = iDItem;
    }
}
