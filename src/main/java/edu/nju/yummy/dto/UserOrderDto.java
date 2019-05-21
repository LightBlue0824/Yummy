package edu.nju.yummy.dto;

public class UserOrderDto {
    private int orderid;
    private int shopid;
    private String shopName;
    private String orderState;
    private String orderTime;
    private String brief;
    private int itemNum;
    private double actual;
    private int deliveryTime = -1;

    public UserOrderDto() {
    }

    public UserOrderDto(int orderid, int shopid, String shopName, String orderState, String orderTime, String brief, int itemNum, double actual) {
        this.orderid = orderid;
        this.shopid = shopid;
        this.shopName = shopName;
        this.orderState = orderState;
        this.orderTime = orderTime;
        this.brief = brief;
        this.itemNum = itemNum;
        this.actual = actual;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
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
