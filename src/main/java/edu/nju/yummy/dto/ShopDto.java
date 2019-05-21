package edu.nju.yummy.dto;
/**
 * 传输用shop对象，包含基本信息、距离等
 */
public class ShopDto{
    private int shopid;
    private String name;
    private double x;
    private double y;
    private String type;
    private String brief;
    private int distance = -1;

    public ShopDto() {
    }

    public ShopDto(int shopid, String name, double x, double y, String type, String brief) {
        this.shopid = shopid;
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
        this.brief = brief;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
