package edu.nju.yummy.service.impl;

import edu.nju.yummy.data.OrderDao;
import edu.nju.yummy.data.ShopDao;
import edu.nju.yummy.data.UserDao;
import edu.nju.yummy.dto.*;
import edu.nju.yummy.model.Cart;
import edu.nju.yummy.model.Dish;
import edu.nju.yummy.model.Ebank;
import edu.nju.yummy.service.UserService;
import edu.nju.yummy.util.MailUtil;
import edu.nju.yummy.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private OrderDao orderDao;

    private static long DAY = 1000 * 60 * 60 * 24;      //一天

    @Override
    public String login(String email, String password) {
        return userDao.login(email, password);
    }

    @Override
    public String register(String email, String password) {
        String result = userDao.register(email, password);

        if(!result.contains("注册失败")){
            String address = "localhost:8080/yummy/user/verify?code=" + result;
            String message = "<h2>注册成功</h2>"+
                    "<p>请点击下方链接进行验证</p>"+
                    "<a href=\"" + address + "\">点击进行验证</a>"+
                    "<p>" + address +"</p>";
            MailUtil.sendMail(email, message);
            result = "注册成功";
        }
        return result;
    }

    @Override
    public String verify(String code) {
        return userDao.verify(code);
    }

    @Override
    public String destroy(String email) {
        return userDao.destroy(email);
    }

    @Override
    public UserInfoDto loadUserInfo(String email) {
        return userDao.loadUserInfo(email);
    }

    @Override
    public String modifyUserInfo(String email, String name, String phone) {
        return userDao.modifyUserInfo(email, name, phone);
    }

    @Override
    public List<DeliveryAddressDto> loadUserDeliveryAddress(String email) {
        return userDao.loadUserDeliveryAddress(email);
    }

    @Override
    public boolean addUserDeliveryAddress(String email, String name, String phone, double x, double y) {
        return userDao.addUserDeliveryAddress(email, name, phone, x, y);
    }

    @Override
    public String modifyPayPassword(String email, String oldPassword, String newPassword) {
        return userDao.modifyPayPassword(email, oldPassword, newPassword);
    }

    @Override
    public ShopListDto loadShopList(String shopType) {
        return shopDao.loadShopList(shopType);
    }

    @Override
    public ShopDetailDto loadShopDetail(int shopid, String dishType) {
        ShopDetailDto shopDetailDto;
        ShopInfoDto shopInfoDto = shopDao.loadShopInfo(String.valueOf(shopid));
        //转换、计算为user方显示用的dto
        ShopDto shopDto = new ShopDto(shopid, shopInfoDto.getName(), shopInfoDto.getX(), shopInfoDto.getY(), shopInfoDto.getType(), shopInfoDto.getBrief());
        //商品列表
        DishListDto dishListDto = shopDao.loadShopDishList(String.valueOf(shopid), dishType);
        //过滤已过期商品
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Dish> dishList = dishListDto.getDishList();
        Date now = new Date();
        for(int i = 0; i < dishList.size(); i++){
            Dish dish = dishList.get(i);
            if(dish.getIslimited() == 0){       //如果不是限时商品
                continue;
            }
            Date startTime = dish.getStarttime();
            Date endTime = dish.getEndtime();
            endTime.setTime(endTime.getTime()+ TimeUtil.DAY-1);
            if(now.before(startTime) || now.after(dish.getEndtime())){
                dishList.remove(dish);
                i--;
            }
        }
        shopDetailDto = new ShopDetailDto(shopDto, dishListDto);
        return shopDetailDto;
    }

    @Override
    public CartDto loadCartDetail(Cart cart, int shopid, String email, int addressid) {
        CartDto cartDto = new CartDto(shopid, shopDao.loadShopInfo(String.valueOf(shopid)).getName());
        //加载送餐地址
        cartDto.setAddressDtoList(userDao.loadUserDeliveryAddress(email));
        if (cart == null || cartDto.getAddressDtoList().size() == 0) {         //null保护
            return cartDto;
        }
        //获取购物车项
        Map<Integer, Integer> cartOfThisShop = cart.getCart().get(shopid);
        if (cartOfThisShop == null) {         //null保护
            return cartDto;
        }
        for(Map.Entry<Integer, Integer> temp : cartOfThisShop.entrySet()){
            Dish dish = shopDao.getDish(temp.getKey());
            CartAndOrderItemDto cartItemDto = new CartAndOrderItemDto(dish.getDishid(), dish.getName(), temp.getValue(), dish.getPrice());
            if(dish.getActual() != -1){     //判断并设定特价
                cartItemDto.setActual(dish.getActual());
            }
            cartDto.addCartItemDto(cartItemDto);
        }
        //计算距离和预计送达时间
        cartDto.setSelectedAddressId(addressid);            //设置选中的addressid
        DeliveryAddressDto addressDto = null;
        List<DeliveryAddressDto> addressDtoList = cartDto.getAddressDtoList();
        if(addressid == -1){
            addressDto = addressDtoList.get(0);
        }
        else{
            for(DeliveryAddressDto temp : addressDtoList){
                if(temp.getAddressid() == addressid){
                    addressDto = temp;
                }
            }
            if(addressDto == null){
                addressDto = addressDtoList.get(0);
            }
        }
        ShopInfoDto shopInfoDto = shopDao.loadShopInfo(String.valueOf(shopid));
        int distance = calcDistance(addressDto.getX(), addressDto.getY(), shopInfoDto.getX(), shopInfoDto.getY());
        int time = calcDeliveryTime(distance);
        cartDto.setDistance(distance);
        cartDto.setDeliveryTime(time);

        return cartDto;
    }

    @Override
    public Cart addDishToCart(Cart cart, int shopid, int dishid) {
        if(cart == null){
            cart = new Cart();
        }
        cart.addDish(shopid, dishid);
        return cart;
    }

    @Override
    public Cart subDishToCart(Cart cart, int shopid, int dishid) {
        if(cart == null){
            cart = new Cart();
        }
        cart.subDish(shopid, dishid);
        return cart;
    }

    @Override
    public Cart delDishToCart(Cart cart, int shopid, int dishid) {
        if(cart == null){
            cart = new Cart();
        }
        cart.delDish(shopid, dishid);
        return cart;
    }

    @Override
    public String addOrder(String email, Cart cart, int shopid, String address, int deliveryTime) {
        CartDto cartDto = loadCartDetail(cart, shopid, email, -1);

        String result;
        try {
            int orderid = orderDao.addOrder(email, shopid, cartDto, address, deliveryTime);
            result = String.valueOf(orderid);
        }catch (Exception e){
            result = "订单生成失败，"+e.getMessage();
        }

        return result;
    }

    @Override
    public OrderDetailDto loadOrderDetail(int orderid) {
        OrderDetailDto orderDetailDto = orderDao.loadOrderDetail(orderid);
        if(orderDetailDto.getState().equals("未支付")){
            Ebank ebank = userDao.getEbank(orderDetailDto.getEmail());
            orderDetailDto.setHasPayPassword(ebank.getPaypassword() != null);
        }
        return orderDetailDto;
    }

    @Override
    public String pay(String email, int orderid, String payPassword) {
        String result = "支付失败, ";
        try {
            double payResult = orderDao.pay(email, orderid, payPassword);
            if(payResult != -1){
                result = "支付成功, 账户余额: "+payResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result += e.getMessage();
        }
        return result;
    }

    @Override
    public double getEBankBalance(String email) {
        return userDao.getEbank(email).getBalance();
    }

    @Override
    public List<UserOrderDto> loadUserOrders(String email) {
        return orderDao.loadUserOrders(email);
    }

    @Override
    public String cancelOrder(String email, int orderid) {
        return orderDao.cancelOrder(email, orderid);
    }

    @Override
    public String sureToFinishOrder(String email, int orderid) {
        return orderDao.sureToFinishOrder(email, orderid);
    }

    @Override
    public List<UserOrderDto> loadUserStatisticsDetail(String email, String orderType, String startTime, String endTime, double minPrice, double maxPrice, String shopType) {
        return orderDao.loadUserStatisticsDetail(email, orderType, startTime, endTime, minPrice, maxPrice, shopType);
    }

    @Override
    public Map<String, List> loadUserStatisticsChartData(String email) {
        List<UserOrderDto> userOrderDtoList = loadUserOrders(email);        //该用户所有的订单
        Map<String, List> statisticsData = new HashMap<>();
        Map<String, Integer> orderStateStatistics = new HashMap<>();        //统计订单种类
        Map<String, Integer> shopTypeStatistics = new HashMap<>();          //统计用户订购商店的种类
        Map<String, Integer> near7DaysOrderNum = new TreeMap<>();                 //统计近7日订单
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        SimpleDateFormat simpleDateFormatWithYear = new SimpleDateFormat("yyyy-MM-dd");
        Date tempDate = new Date();
//        tempDate.setDate(tempDate.getDate()-6);     //7天的最早一天
        tempDate.setTime(tempDate.getTime()-DAY*6);     //7天的最早一天
        Date firstDate = (Date) tempDate.clone();    //7天的最早一天
        firstDate.setHours(0);  firstDate.setMinutes(0);    firstDate.setSeconds(0);
        Date lastDate = new Date();    //第7天(今天)
        for(int i = 6; i >= 0; i--){        //初始设置近7天的时间
            near7DaysOrderNum.put(simpleDateFormat.format(tempDate), 0);
//            tempDate.setDate(tempDate.getDate()+1);
            tempDate.setTime(tempDate.getTime()+DAY);
        }
        for(UserOrderDto userOrderDto : userOrderDtoList){
            //统计订单种类
            int orderStateNum = orderStateStatistics.get(userOrderDto.getOrderState()) == null ? 1 : orderStateStatistics.get(userOrderDto.getOrderState())+1;
            orderStateStatistics.put(userOrderDto.getOrderState(), orderStateNum);
            //统计用户订单商店的类别
            String shopType = shopDao.getShop(userOrderDto.getShopid()).getType();
            int shopTypeNum = shopTypeStatistics.get(shopType) == null ? 1 : shopTypeStatistics.get(shopType)+1;
            shopTypeStatistics.put(shopType, shopTypeNum);
            //统计近7日订单
            try {
                Date orderTime = simpleDateFormatWithYear.parse(userOrderDto.getOrderTime());
                if((userOrderDto.getOrderState().equals("已支付") || userOrderDto.getOrderState().equals("已送达"))
                        && !orderTime.before(firstDate) && !orderTime.after(lastDate)){      //判断是否在7天内，即大于等于第一天，小于等于第7天
                    String str_orderTime = simpleDateFormat.format(orderTime);
                    near7DaysOrderNum.put(str_orderTime, near7DaysOrderNum.get(str_orderTime)+1);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        statisticsData.put("orderStates", new ArrayList(orderStateStatistics.keySet()));
        statisticsData.put("orderStatesData", new ArrayList(orderStateStatistics.values()));
        statisticsData.put("shopTypes", new ArrayList(shopTypeStatistics.keySet()));
        statisticsData.put("shopTypesData", new ArrayList(shopTypeStatistics.values()));
        statisticsData.put("near7Days", new ArrayList(near7DaysOrderNum.keySet()));
        statisticsData.put("near7DaysOrderNum", new ArrayList(near7DaysOrderNum.values()));
        return statisticsData;
    }

    @Override
    public void test() {
        orderDao.test();
    }

    /**
     * 计算距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private int calcDistance(double x1, double y1, double x2, double y2){
        return (int) Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2,2));
    }

    /**
     * 计算配送时间
     * @param distance
     * @return
     */
    private int calcDeliveryTime(int distance){
        int time;
        if(distance <= 10){
            time = 20;
        }else if(distance <= 30){
            time = distance * 2;
        }else{
            time = -1;          //无法配送
        }
        return time;
    }

}
