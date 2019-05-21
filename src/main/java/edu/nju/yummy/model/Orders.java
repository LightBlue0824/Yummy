package edu.nju.yummy.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Orders {
    private int orderid;
    private Timestamp ordertime;
    private String email;
    private int shopid;
    private String shopName;
    private double total;
    private double actual;
    private String deliveryaddress;
    private String state = "未支付";
    private Timestamp paytime;
    private int deliverytime;
    private Timestamp finishtime;

    public Orders() {
    }

    public Orders(int orderid, Timestamp ordertime, String email, int shopid, String shopName, double total, double actual, String deliveryaddress, int deliverytime) {
        this.orderid = orderid;
        this.ordertime = ordertime;
        this.email = email;
        this.shopid = shopid;
        this.shopName = shopName;
        this.total = total;
        this.actual = actual;
        this.deliveryaddress = deliveryaddress;
        this.deliverytime = deliverytime;
    }

    @Id
    @Column(name = "orderid")
    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    @Basic
    @Column(name = "ordertime")
    public Timestamp getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Timestamp ordertime) {
        this.ordertime = ordertime;
    }

    @Basic
    @Column(name = "shopid")
    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    @Basic
    @Column(name = "shopname")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Basic
    @Column(name = "total")
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Basic
    @Column(name = "actual")
    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    @Basic
    @Column(name = "deliveryaddress")
    public String getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(String deliveryaddress) {
        this.deliveryaddress = deliveryaddress;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "paytime")
    public Timestamp getPaytime() {
        return paytime;
    }

    public void setPaytime(Timestamp paytime) {
        this.paytime = paytime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (orderid != orders.orderid) return false;
        if (shopid != orders.shopid) return false;
        if (Double.compare(orders.total, total) != 0) return false;
        if (Double.compare(orders.actual, actual) != 0) return false;
        if (ordertime != null ? !ordertime.equals(orders.ordertime) : orders.ordertime != null) return false;
        if (shopName != null ? !shopName.equals(orders.shopName) : orders.shopName != null) return false;
        if (deliveryaddress != null ? !deliveryaddress.equals(orders.deliveryaddress) : orders.deliveryaddress != null)
            return false;
        if (state != null ? !state.equals(orders.state) : orders.state != null) return false;
        if (paytime != null ? !paytime.equals(orders.paytime) : orders.paytime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = orderid;
        result = 31 * result + (ordertime != null ? ordertime.hashCode() : 0);
        result = 31 * result + shopid;
        result = 31 * result + (shopName != null ? shopName.hashCode() : 0);
        temp = Double.doubleToLongBits(total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(actual);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (deliveryaddress != null ? deliveryaddress.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (paytime != null ? paytime.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "deliverytime")
    public int getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(int deliverytime) {
        this.deliverytime = deliverytime;
    }

    @Basic
    @Column(name = "finishtime")
    public Timestamp getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Timestamp finishtime) {
        this.finishtime = finishtime;
    }
}
