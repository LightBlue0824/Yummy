package edu.nju.yummy.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDto {
    private int orderid;
    private String orderTime;
    private String email;
    private int shopid;
    private String shopName;
    private List<CartAndOrderItemDto> orderItemDtoList = new ArrayList<>();
    private double total;
    private double actual;
    private String deliveryAddress;
    private String state;
    private String payTime;
    private boolean hasPayPassword;
    private int deliveryTime;
    private String finishTime;

    public OrderDetailDto() {
    }

    public OrderDetailDto(int orderid, String orderTime, String email, int shopid, String shopName, double total, double actual, String deliveryAddress, String state) {
        this.orderid = orderid;
        this.orderTime = orderTime;
        this.email = email;
        this.shopid = shopid;
        this.shopName = shopName;
        this.total = total;
        this.actual = actual;
        this.deliveryAddress = deliveryAddress;
        this.state = state;
    }

    public void addOrderItem(CartAndOrderItemDto orderItemDto){
        this.orderItemDtoList.add(orderItemDto);
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

    public List<CartAndOrderItemDto> getOrderItemDtoList() {
        return orderItemDtoList;
    }

    public void setOrderItemDtoList(List<CartAndOrderItemDto> orderItemDtoList) {
        this.orderItemDtoList = orderItemDtoList;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public boolean isHasPayPassword() {
        return hasPayPassword;
    }

    public void setHasPayPassword(boolean hasPayPassword) {
        this.hasPayPassword = hasPayPassword;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }
}
