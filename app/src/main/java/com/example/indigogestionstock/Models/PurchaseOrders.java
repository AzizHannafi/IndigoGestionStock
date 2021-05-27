package com.example.indigogestionstock.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PurchaseOrders {
    @SerializedName("key")
    @Expose
    String Key;

    @SerializedName("no")
    @Expose
    String      No;

    @SerializedName("buy_from_Vendor_No")
    @Expose
    String      Buy_from_Vendor_No;

    @SerializedName("buy_from_Vendor_Name")
    @Expose
    String      Buy_from_Vendor_Name;

    @SerializedName("posting_Description")
    @Expose
    String       Posting_Description;

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("purchLines")
    @Expose
    List<PurchaseLine> PurchLines;

    public PurchaseOrders() {
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getBuy_from_Vendor_No() {
        return Buy_from_Vendor_No;
    }

    public void setBuy_from_Vendor_No(String buy_from_Vendor_No) {
        Buy_from_Vendor_No = buy_from_Vendor_No;
    }

    public String getBuy_from_Vendor_Name() {
        return Buy_from_Vendor_Name;
    }

    public void setBuy_from_Vendor_Name(String buy_from_Vendor_Name) {
        Buy_from_Vendor_Name = buy_from_Vendor_Name;
    }

    public String getPosting_Description() {
        return Posting_Description;
    }

    public void setPosting_Description(String posting_Description) {
        Posting_Description = posting_Description;
    }

    public List<PurchaseLine> getPurchLines() {
        return PurchLines;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPurchLines(List<PurchaseLine> purchLines) {
        PurchLines = purchLines;
    }

    public PurchaseOrders(String key, String no, String buy_from_Vendor_No, String buy_from_Vendor_Name, String posting_Description, List<PurchaseLine> purchLines) {
        Key = key;
        No = no;
        Buy_from_Vendor_No = buy_from_Vendor_No;
        Buy_from_Vendor_Name = buy_from_Vendor_Name;
        Posting_Description = posting_Description;
        PurchLines = purchLines;
    }
}
