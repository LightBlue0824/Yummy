package edu.nju.yummy.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDto {
    private int shopid;
    private String shopName;
    private List<CartAndOrderItemDto> cartItemDtoList = new ArrayList<>();
    private double total = 0;           //实际总价
    private double actual = 0;          //最终支付价
    private List<DeliveryAddressDto> addressDtoList;
    private int selectedAddressId;
    private int distance = -1;
    private int deliveryTime = -1;

    public CartDto(int shopid, String shopName) {
        this.shopid = shopid;
        this.shopName = shopName;
    }

    /**
     * 添加一个cartItemDto
     * @param item
     */
    public void addCartItemDto(CartAndOrderItemDto item){
        cartItemDtoList.add(item);
        total += item.getSum();
        actual += item.getSum();
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<CartAndOrderItemDto> getCartItemDtoList() {
        return cartItemDtoList;
    }

    public void setCartItemDtoList(List<CartAndOrderItemDto> cartItemDtoList) {
        this.cartItemDtoList = cartItemDtoList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
    }

    public List<DeliveryAddressDto> getAddressDtoList() {
        return addressDtoList;
    }

    public void setAddressDtoList(List<DeliveryAddressDto> addressDtoList) {
        this.addressDtoList = addressDtoList;
    }

    public int getSelectedAddressId() {
        return selectedAddressId;
    }

    public void setSelectedAddressId(int selectedAddressId) {
        this.selectedAddressId = selectedAddressId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
