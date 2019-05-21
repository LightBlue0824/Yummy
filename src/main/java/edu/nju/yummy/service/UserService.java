package edu.nju.yummy.service;

import edu.nju.yummy.dto.*;
import edu.nju.yummy.model.Cart;

import java.util.List;
import java.util.Map;

public interface UserService {
    String register(String email, String password);

    String login(String email, String password);

    String verify(String code);

    String destroy(String email);

    /**
     * 加载用户个人信息
     * @param email
     * @return
     */
    UserInfoDto loadUserInfo(String email);

    /**
     * 修改用户基本信息
     * @param email
     * @param name
     * @param phone
     * @return
     */
    String modifyUserInfo(String email, String name, String phone);

    /**
     * 加载用户送餐地址
     * @param email
     * @return
     */
    List<DeliveryAddressDto> loadUserDeliveryAddress(String email);

    /**
     * 新增送餐地址
     * @param email
     * @param name
     * @param phone
     * @param x
     * @param y
     * @return
     */
    boolean addUserDeliveryAddress(String email, String name, String phone, double x, double y);

    /**
     * 修改支付密码
     * @param email
     * @param oldPassword
     * @param newPassword
     * @return
     */
    String modifyPayPassword(String email, String oldPassword, String newPassword);

    ShopListDto loadShopList(String shopType);

    ShopDetailDto loadShopDetail(int shopid, String dishType);

    CartDto loadCartDetail(Cart cart, int shopid, String email, int addressid);

    Cart addDishToCart(Cart cart, int shopid, int dishid);

    Cart subDishToCart(Cart cart, int shopid, int dishid);

    Cart delDishToCart(Cart cart, int shopid, int dishid);

    /**
     * 生成订单
     * @param email
     * @param cart
     * @param shopid
     * @param address
     * @param deliveryTime
     * @return 成功返回订单id，失败返回原因
     */
    String addOrder(String email, Cart cart, int shopid, String address, int deliveryTime);

    /**
     * 加载订单详情
     * @param orderid
     * @return
     */
    OrderDetailDto loadOrderDetail(int orderid);

    /**
     * 支付订单
     * @param email
     * @param orderid
     * @param payPassword
     */
    String pay(String email, int orderid, String payPassword);

    /**
     * 获取账户余额
     * @param email
     * @return
     */
    double getEBankBalance(String email);

    /**
     * 加载用户订单
     * @param email
     * @return
     */
    List<UserOrderDto> loadUserOrders(String email);

    /**
     * 用户退订
     * @param email
     * @param orderid
     * @return
     */
    String cancelOrder(String email, int orderid);

    /**
     * 确认送达
     * @param email
     * @param orderid
     * @return
     */
    String sureToFinishOrder(String email, int orderid);

    void test();

    List<UserOrderDto> loadUserStatisticsDetail(String email, String orderType, String startTime, String endTime, double minPrice, double maxPrice, String shopType);

    Map<String, List> loadUserStatisticsChartData(String email);
}
