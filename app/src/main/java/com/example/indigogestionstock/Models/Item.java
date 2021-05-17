package com.example.indigogestionstock.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("key")
    @Expose
    String Key;

    @SerializedName("no")
    @Expose
    String No;

    @SerializedName("description")
    @Expose
    String Description;

    @SerializedName("inventory")
    @Expose
    String Inventory;

    @SerializedName("base_Unit_of_Measure")
    @Expose
    String Base_Unit_of_Measure;

    public Item() {
        super();
    }

    public Item(String key, String no, String description, String inventory,
                String base_Unit_of_Measure) {
        super();
        Key = key;
        No = no;
        Description = description;
        Inventory = inventory;
        Base_Unit_of_Measure = base_Unit_of_Measure;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getInventory() {
        return Inventory;
    }

    public void setInventory(String inventory) {
        Inventory = inventory;
    }

    public String getBase_Unit_of_Measure() {
        return Base_Unit_of_Measure;
    }

    public void setBase_Unit_of_Measure(String base_Unit_of_Measure) {
        Base_Unit_of_Measure = base_Unit_of_Measure;
    }
}
