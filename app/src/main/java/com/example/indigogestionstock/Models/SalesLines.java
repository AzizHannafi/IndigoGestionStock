package com.example.indigogestionstock.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesLines {
    @SerializedName("key")
    @Expose
    private String       Key;

    private String       Type ;

    @SerializedName("no")
    @Expose
    private String       No;
    private String       Document_No;

    @SerializedName("line_No")
    @Expose
    private String       Line_No;

    @SerializedName("quantity")
    @Expose
    private String       Quantity;

    private String       Unit_of_Measure_Code;
    private String       Unit_of_Measure;
    private String       Quantity_Shipped;
    private String       Qty_to_Invoice;


    public SalesLines() {
    }
    public SalesLines(String key, String type, String no, String document_No, String line_No, String quantity, String unit_of_Measure_Code, String unit_of_Measure, String quantity_Shipped, String qty_to_Invoice) {
        Key = key;
        Type = type;
        No = no;
        Document_No = document_No;
        Line_No = line_No;
        Quantity = quantity;
        Unit_of_Measure_Code = unit_of_Measure_Code;
        Unit_of_Measure = unit_of_Measure;
        Quantity_Shipped = quantity_Shipped;
        Qty_to_Invoice = qty_to_Invoice;
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

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
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

    public String getQuantity_Shipped() {
        return Quantity_Shipped;
    }

    public void setQuantity_Shipped(String quantity_Shipped) {
        Quantity_Shipped = quantity_Shipped;
    }

    public String getQty_to_Invoice() {
        return Qty_to_Invoice;
    }

    public void setQty_to_Invoice(String qty_to_Invoice) {
        Qty_to_Invoice = qty_to_Invoice;
    }
}
