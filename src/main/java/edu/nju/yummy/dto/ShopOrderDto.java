package edu.nju.yummy.dto;

import java.util.List;

public class ShopOrderDto {
    private int orderid;
    private String orderTime;
    private String email;
    private String deliveryAddress;
    private String orderState;
    private List<CartAndOrderItemDto> orderItemList;
    private double actual;
    private int deliveryTime = -1;

    public ShopOrderDto(int orderid, String orderTime, String email, String deliveryAddress, String orderState, List<CartAndOrderItemDto> orderItemList, double actual, int deliveryTime) {
        this.orderid = orderid;
        this.orderTime = orderTime;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
        this.orderState = orderState;
        this.orderItemList = orderItemList;
        this.actual = actual;
        this.deliveryTime = deliveryTime;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public List<CartAndOrderItemDto> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<CartAndOrderItemDto> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
