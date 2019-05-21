package edu.nju.yummy.dto;

public class CartAndOrderItemDto {
    private int dishid;
    private String dishName;
    private int num;
    private double price;
    private double actual = -1;     //默认-1
    private double sum;

    public CartAndOrderItemDto(int dishid, String dishName, int num, double price) {
        this.dishid = dishid;
        this.dishName = dishName;
        this.num = num;
        this.price = price;
        this.sum = num * price;
    }

    public CartAndOrderItemDto(int dishid, String dishName, int num, double price, double actual, double sum) {
        this.dishid = dishid;
        this.dishName = dishName;
        this.num = num;
        this.price = price;
        this.actual = actual;
        this.sum = sum;
    }

    public int getDishid() {
        return dishid;
    }

    public void setDishid(int dishid) {
        this.dishid = dishid;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double actual) {
        this.actual = actual;
        this.sum = actual * num;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
