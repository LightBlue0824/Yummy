package edu.nju.yummy.data.impl;

import edu.nju.yummy.data.AdminDao;
import edu.nju.yummy.model.ModifyShopInfo;
import edu.nju.yummy.model.Orders;
import edu.nju.yummy.model.Shop;
import edu.nju.yummy.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository
public class AdminDaoImpl implements AdminDao {
    @Autowired
    private SessionFactory sessionFactory;

    private static long DAY = 1000 * 60 * 60 * 24;
    private static double PERCENT_YUMMY = 0.2;      //平台分成

    @Override
    public String approveModifyShopInfo(int modifyShopInfoId) {
        String result;

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //修改为同意状态
        ModifyShopInfo modifyShopInfo = session.find(ModifyShopInfo.class, modifyShopInfoId);
        if(modifyShopInfo != null && modifyShopInfo.getIsapproved() == 0){
            modifyShopInfo.setIsapproved(1);
        }
        else{
            result = "同意修改申请失败, 修改申请不存在";
        }
        //修改商店的信息
        Shop shop = session.find(Shop.class, modifyShopInfo.getShopid());
        if(shop != null){
            shop.setName(modifyShopInfo.getName());     shop.setType(modifyShopInfo.getType());
            shop.setX(modifyShopInfo.getX());           shop.setY(modifyShopInfo.getY());
            shop.setBrief(modifyShopInfo.getBrief());
        }
        else {
            result = "同意修改申请失败, 商店异常";
        }

        transaction.commit();
        session.close();
        result = "同意修改申请成功";
        return result;
    }

    @Override
    public String rejectModifyShopInfo(int modifyShopInfoId) {
        String result;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ModifyShopInfo modifyShopInfo = session.find(ModifyShopInfo.class, modifyShopInfoId);
        modifyShopInfo.setIsapproved(2);        //2表示已拒绝
        session.update(modifyShopInfo);
        transaction.commit();
        session.close();
        result = "拒绝修改申请成功";
        return result;
    }

    @Override
    public int loadShopNum() {
        int result = -1;
        Session session = sessionFactory.openSession();
        Query<Long> shopNumQuery = session.createQuery("select count(shopid) from Shop ");
        Long shopNum = shopNumQuery.uniqueResult();
        result = shopNum == null ? 0 : shopNum.intValue();
        return result;
    }

    @Override
    public int loadUserNum() {
        int result = -1;
        Session session = sessionFactory.openSession();
        Query<Long> userNumQuery = session.createQuery("select count(email) from User ");
        Long userNum = userNumQuery.uniqueResult();
        result = userNum == null ? 0 : userNum.intValue();
        return result;
    }

    @Override
    public double loadTotalIncome() {
        double result = -1;
        Session session = sessionFactory.openSession();
        Query<Double> totalIncomeQuery = session.createQuery("select sum(actual) from Orders where state= '已送达'");
        Double totalIncome = totalIncomeQuery.uniqueResult();
        result = totalIncome == null ? 0 : totalIncome*PERCENT_YUMMY;
        return result;
    }

    @Override
    public double loadMonthIncome() {
        double result = -1;
        Session session = sessionFactory.openSession();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        String firstDayOfMonth = today.substring(0, 8) + "01";
        Query<Double> monthIncomeQuery = session.createQuery("select sum(actual) from Orders where state= '已送达' and Date(ordertime) >= '"+firstDayOfMonth+"' and Date(ordertime) <= '"+today+"'");
//        monthIncomeQuery.setParameter("firstDay", firstDayOfMonth);
//        monthIncomeQuery.setParameter("today", today);
        Double monthIncome = monthIncomeQuery.uniqueResult();
        result = monthIncome == null ? 0 : monthIncome*PERCENT_YUMMY;
        return result;
    }

    @Override
    public List<User> loadAllUserList() {
        Session session = sessionFactory.openSession();
        Query<User> userQuery = session.createQuery("from User ");
        List<User> userList = userQuery.getResultList();
        return userList;
    }

    @Override
    public List<Shop> loadAllShopList() {
        Session session = sessionFactory.openSession();
        Query<Shop> shopQuery = session.createQuery("from Shop ");
        List<Shop> shopList = shopQuery.getResultList();
        return shopList;
    }

    @Override
    public List<Orders> loadNear7DaysOrderList() {
        Session session = sessionFactory.openSession();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDate = new Date();
        firstDate.setTime(firstDate.getTime()-DAY*6);     //7天的最早一天
        Date lastDate = new Date();    //第7天(今天)
        String firstDate_str = simpleDateFormat.format(firstDate);
        String lastDate_str = simpleDateFormat.format(lastDate);

        Query<Orders> orderQuery = session.createQuery("from Orders where state= '已送达' and Date(ordertime) >= '"+firstDate_str+"' and Date(ordertime) <= '"+lastDate_str+"'");
        List<Orders> orderList = orderQuery.getResultList();
        return orderList;
    }
}
