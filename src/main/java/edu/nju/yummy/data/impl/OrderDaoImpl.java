package edu.nju.yummy.data.impl;

import edu.nju.yummy.data.OrderDao;
import edu.nju.yummy.dto.*;
import edu.nju.yummy.model.*;
import edu.nju.yummy.util.UserLevel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private SessionFactory sessionFactory;

    //超时时间
    private static long TIMEOUT = 1000 * 60 * 2;
    private static long MINUTE = 1000 * 60;
    //平台分成
    private static double PERCENT_SHOP = 0.8;

    @Override
    public int addOrder(String email, int shopid, CartDto cartDto, String address, int deliveryTime) throws Exception {
        int orderid;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Integer maxid = (Integer)session.createQuery("select max(a.orderid) from Orders as a").uniqueResult();
            orderid = maxid == null ? 1 : maxid+1;
            Integer maxItemId = (Integer)session.createQuery("select max(a.itemid) from OrderItem as a").uniqueResult();
            int itemid = maxItemId == null ? 1 : maxItemId+1;
            //检查商品是否都充足，并保存项
            List<CartAndOrderItemDto> cartItemDtoList = cartDto.getCartItemDtoList();
            for(CartAndOrderItemDto cartItemDto : cartItemDtoList){
                Dish dish = session.find(Dish.class, cartItemDto.getDishid());
                if(dish != null){
                    int dishNum = dish.getNum() - cartItemDto.getNum();
                    if(dishNum >= 0){
                        //更新商品库存
                        dish.setNum(dishNum);
                        session.save(dish);
                        //保存订单项
                        OrderItem orderItem = new OrderItem(itemid, orderid, cartItemDto.getDishid(),
                                cartItemDto.getDishName(), cartItemDto.getNum(), cartItemDto.getPrice(), cartItemDto.getActual(), cartItemDto.getSum());
                        session.save(orderItem);
                        session.flush();
                        itemid++;
                    }
                    else{
                        throw new Exception("商品库存不足: "+cartItemDto.getDishName());
                    }
                }
            }
            //计算实付
            User user = session.find(User.class, email);
            double actual = cartDto.getActual() * UserLevel.USER_DISCOUNT_DOUBLE[user.getLevel()-1];
            //生成订单对象
            Orders orders = new Orders(orderid, new Timestamp(new java.util.Date().getTime()), email,
                    shopid,cartDto.getShopName(), cartDto.getTotal(), actual, address, deliveryTime);
            session.save(orders);

            //commit
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();

            transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }

        return orderid;
    }

    @Override
    public OrderDetailDto loadOrderDetail(int orderid) {
        OrderDetailDto orderDetailDto;
        Session session = sessionFactory.openSession();
        //查询order
        Orders order = session.find(Orders.class, orderid);
        order = checkOrderState(order);         //检查是否超时, 是否送达等
        orderDetailDto = new OrderDetailDto(orderid, order.getOrdertime().toString(), order.getEmail(), order.getShopid(),
                order.getShopName(), order.getTotal(), order.getActual(), order.getDeliveryaddress(), order.getState());
        if(order.getPaytime() != null){     //已支付
            orderDetailDto.setPayTime(order.getPaytime().toString());
            if(!order.getState().equals("已送达")){        //未送达
                long diff = new Date().getTime() - order.getPaytime().getTime();
                orderDetailDto.setDeliveryTime((int) (order.getDeliverytime() - diff/MINUTE));
            }
            else if(order.getState().equals("已送达")){
                orderDetailDto.setFinishTime(order.getFinishtime().toString());
            }
        }
        //查询该order的item项
        Query<OrderItem> itemQuery = session.createQuery("from OrderItem where orderid= :orderid");
        itemQuery.setParameter("orderid", orderid);
        for(OrderItem item : itemQuery.getResultList()){
            orderDetailDto.addOrderItem(new CartAndOrderItemDto(item.getDishid(), item.getDishname(), item.getNum(), item.getPrice(), item.getActual(), item.getSum()));
        }

        return orderDetailDto;
    }

    @Override
    public double pay(String email, int orderid, String payPassword) throws Exception{
        double result = -1;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            Ebank ebank = session.find(Ebank.class, email);
            if(payPassword.equals(ebank.getPaypassword())){
                Orders order = session.find(Orders.class, orderid);
                double balance = ebank.getBalance() - order.getActual();
                if(balance >= 0){        //余额充足
                    //更新余额和订单状态
                    ebank.setBalance(balance);
                    order.setState("已支付");
                    order.setPaytime(new Timestamp(new java.util.Date().getTime()));

                    session.update(ebank);
                    session.update(order);

                    transaction.commit();
                    result = balance;
                }
                else{
                    throw new Exception("用户余额不足");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
        return result;
    }

    @Override
    public void checkAllOrderState() {
        Session session = sessionFactory.openSession();
        List<Orders> notPayed = session.createQuery("from Orders where state= '未支付'").getResultList();
        for(Orders order : notPayed){
            checkOrderState(order);
        }
        List<Orders> notFinished = session.createQuery("from  Orders where state='已支付'").getResultList();
        for(Orders order : notFinished){
            checkOrderState(order);
        }
    }

    @Override
    public List<UserOrderDto> loadUserOrders(String email) {
        List<UserOrderDto> userOrderDtoList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Query<Orders> ordersQuery = session.createQuery("from Orders where email= :email order by orderid desc ");
        ordersQuery.setParameter("email", email);
        List<Orders> ordersList = ordersQuery.getResultList();
        for(Orders order : ordersList){
            Query<OrderItem> itemQuery = session.createQuery("from OrderItem where orderid= :orderid order by itemid");
            itemQuery.setParameter("orderid", order.getOrderid());
            List<OrderItem> orderItemList = itemQuery.getResultList();
            UserOrderDto userOrderDto = new UserOrderDto(order.getOrderid(), order.getShopid(), order.getShopName(), order.getState(),
                    order.getOrdertime().toString(), orderItemList.get(0).getDishname(), calcSizeOfOrderItems(orderItemList), order.getActual());
            if(order.getState().equals("已支付")){     //已支付的计算剩余配送时间
                userOrderDto.setDeliveryTime(calcRemainDeliveryTime(order.getPaytime().getTime(), order.getDeliverytime()));
            }
            userOrderDtoList.add(userOrderDto);
        }
        return userOrderDtoList;
    }

    @Override
    public String cancelOrder(String email, int orderid) {
        String result;
        Session session = sessionFactory.openSession();
        Orders order = session.find(Orders.class, orderid);
        if(order != null && order.getEmail().equals(email)){
            Ebank ebank = session.find(Ebank.class, email);
            if(ebank != null){
                Transaction transaction = session.beginTransaction();
                order.setState("已取消");
                double returnMoney = calcReturnMoney(order);
                ebank.setBalance(ebank.getBalance()+returnMoney);
                session.update(order);
                session.update(ebank);
                transaction.commit();
                result = "取消订单成功";
            }
            else{
                result = "取消订单失败, 用户状态异常";
            }
        }
        else{
            result = "取消订单失败, 订单不存在";
        }
        session.close();
        return result;
    }

    @Override
    public String sureToFinishOrder(String email, int orderid) {
        String result;
        Session session = sessionFactory.openSession();
        Orders order = session.find(Orders.class, orderid);
        if(order != null && order.getEmail().equals(email)){
            Transaction transaction = session.beginTransaction();
            order.setState("已送达");
            order.setFinishtime(new Timestamp(new java.util.Date().getTime()));     //设置送达时间
            session.update(order);

            //计算并更新用户等级, 只计算已送达订单
            User user = session.find(User.class, email);
            Query<Double> userSumQuery = session.createQuery("select sum(actual) from Orders where email= :email and state='已送达'");
            userSumQuery.setParameter("email", email);
            Double sum = userSumQuery.uniqueResult();
            sum = sum == null ? 0 : sum;
            if(user.getLevel() == 1 && sum >= UserLevel.CONSUME[0]) {
                user.setLevel(2);
            }
            else if(user.getLevel() == 2 && sum >= UserLevel.CONSUME[1]){
                user.setLevel(3);
            }
            //计算并给餐厅分成
            Shop shop = session.find(Shop.class, order.getShopid());
            double shopIncome = shop.getIncome();
            shopIncome += order.getActual() * PERCENT_SHOP;
            shop.setIncome(shopIncome);

            session.update(user);
            session.update(shop);

            transaction.commit();
            result = "确认送达成功";
        }
        else{
            result = "确认送达失败, 权限不足";
        }
        session.close();
        return result;
    }

    @Override
    public List<UserOrderDto> loadUserStatisticsDetail(String email, String orderType, String startTime, String endTime, double minPrice, double maxPrice, String shopType) {
        List<UserOrderDto> userOrderDtoList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        String hql = "from Orders where email= '"+email+"'";
        if(orderType != null && !orderType.equals("")){
            if(orderType.equals("点餐")) {
                hql += " and (state= '已支付' or state= '已送达')";
            }
            else if(orderType.equals("退订")){
                hql += " and state= '已取消'";
            }
        }
        if(startTime != null && !startTime.equals("")){
            hql += " and Date(ordertime)>= '" + startTime+"'";
        }
        if(endTime != null && !endTime.equals("")){
            hql += " and Date(ordertime)<= '" + endTime+"'";
        }
        if(minPrice != -1){
            hql += " and actual>=" + minPrice;
        }
        if(maxPrice != -1){
            hql += " and actual<=" + maxPrice;
        }
        if(shopType != null && !shopType.equals("")){
            hql += " and shopid in (select shopid from Shop as s where s.type= '"+ shopType +"')";
        }
        hql += " order by orderid desc ";
        Query<Orders> ordersQuery = session.createQuery(hql);
        List<Orders> ordersList = ordersQuery.getResultList();
        for(Orders order : ordersList){
            Query<OrderItem> itemQuery = session.createQuery("from OrderItem where orderid= :orderid order by itemid");
            itemQuery.setParameter("orderid", order.getOrderid());
            List<OrderItem> orderItemList = itemQuery.getResultList();
            UserOrderDto userOrderDto = new UserOrderDto(order.getOrderid(), order.getShopid(), order.getShopName(), order.getState(),
                    order.getOrdertime().toString(), orderItemList.get(0).getDishname(), calcSizeOfOrderItems(orderItemList), order.getActual());
            userOrderDtoList.add(userOrderDto);
        }

        return userOrderDtoList;
    }

    @Override
    public List<ShopOrderDto> loadShopOrderList(int shopid, String startTime, String endTime, double minPrice, double maxPrice, int userLevel) {
        List<ShopOrderDto> shopOrderDtoList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        String hql = "from Orders where shopid= "+shopid;
        if(startTime != null && !startTime.equals("")){
            hql += " and Date(ordertime)>= '" + startTime+"'";
        }
        if(endTime != null && !endTime.equals("")){
            hql += " and Date(ordertime)<= '" + endTime+"'";
        }
        if(minPrice != -1){
            hql += " and actual>=" + minPrice;
        }
        if(maxPrice != -1){
            hql += " and actual<=" + maxPrice;
        }
        if(userLevel != -1){
            hql += " and email in (select email from User as u where u.level= "+ userLevel +")";
        }
        hql += " order by orderid desc ";
        Query<Orders> ordersQuery = session.createQuery(hql);
        List<Orders> ordersList = ordersQuery.getResultList();
        for(Orders order : ordersList){
            Query<OrderItem> itemQuery = session.createQuery("from OrderItem where orderid= :orderid order by itemid");
            itemQuery.setParameter("orderid", order.getOrderid());
            List<OrderItem> orderItemList = itemQuery.getResultList();
            List<CartAndOrderItemDto> orderItemDtoList = new ArrayList<>();
            for(OrderItem orderItem : orderItemList){
                CartAndOrderItemDto orderItemDto = new CartAndOrderItemDto(orderItem.getDishid(), orderItem.getDishname(),
                        orderItem.getNum(), orderItem.getPrice(), orderItem.getActual(), orderItem.getSum());
                orderItemDtoList.add(orderItemDto);
            }
            ShopOrderDto shopOrderDto = new ShopOrderDto(order.getOrderid(), order.getOrdertime().toString(), order.getEmail(), order.getDeliveryaddress(),
                    order.getState(), orderItemDtoList, order.getActual(), order.getDeliverytime());
            shopOrderDtoList.add(shopOrderDto);
        }

        return shopOrderDtoList;
    }

    @Override
    public void test() {
        Session session = sessionFactory.openSession();
        Query userSumQuery = session.createQuery("from Orders where email= :email and state='已送达'");
        userSumQuery.setParameter("email", "498690270@qq.com");
//        String temp = userSumQuery.getQueryString();
        List list = userSumQuery.getResultList();
    }

    /**
     * 检查订单的状态，超时未支付的订单进行取消
     * @param order
     */
    private Orders checkOrderState(Orders order){
        if(order.getState().equals("未支付")){        //对未支付订单进行判断支付超时
            long diff = new Date().getTime() - order.getOrdertime().getTime();      //时间差
            if(diff > TIMEOUT){
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                order.setState("已取消");
                session.update(order);
                transaction.commit();
                session.close();
            }
        }
        else if(order.getState().equals("已支付")){        //对已支付订单判断是否送达
            long diff = new Date().getTime() - order.getPaytime().getTime();      //时间差
            if(diff/MINUTE >= order.getDeliverytime()){     //时间差超过预计时间则送达
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                order.setState("已送达");
                order.setFinishtime(new Timestamp(order.getPaytime().getTime() + order.getDeliverytime()*MINUTE));
                session.update(order);
                transaction.commit();
                session.close();
            }
        }
        return order;
    }

    /**
     * 计算剩余配送时间
     * @param payTime
     * @return
     */
    private int calcRemainDeliveryTime(long payTime, int deliveryTime){
        long diff = new Date().getTime() - payTime;
        int result = (int) (deliveryTime - diff/MINUTE);
        return result;
    }

    /**
     * 计算退订返还的金额
     */
    private double calcReturnMoney(Orders order){
        double result;
        double actual = order.getActual();
        long diff = new Date().getTime() - order.getPaytime().getTime();
        int minute = (int) (diff / MINUTE);
        double percent = (double)minute / order.getDeliverytime();
        if(percent <= 0.1){
            result = actual;
        }
        else if(percent <= 0.3){
            result = actual * 0.8;
        }
        else{
            result = 0;
        }
        return result;
    }

    /**
     * 计算订单的订单项数目
     * @param orderItemList
     * @return
     */
    private int calcSizeOfOrderItems(List<OrderItem> orderItemList){
        int size = 0;
        for(OrderItem orderItem : orderItemList){
            size += orderItem.getNum();
        }
        return size;
    }
}
