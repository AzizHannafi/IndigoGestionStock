package com.example.indigogestionstock.Data;

import com.example.indigogestionstock.Models.Item;
import com.example.indigogestionstock.Models.Key;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.Models.Rejet;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class ClientDynamicsWebService {
    private static final String BASE_URL = "http:/192.168.1.7:8000/";
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

    public Call<Void> deletePurchaseOrder(Key key){return  dynamicsInterface.DeletePurchaseOrder(key);}
    //*********************************Purchase order crud ************************************//

    public Call<List<PurchaseOrders>> getAllPurchaseOrders(){
        return dynamicsInterface.getAllPurchaseOrders();
    }

    public Call<PurchaseOrders> getOnePurchaseOrders(String id_PurchaseOrder){
        return dynamicsInterface.getOnePurchaseOrders(id_PurchaseOrder);
    }

    public  Call<Void>updateStatus(String idPurchaseOrder, String status){
        return dynamicsInterface.updateStatus(idPurchaseOrder,status);
    }

    //*********************************Item  crud ************************************//
    public  Call<Item> getOneItem(String idItem){
        return  dynamicsInterface.getOneItem(idItem);
    };


    //**********************************************Login*****************************************//
    public Call<User>login(User user){return dynamicsInterface.login(user);}

    public  Call<User>getUserByID(String idUser){return dynamicsInterface.getUserByID(idUser);}

    //**********************************************Reject crud*****************************************//
    public  Call<Void> addToReject(String idCommande,String idItem){
        return dynamicsInterface.addToReject(idCommande,idItem);
    }

    public Call<Rejet> getOneRejet(Rejet rejet){
        return  dynamicsInterface.getOneRejet(rejet);
    }
}
