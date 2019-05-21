package edu.nju.yummy.dto;

import edu.nju.yummy.model.Dish;

import java.util.List;

public class DishListDto {
    private List<String> typeList;
    private List<Dish> dishList;
    private String typeToShow;

    public DishListDto() {
    }

    public DishListDto(List<String> typeList, List<Dish> dishList, String typeToShow) {
        this.typeList = typeList;
        this.dishList = dishList;
        this.typeToShow = typeToShow;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public String getTypeToShow() {
        return typeToShow;
    }

    public void setTypeToShow(String typeToShow) {
        this.typeToShow = typeToShow;
    }
}
