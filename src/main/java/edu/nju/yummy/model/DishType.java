package edu.nju.yummy.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DishType {
    private int dishtypeid;
    private int shopid;
    private String type;

    public DishType() {
    }

    public DishType(int shopid, String type) {
        this.shopid = shopid;
        this.type = type;
    }

    @Id
    @Column(name = "dishtypeid")
    public int getDishtypeid() {
        return dishtypeid;
    }

    public void setDishtypeid(int dishtypeid) {
        this.dishtypeid = dishtypeid;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DishType dishtype = (DishType) o;

        if (dishtypeid != dishtype.dishtypeid) return false;
        if (shopid != dishtype.shopid) return false;
        if (type != null ? !type.equals(dishtype.type) : dishtype.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dishtypeid;
        result = 31 * result + shopid;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
