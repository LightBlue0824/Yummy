package edu.nju.yummy.data;

import edu.nju.yummy.dto.CartDto;
import edu.nju.yummy.dto.OrderDetailDto;
import edu.nju.yummy.dto.ShopOrderDto;
import edu.nju.yummy.dto.UserOrderDto;

import java.util.List;

public interface OrderDao {
    int addOrder(String email, int shopid, CartDto cartDto, String address, int deliveryTime) throws Exception;

    OrderDetailDto loadOrderDetail(int orderid);

    /**
     * 支付
     * @param email
     * @param orderid
     * @param payPassword
     * @return 账户余额
     * @throws Exception
     */
    double pay(String email, int orderid, String payPassword) throws Exception;

    void checkAllOrderState();

    List<UserOrderDto> loadUserOrders(String email);

    String cancelOrder(String email, int orderid);

    String sureToFinishOrder(String email, int orderid);

    void test();

    List<UserOrderDto> loadUserStatisticsDetail(String email, String orderType, String startTime, String endTime, double minPrice, double maxPrice, String shopType);

    List<ShopOrderDto> loadShopOrderList(int shopid, String startTime, String endTime, double minPrice, double maxPrice, int userLevel);
}
