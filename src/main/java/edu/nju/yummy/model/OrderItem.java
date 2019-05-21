package edu.nju.yummy.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderItem {
    private int itemid;
    private int orderid;
    private int dishid;
    private String dishname;
    private int num;
    private double price;
    private double sum;
    private double actual = -1;

    public OrderItem() {
    }

    public OrderItem(int itemid, int orderid, int dishid, String dishname, int num, double price, double actual, double sum) {
        this.itemid = itemid;
        this.orderid = orderid;
        this.dishid = dishid;
        this.dishname = dishname;
        this.num = num;
        this.price = price;
        this.actual = actual;
        this.sum = sum;
    }

    @Id
    @Column(name = "itemid")
    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    @Basic
    @Column(name = "orderid")
    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    @Basic
    @Column(name = "dishid")
    public int getDishid() {
        return dishid;
    }

    public void setDishid(int dishid) {
        this.dishid = dishid;
    }

    @Basic
    @Column(name = "dishname")
    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    @Basic
    @Column(name = "num")
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "sum")
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        if (itemid != orderItem.itemid) return false;
        if (orderid != orderItem.orderid) return false;
        if (dishid != orderItem.dishid) return false;
        if (num != orderItem.num) return false;
        if (Double.compare(orderItem.price, price) != 0) return false;
        if (Double.compare(orderItem.sum, sum) != 0) return false;
        if (dishname != null ? !dishname.equals(orderItem.dishname) : orderItem.dishname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = itemid;
        result = 31 * result + orderid;
        result = 31 * result + dishid;
        result = 31 * result + (dishname != null ? dishname.hashCode() : 0);
        result = 31 * result + num;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(sum);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Basic
    @Column(name = "actual")
    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }
}
