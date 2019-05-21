package edu.nju.yummy.dto;

/**
 * 商店基本信息dto
 */
public class ShopInfoDto {
    private String shopId;
    private String name;
    private double x;
    private double y;
    private String type;
    private String brief;
    private ModifyShopInfoDto modifyShopInfoDto;

    public ShopInfoDto(String shopId, String name, double x, double y, String type, String brief) {
        this.shopId = shopId;
        this.name = name;
        this.x = x;
        this.y = y;
        this.type = type;
        this.brief = brief;
    }

    public ShopInfoDto() {
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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

    public ModifyShopInfoDto getModifyShopInfoDto() {
        return modifyShopInfoDto;
    }

    public void setModifyShopInfoDto(ModifyShopInfoDto modifyShopInfoDto) {
        this.modifyShopInfoDto = modifyShopInfoDto;
    }
}
