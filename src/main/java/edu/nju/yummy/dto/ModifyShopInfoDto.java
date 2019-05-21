package edu.nju.yummy.dto;

public class ModifyShopInfoDto {
    private int modifyId;
    private String shopid;
    private String time;
    private String name;
    private double x;
    private double y;
    private String type;
    private String brief;
    private int isApproved;

    public ModifyShopInfoDto() {
    }

    public ModifyShopInfoDto(int modifyId, String shopid, String time, String name, double x, double y, String type, String brief, int isApproved) {
        this.modifyId = modifyId;
        this.shopid = shopid;
        this.time = time;
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
        this.brief = brief;
        this.isApproved = isApproved;
    }

    public int getModifyId() {
        return modifyId;
    }

    public void setModifyId(int modifyId) {
        this.modifyId = modifyId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
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

    public int getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(int isApproved) {
        this.isApproved = isApproved;
    }
}
