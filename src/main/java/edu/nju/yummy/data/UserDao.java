package edu.nju.yummy.data;

import edu.nju.yummy.dto.DeliveryAddressDto;
import edu.nju.yummy.dto.ShopListDto;
import edu.nju.yummy.dto.UserInfoDto;
import edu.nju.yummy.model.Ebank;

import java.util.List;

public interface UserDao {
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

    Ebank getEbank(String email);

    int getUserLevel(String email);
}
