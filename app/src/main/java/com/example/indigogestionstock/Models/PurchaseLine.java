package com.example.indigogestionstock.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseLine {
    @SerializedName("key")
    @Expose
    String       Key;

    @SerializedName("type")
    @Expose
    String       Type;

    @SerializedName("unit_of_Measure_Code")
    @Expose
    String       Unit_of_Measure_Code;

    @SerializedName("unit_of_Measure")
    @Expose
    String       Unit_of_Measure;

    @SerializedName("document_No")
    @Expose
    String       Document_No;

    @SerializedName("line_No")
    @Expose
    String       Line_No;

    public PurchaseLine() {
    }

    public PurchaseLine(String key, String type, String unit_of_Measure_Code, String unit_of_Measure, String document_No, String line_No) {
        Key = key;
        Type = type;
        Unit_of_Measure_Code = unit_of_Measure_Code;
        Unit_of_Measure = unit_of_Measure;
        Document_No = document_No;
        Line_No = line_No;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUnit_of_Measure_Code() {
        return Unit_of_Measure_Code;
    }

    public void setUnit_of_Measure_Code(String unit_of_Measure_Code) {
        Unit_of_Measure_Code = unit_of_Measure_Code;
    }

    public String getUnit_of_Measure() {
        return Unit_of_Measure;
    }

    public void setUnit_of_Measure(String unit_of_Measure) {
        Unit_of_Measure = unit_of_Measure;
    }

    public String getDocument_No() {
        return Document_No;
    }

    public void setDocument_No(String document_No) {
        Document_No = document_No;
    }

    public String getLine_No() {
        return Line_No;
    }

    public void setLine_No(String line_No) {
        Line_No = line_No;
    }
}
