package edu.nju.yummy.service.impl;

import edu.nju.yummy.data.OrderDao;
import edu.nju.yummy.data.ShopDao;
import edu.nju.yummy.data.UserDao;
import edu.nju.yummy.dto.DishListDto;
import edu.nju.yummy.dto.ShopInfoDto;
import edu.nju.yummy.dto.ShopOrderDto;
import edu.nju.yummy.service.ShopService;
import edu.nju.yummy.util.PercentUtil;
import edu.nju.yummy.util.TimeUtil;
import edu.nju.yummy.util.UserLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Override
    public String register(String name, String password, double x, double y, String type, String brief) {
        return shopDao.register(name, password, x, y, type, brief);
    }

    @Override
    public String login(String id, String password) {
        return shopDao.login(id, password);
    }

    @Override
    public ShopInfoDto loadShopInfo(String id) {
        return shopDao.loadShopInfo(id);
    }

    @Override
    public String modifyShopInfo(String id, String name, double x, double y, String type, String brief) {
        return shopDao.modifyShopInfo(id, name, x, y, type, brief);
    }

    @Override
    public DishListDto loadShopDishList(String shopid, String type) {
        return shopDao.loadShopDishList(shopid, type);
    }

    @Override
    public String modifyDish(int dishid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount) {
        return shopDao.modifyDish(dishid, name, type, price, brief, num, isLimited, startTime, endTime, isDiscount, discount);
    }

    @Override
    public String addDish(String shopid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount) {
        return shopDao.addDish(shopid, name, type, price, brief, num, isLimited, startTime, endTime, isDiscount, discount);
    }

    @Override
    public String addType(String shopid, String typeName) {
        return shopDao.addType(shopid, typeName);
    }

    @Override
    public int getModifyInfoState(String shopid) {
        return shopDao.getModifyInfoState(shopid);
    }

    @Override
    public List<ShopOrderDto> loadShopOrderList(int shopid, String startTime, String endTime, double minPrice, double maxPrice, int userLevel) {
        return orderDao.loadShopOrderList(shopid, startTime, endTime, minPrice, maxPrice, userLevel);
    }

    @Override
    public Map<String, List> loadShopStatisticsChartData(int shopid) {
        List<ShopOrderDto> shopOrderDtoList = loadShopOrderList(shopid, null, null, -1, -1, -1);        //该商店所有的订单
        Map<String, List> statisticsData = new HashMap<>();
        Map<String, Integer> orderStateStatistics = new HashMap<>();        //统计订单种类
        Map<String, Integer> userLevelStatistics = new HashMap<>();          //统计订购该商店的用户的等级
        for(String level : UserLevel.USER_LEVEL){       //初始化
            userLevelStatistics.put(level, 0);
        }
        Map<String, Double> near7DaysIncome = new TreeMap<>();                 //统计近7日收入
//        Map<String, Double> near7DaysIncome = new HashMap<>();                 //统计近7日收入
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        SimpleDateFormat simpleDateFormatWithYear = new SimpleDateFormat("yyyy-MM-dd");
        Date tempDate = new Date();
        tempDate.setTime(tempDate.getTime()- TimeUtil.DAY*6);     //7天的最早一天
        Date firstDate = (Date) tempDate.clone();    //7天的最早一天
        firstDate.setHours(0);  firstDate.setMinutes(0);    firstDate.setSeconds(0);
        Date lastDate = new Date();    //第7天(今天)
        for(int i = 6; i >= 0; i--){        //初始设置近7天的时间
            near7DaysIncome.put(simpleDateFormat.format(tempDate), 0.0);
            tempDate.setTime(tempDate.getTime()+TimeUtil.DAY);
        }
        for(ShopOrderDto shopOrderDto : shopOrderDtoList){
            //统计订单种类
            int orderStateNum = orderStateStatistics.get(shopOrderDto.getOrderState()) == null ? 1 : orderStateStatistics.get(shopOrderDto.getOrderState())+1;
            orderStateStatistics.put(shopOrderDto.getOrderState(), orderStateNum);

            //统计订购该商店的用户等级
            int userLevel = userDao.getUserLevel(shopOrderDto.getEmail());
            String userLevel_str = UserLevel.USER_LEVEL[userLevel-1];
            int userLevelOrderNum = userLevelStatistics.get(userLevel_str)+1;
            userLevelStatistics.put(userLevel_str, userLevelOrderNum);

            //统计近7日收入
            try {
                Date orderTime = simpleDateFormatWithYear.parse(shopOrderDto.getOrderTime());
                if(shopOrderDto.getOrderState().equals("已送达")
                        && !orderTime.before(firstDate) && !orderTime.after(lastDate)){      //判断是否在7天内，即大于等于第一天，小于等于第7天
                    String str_orderTime = simpleDateFormat.format(orderTime);
                    near7DaysIncome.put(str_orderTime, near7DaysIncome.get(str_orderTime) + shopOrderDto.getActual() * PercentUtil.PERCENT_SHOP);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        statisticsData.put("orderStates", new ArrayList(orderStateStatistics.keySet()));
        statisticsData.put("orderStatesData", new ArrayList(orderStateStatistics.values()));
        statisticsData.put("userLevels", new ArrayList(userLevelStatistics.keySet()));
        statisticsData.put("userLevelsData", new ArrayList(userLevelStatistics.values()));
        statisticsData.put("near7Days", new ArrayList(near7DaysIncome.keySet()));
        statisticsData.put("near7DaysIncome", new ArrayList(near7DaysIncome.values()));
        return statisticsData;
    }

    @Override
    public List<String> loadAllDishByStrExceptPackage(int shopid) {
        return shopDao.loadAllDishByStrExceptPackage(shopid);
    }
}
