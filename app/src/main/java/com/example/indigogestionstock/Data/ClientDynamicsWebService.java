package com.example.indigogestionstock.Data;

import com.example.indigogestionstock.Models.Item;
import com.example.indigogestionstock.Models.Key;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.Models.SalesOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class ClientDynamicsWebService {
    private static final String BASE_URL = "http:/192.168.1.9:8000/";
    private DynamicsInterface dynamicsInterface;
    private static ClientDynamicsWebService INSTANCE;

    public ClientDynamicsWebService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dynamicsInterface=retrofit.create(DynamicsInterface.class);
    }

    public static  ClientDynamicsWebService getINSTANCE(){
        if (null == INSTANCE) {
            INSTANCE = new ClientDynamicsWebService();
        }
        return INSTANCE;
    }
    //*********************************Sales order crud ************************************//
    public Call<List<SalesOrder>> getAllSalesOrder() {
        return dynamicsInterface.getAllSalesOrder();
    }

    public Call<SalesOrder> getOneSaleOrder(String id_Order){
        return dynamicsInterface.getOneSaleOrder(id_Order);
    }

    public Call<SalesOrder> GetAllReleasedOrder(){
        return dynamicsInterface.GetAllReleasedOrder();
    }
    public Call<List<SalesOrder>> GetAllOpenOrder(){
        return dynamicsInterface.GetAllOpenOrder();
    }
    //*********************************Sales Line crud ************************************//
    public Call<Void> deleteLine(Key key){
        return dynamicsInterface.DeleteSlaesLine(key);
    }

    //*********************************Purchase order crud ************************************//

    public Call<List<PurchaseOrders>> getAllPurchaseOrders(){
        return dynamicsInterface.getAllPurchaseOrders();
    }

    public Call<PurchaseOrders> getOnePurchaseOrders(String id_PurchaseOrder){
        return dynamicsInterface.getOnePurchaseOrders(id_PurchaseOrder);
    }

    //*********************************Item  crud ************************************//
    public  Call<Item> getOneItem(String idItem){
        return  dynamicsInterface.getOneItem(idItem);
    };
}
