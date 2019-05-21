package model;

import java.util.Date;

public class Orders {
    private int idOrder;
    private int idClient;
    private int idProduct;
    private int quantity;
    private String dateTime;

    public Orders(){

    }

    public Orders(int idClient, int idProduct, int quantity, String dateTime) {
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.dateTime = dateTime;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
