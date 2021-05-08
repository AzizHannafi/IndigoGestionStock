package com.example.indigogestionstock.Data;

import com.example.indigogestionstock.Models.Key;
import com.example.indigogestionstock.Models.PurchaseOrders;
import com.example.indigogestionstock.Models.SalesOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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



    //**********************************************Purchase Order Crud***********************************//

    //Get  PurchaseOrders  methode
    @GET("PurchaseOrders/GetAll")
    Call<List<PurchaseOrders>> getAllPurchaseOrders();

    //Get  one Purchase order by given id
    @GET("PurchaseOrders/GetOne/{id_PurchaseOrder}")
    Call<PurchaseOrders> getOnePurchaseOrders(@Path(value = "id_PurchaseOrder", encoded = true) String id_PurchaseOrder);
}
