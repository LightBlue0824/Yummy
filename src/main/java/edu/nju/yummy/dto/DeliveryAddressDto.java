package edu.nju.yummy.dto;

public class DeliveryAddressDto {
    private int addressid;
    private String name;
    private String phone;
    private double x;
    private double y;

    public DeliveryAddressDto(int addressid, String name, String phone, double x, double y) {
        this.addressid = addressid;
        this.name = name;
        this.phone = phone;
        this.x = x;
        this.y = y;
    }

    public String getAddressString(){
        return name + " " + phone + "   " + "(" + x + ", " + y + ")";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

}
