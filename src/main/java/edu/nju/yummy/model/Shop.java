package edu.nju.yummy.model;

import javax.persistence.*;

@Entity
@Table(name = "shop", schema = "yummy", catalog = "")
public class Shop {
    private int shopid;
    private String password;
    private String name;
    private double x;
    private double y;
    private String type;
    private String brief;
    private double income;

    @Id
    @Column(name = "shopid")
    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "x")
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (shopid != shop.shopid) return false;
        if (x != shop.x) return false;
        if (y != shop.y) return false;
        if (password != null ? !password.equals(shop.password) : shop.password != null) return false;
        if (name != null ? !name.equals(shop.name) : shop.name != null) return false;
        if (type != null ? !type.equals(shop.type) : shop.type != null) return false;
        if (brief != null ? !brief.equals(shop.brief) : shop.brief != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = shopid;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (brief != null ? brief.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "income")
    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
