package edu.nju.yummy.data;

import edu.nju.yummy.dto.DishListDto;
import edu.nju.yummy.dto.ModifyShopInfoDto;
import edu.nju.yummy.dto.ShopInfoDto;
import edu.nju.yummy.dto.ShopListDto;
import edu.nju.yummy.model.Dish;
import edu.nju.yummy.model.ModifyShopInfo;
import edu.nju.yummy.model.Shop;

import java.util.List;

public interface ShopDao {
    Shop getShop(int id);

    void updateShop(Shop shop);

    String register(String name, String password, double x, double y, String type, String brief);

    String login(String id, String password);

    ShopInfoDto loadShopInfo(String id);

    String modifyShopInfo(String id, String name, double x, double y, String type, String brief);

    ModifyShopInfo getModifyShopInfo(int modifyShopInfoId);

    DishListDto loadShopDishList(String shopid, String type);

    String modifyDish(int dishid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount);

    String addDish(String shopid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount);

    String addType(String shopid, String typeName);


    /**
     * 加载对应类型的商店列表
     * @param shopType
     * @return
     */
    ShopListDto loadShopList(String shopType);

    /**
     * 获取dish对象
     * @param dishid
     * @return
     */
    Dish getDish(int dishid);

    int getModifyInfoState(String shopid);

    /**
     * 加载修改商户信息列表
     * @return
     */
    List<ModifyShopInfoDto> loadModifyShopInfoList();

    List<String> loadAllDishByStrExceptPackage(int shopid);
}
