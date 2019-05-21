package edu.nju.yummy.dto;

import java.util.List;

public class ShopListDto {
    private String typeToShow;      //要显示的商店类型
    private List<ShopDto> shopDtoList;

    public ShopListDto() {
    }

    public ShopListDto(String typeToShow, List<ShopDto> shopDtoList) {
        this.typeToShow = typeToShow;
        this.shopDtoList = shopDtoList;
    }

    public String getTypeToShow() {
        return typeToShow;
    }

    public void setTypeToShow(String typeToShow) {
        this.typeToShow = typeToShow;
    }

    public List<ShopDto> getShopDtoList() {
        return shopDtoList;
    }

    public void setShopDtoList(List<ShopDto> shopDtoList) {
        this.shopDtoList = shopDtoList;
    }
}
