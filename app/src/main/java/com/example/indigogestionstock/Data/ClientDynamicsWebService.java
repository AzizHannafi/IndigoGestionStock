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
    private static final String BASE_URL = "http:/192.168.1.10:8000/";
    private DynamicsInterface dynamicsInterface;
    private static ClientDynamicsWebService INSTANCE;

    public ClientDynamicsWebService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dynamicsInterface = retrofit.create(DynamicsInterface.class);
    }

    public static ClientDynamicsWebService getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new ClientDynamicsWebService();
        }
        return INSTANCE;
    }

    //*********************************Sales order crud ************************************//
    public Call<List<SalesOrder>> getAllSalesOrder(String locationCode) {
        return dynamicsInterface.getAllSalesOrder(locationCode);
    }

    public Call<SalesOrder> getOneSaleOrder(String id_Order) {
        return dynamicsInterface.getOneSaleOrder(id_Order);
    }

    public Call<List<SalesOrder>> GetAllReleasedSalesOrder() {
        return dynamicsInterface.GetAllReleasedSalesOrder();
    }

    public Call<List<SalesOrder>> GetAllOpenSalesOrder() {
        return dynamicsInterface.GetAllOpenSalesOrder();
    }
    public Call<Void> updateSalesStatus(String idOrder, String status) {
        return dynamicsInterface.updateSalesStatus(idOrder, status);
    }
    //*********************************Sales Line crud ************************************//
    public Call<Void> deleteLine(Key key) {
        return dynamicsInterface.DeleteSlaesLine(key);
    }

    public Call<Void> deletePurchaseOrder(Key key) {
        return dynamicsInterface.DeletePurchaseOrder(key);
    }


    public Call<List<SalesOrder>> getOneReleasedSalesOrder(String id_Order) {
        return dynamicsInterface.getOneReleasedSalesOrder(id_Order);
    }

    public Call<List<SalesOrder>> getOneOpenSalesOrder(String id_Order) {
        return dynamicsInterface.getOneOpenSalesOrder(id_Order);
    }
    //*********************************Purchase order crud ************************************//

    public Call<List<PurchaseOrders>> GetAllReleasedPurchaseOrder() {
        return dynamicsInterface.GetAllReleasedPurchaseOrder();
    }

    public Call<List<PurchaseOrders>> GetAllOpenPurchaseOrder() {
        return dynamicsInterface.GetAllOpenPurchaseOrder();
    }

    public Call<List<PurchaseOrders>> getAllPurchaseOrders(String locationCode) {
        return dynamicsInterface.getAllPurchaseOrders(locationCode);
    }

    public Call<PurchaseOrders> getOnePurchaseOrders(String id_PurchaseOrder) {
        return dynamicsInterface.getOnePurchaseOrders(id_PurchaseOrder);
    }

    public Call<Void> updateStatus(String idPurchaseOrder, String status) {
        return dynamicsInterface.updateStatus(idPurchaseOrder, status);
    }


    public Call<List<PurchaseOrders>> getOneReleasedPurchaseOrder(String id_PurchaseOrder) {
        return dynamicsInterface.getOneReleasedPurchaseOrder(id_PurchaseOrder);
    }

    public Call<List<PurchaseOrders>>getOneOpenPurchaseOrder(String id_PurchaseOrder) {
        return dynamicsInterface.getOneOpenPurchaseOrder(id_PurchaseOrder);
    }
    //*********************************Item  crud ************************************//
    public Call<Item> getOneItem(String idItem) {
        return dynamicsInterface.getOneItem(idItem);
    }

    ;

    public Call<Item> getOneItemWithShelf(String idItem) {
        return dynamicsInterface.GetOneWithShelf(idItem);
    }

    public Call<Void> updateEmplacement(String idItem, String idEmplacement) {
        return dynamicsInterface.updateEmplacement(idItem, idEmplacement);
    }

    public Call<Item> GetBilanPurchaseOneItem(String idItem){
        return dynamicsInterface.GetBilanPurchaseOneItem(idItem);
    }

    public Call<Item> GetBilanSalesOneItem(String idItem){
        return dynamicsInterface.GetBilanSalesOneItem(idItem);
    }

    //**********************************************Login*****************************************//
    public Call<User> login(User user) {
        return dynamicsInterface.login(user);
    }

    public Call<User> getUserByID(String idUser) {
        return dynamicsInterface.getUserByID(idUser);
    }

    //**********************************************Reject crud*****************************************//
    public Call<Void> addToReject(String idCommande, String idItem,String IDUser) {
        return dynamicsInterface.addToReject(idCommande, idItem,IDUser);
    }
    public Call<Void> addtoReception(String idCommande, String idItem,String IDUser) {
        return dynamicsInterface.addToRecption(idCommande, idItem,IDUser);
    }

    public Call<Void> addToDelete(String idCommande, String idItem,String IDUser) {
        return dynamicsInterface.addToDelete(idCommande, idItem,IDUser);
    }

    public Call<Void> addToPreparetion( String IDUser,String idCommande) {
        return dynamicsInterface.addToPreparetion(IDUser, idCommande);
    }

    public Call<Rejet> getOneRejet(Rejet rejet) {
        return dynamicsInterface.getOneRejet(rejet);
    }
}
