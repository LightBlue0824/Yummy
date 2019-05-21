package edu.nju.yummy.dto;

public class ShopDetailDto {
    private ShopDto shopDto;
    private DishListDto dishListDto;

    public ShopDetailDto() {
    }

    public ShopDetailDto(ShopDto shopDto, DishListDto dishListDto) {
        this.shopDto = shopDto;
        this.dishListDto = dishListDto;
    }

    public ShopDto getShopDto() {
        return shopDto;
    }

    public void setShopDto(ShopDto shopDto) {
        this.shopDto = shopDto;
    }

    public DishListDto getDishListDto() {
        return dishListDto;
    }

    public void setDishListDto(DishListDto dishListDto) {
        this.dishListDto = dishListDto;
    }
}
