package edu.nju.yummy.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class ModifyShopInfo {
    private int id;
    private Timestamp time;
    private int shopid;
    private String name;
    private double x;
    private double y;
    private String type;
    private String brief;
    private int isapproved;

    public ModifyShopInfo() {
    }

    public ModifyShopInfo(int id, Timestamp time, int shopid, String name, double x, double y, String type, String brief) {
        this.id = id;
        this.time = time;
        this.shopid = shopid;
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
        this.brief = brief;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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

    @Basic
    @Column(name = "isapproved")
    public int getIsapproved() {
        return isapproved;
    }

    public void setIsapproved(int isaproved) {
        this.isapproved = isaproved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModifyShopInfo that = (ModifyShopInfo) o;

        if (id != that.id) return false;
        if (shopid != that.shopid) return false;
        if (x != that.x) return false;
        if (y != that.y) return false;
        if (type != that.type) return false;
        if (isapproved != that.isapproved) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (brief != null ? !brief.equals(that.brief) : that.brief != null) return false;

        return true;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, time, shopid, name, x, y, type, brief, isapproved);
    }
}
