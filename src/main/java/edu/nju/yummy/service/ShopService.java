package edu.nju.yummy.service;

import edu.nju.yummy.dto.DishListDto;
import edu.nju.yummy.dto.ShopInfoDto;
import edu.nju.yummy.dto.ShopOrderDto;

import java.util.List;
import java.util.Map;

public interface ShopService {
    String register(String name, String password, double x, double y, String type, String brief);

    String login(String id, String password);

    ShopInfoDto loadShopInfo(String id);

    String modifyShopInfo(String id, String name, double x, double y, String type, String brief);

    DishListDto loadShopDishList(String shopid, String type);

    String modifyDish(int dishid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount);

    String addDish(String shopid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount);

    String addType(String shopid, String typeName);

    int getModifyInfoState(String shopid);

    List<ShopOrderDto> loadShopOrderList(int shopid, String startTime, String endTime, double minPrice, double maxPrice, int userLevel);

    Map<String,List> loadShopStatisticsChartData(int shopid);

    List<String> loadAllDishByStrExceptPackage(int shopid);
}
