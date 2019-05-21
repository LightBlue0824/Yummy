package edu.nju.yummy.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Dish {
    private int dishid;
    private int shopid;
    private String name;
    private String type;
    private String brief;
    private int num;
    private int islimited;
    private Date starttime;
    private Date endtime;
    private double price;
    private Double actual = -1.0;

    public Dish() {
    }

    public Dish(int shopid, String name, String type, double price, String brief, int num, int islimited) {
        this.shopid = shopid;
        this.name = name;
        this.type = type;
        this.brief = brief;
        this.num = num;
        this.islimited = islimited;
        this.price = price;
    }

    @Id
    @Column(name = "dishid")
    public int getDishid() {
        return dishid;
    }

    public void setDishid(int dishid) {
        this.dishid = dishid;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "brief")
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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
    @Column(name = "islimited")
    public int getIslimited() {
        return islimited;
    }

    public void setIslimited(int islimited) {
        this.islimited = islimited;
    }

    @Basic
    @Column(name = "starttime")
    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @Basic
    @Column(name = "endtime")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (dishid != dish.dishid) return false;
        if (shopid != dish.shopid) return false;
        if (num != dish.num) return false;
        if (islimited != dish.islimited) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (type != null ? !type.equals(dish.type) : dish.type != null) return false;
        if (brief != null ? !brief.equals(dish.brief) : dish.brief != null) return false;
        if (starttime != null ? !starttime.equals(dish.starttime) : dish.starttime != null) return false;
        if (endtime != null ? !endtime.equals(dish.endtime) : dish.endtime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dishid;
        result = 31 * result + shopid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (brief != null ? brief.hashCode() : 0);
        result = 31 * result + num;
        result = 31 * result + islimited;
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (endtime != null ? endtime.hashCode() : 0);
        return result;
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
    @Column(name = "actual")
    public Double getActual() {
        return actual;
    }

    public void setActual(Double actual) {
        this.actual = actual;
    }
}
