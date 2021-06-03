package com.example.indigogestionstock.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalesOrder {
    @SerializedName("no")
    @Expose
    private String 	       No;

    @SerializedName("sell_to_Customer_No")
    @Expose
    private String         Sell_to_Customer_No;

    @SerializedName("sell_to_Customer_Name")
    @Expose
    private String         Sell_to_Customer_Name;

    @SerializedName("posting_Description")
    @Expose
    private String         Posting_Description;

    @SerializedName("sell_to_Address")
    @Expose
    private String         Sell_to_Address;

    @SerializedName("sell_to_City")
    @Expose
    private String         Sell_to_City;

    @SerializedName("sell_to_County")
    @Expose
    private String         Sell_to_County;

    @SerializedName("order_Date")
    @Expose
    private String         Order_Date;


    @SerializedName("due_Date")
    @Expose
    private String         Due_Date;

    @SerializedName("prepayment_Due_Date")
    @Expose
    private String         Prepayment_Due_Date;

    @SerializedName("prepmt_Pmt_Discount_Date")
    @Expose
    private String         Prepmt_Pmt_Discount_Date;

    @SerializedName("location_Code")
    @Expose
    private String         Location_Code;

    @SerializedName("status")
    @Expose
    private String         Status;

    @SerializedName("salesLine")
    @Expose
    private List<SalesLines> salesLines;



    public SalesOrder() {
    }

    public SalesOrder(String no, String sell_to_Customer_No, String sell_to_Customer_Name, String posting_Description, String sell_to_Address, String sell_to_City, String sell_to_County, String order_Date, String due_Date, String prepayment_Due_Date, String prepmt_Pmt_Discount_Date, String location_Code, String status, List<SalesLines> salesLines) {
        No = no;
        Sell_to_Customer_No = sell_to_Customer_No;
        Sell_to_Customer_Name = sell_to_Customer_Name;
        Posting_Description = posting_Description;
        Sell_to_Address = sell_to_Address;
        Sell_to_City = sell_to_City;
        Sell_to_County = sell_to_County;
        Order_Date = order_Date;
        Due_Date = due_Date;
        Prepayment_Due_Date = prepayment_Due_Date;
        Prepmt_Pmt_Discount_Date = prepmt_Pmt_Discount_Date;
        Location_Code = location_Code;
        Status = status;
        this.salesLines = salesLines;
    }

    public SalesOrder(String no, String sell_to_Customer_No, String sell_to_Customer_Name, String posting_Description, String sell_to_Address, String sell_to_City, String sell_to_County, String order_Date, String due_Date, String prepayment_Due_Date, String prepmt_Pmt_Discount_Date, String location_Code, List<SalesLines> salesLines) {
        No = no;
        Sell_to_Customer_No = sell_to_Customer_No;
        Sell_to_Customer_Name = sell_to_Customer_Name;
        Posting_Description = posting_Description;
        Sell_to_Address = sell_to_Address;
        Sell_to_City = sell_to_City;
        Sell_to_County = sell_to_County;
        Order_Date = order_Date;
        Due_Date = due_Date;
        Prepayment_Due_Date = prepayment_Due_Date;
        Prepmt_Pmt_Discount_Date = prepmt_Pmt_Discount_Date;
        Location_Code = location_Code;
        this.salesLines = salesLines;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public String getSell_to_Customer_No() {
        return Sell_to_Customer_No;
    }

    public void setSell_to_Customer_No(String sell_to_Customer_No) {
        Sell_to_Customer_No = sell_to_Customer_No;
    }

    public String getSell_to_Customer_Name() {
        return Sell_to_Customer_Name;
    }

    public void setSell_to_Customer_Name(String sell_to_Customer_Name) {
        Sell_to_Customer_Name = sell_to_Customer_Name;
    }

    public String getPosting_Description() {
        return Posting_Description;
    }

    public void setPosting_Description(String posting_Description) {
        Posting_Description = posting_Description;
    }

    public String getSell_to_Address() {
        return Sell_to_Address;
    }

    public void setSell_to_Address(String sell_to_Address) {
        Sell_to_Address = sell_to_Address;
    }

    public String getSell_to_City() {
        return Sell_to_City;
    }

    public void setSell_to_City(String sell_to_City) {
        Sell_to_City = sell_to_City;
    }

    public String getSell_to_County() {
        return Sell_to_County;
    }

    public void setSell_to_County(String sell_to_County) {
        Sell_to_County = sell_to_County;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getDue_Date() {
        return Due_Date;
    }

    public void setDue_Date(String due_Date) {
        Due_Date = due_Date;
    }

    public String getPrepayment_Due_Date() {
        return Prepayment_Due_Date;
    }

    public void setPrepayment_Due_Date(String prepayment_Due_Date) {
        Prepayment_Due_Date = prepayment_Due_Date;
    }

    public String getPrepmt_Pmt_Discount_Date() {
        return Prepmt_Pmt_Discount_Date;
    }

    public void setPrepmt_Pmt_Discount_Date(String prepmt_Pmt_Discount_Date) {
        Prepmt_Pmt_Discount_Date = prepmt_Pmt_Discount_Date;
    }

    public String getLocation_Code() {
        return Location_Code;
    }

    public void setLocation_Code(String location_Code) {
        Location_Code = location_Code;
    }

    public List<SalesLines> getSalesLines() {
        return salesLines;
    }

    public void setSalesLines(List<SalesLines> salesLines) {
        this.salesLines = salesLines;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
