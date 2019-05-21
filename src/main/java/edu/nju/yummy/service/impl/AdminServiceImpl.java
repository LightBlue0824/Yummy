package edu.nju.yummy.service.impl;

import edu.nju.yummy.data.AdminDao;
import edu.nju.yummy.data.ShopDao;
import edu.nju.yummy.dto.ModifyShopInfoDto;
import edu.nju.yummy.model.ModifyShopInfo;
import edu.nju.yummy.model.Orders;
import edu.nju.yummy.model.Shop;
import edu.nju.yummy.model.User;
import edu.nju.yummy.service.AdminService;
import edu.nju.yummy.util.ShopType;
import edu.nju.yummy.util.UserLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private AdminDao adminDao;

    private static long DAY = 1000 * 60 * 60 * 24;      //一天
    private static double PECENT_YUMMY = 0.2;       //平台分成

    @Override
    public String login(String password) {
        String result;
        if("admin".equals(password)){
            result = "登录成功";
        }
        else{
            result = "登录失败, 密码错误";
        }
        return result;
    }

    @Override
    public List<ModifyShopInfoDto> loadModifyShopInfoList() {
        return shopDao.loadModifyShopInfoList();
    }

    @Override
    public String approveModifyShopInfo(int modifyShopInfoId) {
        return adminDao.approveModifyShopInfo(modifyShopInfoId);
    }

    @Override
    public String rejectModifyShopInfo(int modifyShopInfoId) {
        return adminDao.rejectModifyShopInfo(modifyShopInfoId);
    }

    @Override
    public int loadShopNum() {
        return adminDao.loadShopNum();
    }

    @Override
    public int loadUserNum() {
        return adminDao.loadUserNum();
    }

    @Override
    public double loadTotalIncome() {
        return adminDao.loadTotalIncome();
    }

    @Override
    public double loadMonthIncome() {
        return adminDao.loadMonthIncome();
    }

    @Override
    public Map<String, List> loadStatisticsChartData() {
        Map<String, List> statisticsChartData = new HashMap<>();
        //统计各等级用户数量
        Map<String, Integer> userLevelStatistics = new HashMap<>();
        for(String level : UserLevel.USER_LEVEL){       //初始化
            userLevelStatistics.put(level, 0);
        }
        List<User> userList = adminDao.loadAllUserList();
        for(User user : userList){
            String userLevel = UserLevel.USER_LEVEL[user.getLevel()-1];
            userLevelStatistics.put(userLevel, userLevelStatistics.get(userLevel)+1);
        }
        statisticsChartData.put("userLevels", new ArrayList(userLevelStatistics.keySet()));
        statisticsChartData.put("userLevelData", new ArrayList(userLevelStatistics.values()));

        //统计各种类商店数量
        Map<String, Integer> shopTypeStatistics = new HashMap<>();
        for(String type : ShopType.shopTypes){          //初始化
            shopTypeStatistics.put(type, 0);
        }
        List<Shop> shopList = adminDao.loadAllShopList();
        for(Shop shop : shopList){
            String shopType = shop.getType();
            shopTypeStatistics.put(shopType, shopTypeStatistics.get(shopType)+1);
        }
        statisticsChartData.put("shopTypes", new ArrayList(shopTypeStatistics.keySet()));
        statisticsChartData.put("shopTypeData", new ArrayList(shopTypeStatistics.values()));

        //统计7日内收入情况
        Map<String, Double> near7DaysOrderIncome = new TreeMap<>();                 //统计近7日订单
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        Date tempDate = new Date();
        tempDate.setTime(tempDate.getTime()-DAY*6);     //7天的最早一天
        Date firstDate = (Date) tempDate.clone();    //7天的最早一天
        firstDate.setHours(0);  firstDate.setMinutes(0);    firstDate.setSeconds(0);
        Date lastDate = new Date();    //第7天(今天)
        for(int i = 6; i >= 0; i--){        //初始设置近7天的时间
            near7DaysOrderIncome.put(simpleDateFormat.format(tempDate), 0.0);
            tempDate.setTime(tempDate.getTime()+DAY);
        }
        List<Orders> near7DaysOrderList = adminDao.loadNear7DaysOrderList();
        for(Orders order : near7DaysOrderList){
            Date orderTime = order.getOrdertime();
            if((order.getState().equals("已支付") || order.getState().equals("已送达"))
                    && !orderTime.before(firstDate) && !orderTime.after(lastDate)){      //判断是否在7天内，即大于等于第一天，小于等于第7天
                String str_orderTime = simpleDateFormat.format(orderTime);
                near7DaysOrderIncome.put(str_orderTime, near7DaysOrderIncome.get(str_orderTime)+order.getActual()*PECENT_YUMMY);
            }
        }
        statisticsChartData.put("near7Days", new ArrayList(near7DaysOrderIncome.keySet()));
        statisticsChartData.put("near7DaysIncome", new ArrayList(near7DaysOrderIncome.values()));

        return statisticsChartData;
    }
}
