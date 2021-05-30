package com.example.indigogestionstock.Data;

import com.example.indigogestionstock.Models.Item;
import com.example.indigogestionstock.Models.Key;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.Models.Rejet;
import com.example.indigogestionstock.Models.SalesOrder;
import com.example.indigogestionstock.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DynamicsInterface {
    //********************************************Sales Order Crud***************************************//

    //Get  all sales order methode
    @GET("SalesOrder/GetAll")
    Call<List<SalesOrder>> getAllSalesOrder();

    //Get  a sales order by given id
    @GET("SalesOrder/GetOne/{id_saleOrder}")
    Call<SalesOrder> getOneSaleOrder(@Path(value = "id_saleOrder", encoded = true) String id_saleOrder);

    //Get  all sales Released Order methode
    @GET("SalesOrder/GetAllReleasedOrder")
    Call<SalesOrder>GetAllReleasedOrder();

    //Get  all sales Open Order methode
    @GET("SalesOrder/GetAllOpenOrder")
    Call<List<SalesOrder>>GetAllOpenOrder();

    //**********************************************Sales lines Crud***********************************//

    @HTTP(method = "DELETE",path = "SalesLine/DeleteSlaesLine",hasBody = true)
    Call<Void> DeleteSlaesLine(@Body Key key);

    @HTTP(method = "DELETE",path = "PurchaseOrders/DeletePurchaseOrders",hasBody = true)
    Call<Void> DeletePurchaseOrder(@Body Key key );

    //**********************************************Purchase Order Crud***********************************//

    //Get  PurchaseOrders  methode
    @GET("PurchaseOrders/GetAll")
    Call<List<PurchaseOrders>> getAllPurchaseOrders();

    //Get  one Purchase order by given id
    @GET("PurchaseOrders/GetOne/{id_PurchaseOrder}")
    Call<PurchaseOrders> getOnePurchaseOrders(@Path(value = "id_PurchaseOrder", encoded = true) String id_PurchaseOrder);

    @PUT("PurchaseOrders/UpdateStatus/{idPurchaseOrder}/{status}")
    Call<Void> updateStatus(@Path(value = "idPurchaseOrder", encoded = true)String idPurchaseOrder,@Path(value = "status", encoded = true) String status);
    //**********************************************Items Crud***********************************//
    @GET("Items/GetOne/{idItem}")
    Call<Item> getOneItem(@Path(value = "idItem", encoded = true) String idItem);

    @POST("Items/GetOneWithShelf/{idItem}")
    Call<Item> GetOneWithShelf(@Path(value = "idItem", encoded = true) String idItem);

    @PUT("Items/GetBilanPurchaseOneItem/{idItem}/{idEmplacement}")
    Call<Void> updateEmplacement(@Path(value = "idItem", encoded = true) String idItem,@Path(value = "idEmplacement", encoded = true) String idEmplacement);

    @GET("Items/GetBilanPurchaseOneItem/{idItem}")
    Call<Item> GetBilanPurchaseOneItem(@Path(value = "idItem", encoded = true) String idItem);

    @GET("Items/GetBilanSalesOneItem/{idItem}")
    Call<Item> GetBilanSalesOneItem(@Path(value = "idItem", encoded = true) String idItem);

    //**********************************************Login*****************************************//
    @POST("Users/Login")
    Call<User> login(@Body User user);

    @POST("Users/GetUserByID/{idUser}")
    Call<User> getUserByID(@Path(value = "idUser", encoded = true) String idUser);
    //**********************************************Reject crud*****************************************//
    @POST("Rejet/add/{idCommande}/{idItem}/{IDUser}")
    Call<Void> addToReject(@Path(value = "idCommande", encoded = true) String idCommande,
                           @Path(value = "idItem", encoded = true) String idItem,
                           @Path(value = "IDUser", encoded = true) String IDUser);

    @POST("Reception/add/{idCommande}/{idItem}/{IDUser}")
    Call<Void> addToRecption(@Path(value = "idCommande", encoded = true) String idCommande,
                           @Path(value = "idItem", encoded = true) String idItem,
                           @Path(value = "IDUser", encoded = true) String IDUser);

    @POST("Supprimer/add/{idCommande}/{idItem}/{IDUser}")
    Call<Void> addToDelete(@Path(value = "idCommande", encoded = true) String idCommande,
                             @Path(value = "idItem", encoded = true) String idItem,
                             @Path(value = "IDUser", encoded = true) String IDUser);

    @POST("Rejet/GetOne")
    Call<Rejet> getOneRejet(@Body Rejet rejet);
}
