package edu.nju.yummy.data;

import edu.nju.yummy.model.Orders;
import edu.nju.yummy.model.Shop;
import edu.nju.yummy.model.User;

import java.util.List;

public interface AdminDao {
    String approveModifyShopInfo(int modifyShopInfoId);

    String rejectModifyShopInfo(int modifyShopInfoId);

    int loadShopNum();

    int loadUserNum();

    /**
     * 平台总收入
     * @return
     */
    double loadTotalIncome();

    /**
     * 平台月收入
     * @return
     */
    double loadMonthIncome();

    List<User> loadAllUserList();

    List<Shop> loadAllShopList();

    /**
     * 7天内所有订单
     * @return
     */
    List<Orders> loadNear7DaysOrderList();
}
