package edu.nju.yummy.data.impl;

import edu.nju.yummy.data.ShopDao;
import edu.nju.yummy.dto.*;
import edu.nju.yummy.model.Dish;
import edu.nju.yummy.model.DishType;
import edu.nju.yummy.model.ModifyShopInfo;
import edu.nju.yummy.model.Shop;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ShopDaoImpl implements ShopDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Shop getShop(int id) {
        Session session = sessionFactory.openSession();
        Shop shop = session.find(Shop.class, id);
        session.close();
        return shop;
    }

    @Override
    public void updateShop(Shop shop) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(shop);
        transaction.commit();
        session.close();
    }

    /**
     * 注册商户
     * @param name
     * @param password
     * @param x
     * @param y
     * @param type
     * @return 成功时返回id，失败返回"注册失败"
     */
    @Override
    public String register(String name, String password, double x, double y, String type, String brief) {
        String result;

        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select max(s.shopid) from Shop as s");
        Integer id = (Integer) query.uniqueResult();
        id = id == null ? 1 : id+1;
        Shop shop = new Shop();
        shop.setShopid(id);
        shop.setName(name);
        shop.setPassword(password);
        shop.setX(x);
        shop.setY(y);
        shop.setType(type);
        shop.setBrief(brief);
        Transaction transaction = session.beginTransaction();
        session.save(shop);
        //生成一个默认的套餐分类
        DishType type_combo = new DishType(id, "套餐");
        session.save(type_combo);
        transaction.commit();

        if(id != null){
            result = shopIdToString(id);
        }
        else{
            result = "注册失败";
        }
        return result;
    }

    @Override
    public String login(String id, String password) {
        String result;
        Session session = sessionFactory.openSession();
        Integer shopid = Integer.parseInt(id);
        Shop shop = session.find(Shop.class, shopid);
        if(shop != null){
            if(shop.getPassword().equals(password)){
                result = "登录成功";
            }
            else{
                result = "登录失败，密码错误";
            }
        }
        else{
            result = "登录失败，用户名不存在";
        }
        return result;
    }

    @Override
    public ShopInfoDto loadShopInfo(String id) {
        ShopInfoDto shopInfoDto;
        Session session = sessionFactory.openSession();
        Integer shopid = Integer.parseInt(id);      //存储形式为补0的int
        Shop shop = session.find(Shop.class, shopid);
        if(shop != null){
            shopInfoDto = new ShopInfoDto(id, shop.getName(), shop.getX(), shop.getY(), shop.getType(), shop.getBrief());

            Query<ModifyShopInfo> modifyQuery = session.createQuery("from ModifyShopInfo where shopid= :shopid and isapproved= 0");
            modifyQuery.setParameter("shopid", Integer.parseInt(id));
            List<ModifyShopInfo> modifyShopInfoList = modifyQuery.getResultList();
            //判断是否在审批信息
            if(modifyShopInfoList.size() != 0){
                ModifyShopInfo modifyShopInfo = modifyShopInfoList.get(0);
                ModifyShopInfoDto modifyShopInfoDto = new ModifyShopInfoDto(modifyShopInfo.getId(), String.valueOf(modifyShopInfo.getShopid()), modifyShopInfo.getTime().toString(), modifyShopInfo.getName(),
                        modifyShopInfo.getX(), modifyShopInfo.getY(), modifyShopInfo.getType(), modifyShopInfo.getBrief(), modifyShopInfo.getIsapproved());
                shopInfoDto.setModifyShopInfoDto(modifyShopInfoDto);
            }
        }
        else{
            shopInfoDto = new ShopInfoDto(id, "获取商户信息失败", -1, -1, "", "");
        }

        return shopInfoDto;
    }

    @Override
    public String modifyShopInfo(String id, String name, double x, double y, String type, String brief) {
        String result;
        Session session = sessionFactory.openSession();
        Integer shopid = Integer.parseInt(id);

        //增长modifyShopInfo的id
        Query query = session.createQuery("select max(m.id) from ModifyShopInfo as m");
        Integer mid = (Integer) query.uniqueResult();
        mid = mid == null ? 1 : mid+1;

        Timestamp now = new Timestamp(new java.util.Date().getTime());
        ModifyShopInfo modifyShopInfo = new ModifyShopInfo(mid, now, shopid, name, x, y, type, brief);
        Transaction transaction = session.beginTransaction();
        session.save(modifyShopInfo);
        transaction.commit();

        result = "提交成功";
        return result;
    }

    @Override
    public ModifyShopInfo getModifyShopInfo(int modifyShopInfoId) {
        Session session = sessionFactory.openSession();
        ModifyShopInfo modifyShopInfo = session.find(ModifyShopInfo.class, modifyShopInfoId);
        session.close();
        return modifyShopInfo;
    }

    @Override
    public DishListDto loadShopDishList(String shopid, String type) {
        DishListDto dishListDto;
        Session session = sessionFactory.openSession();
        Query<String> queryType = session.createQuery("select type from DishType where shopid= :shopid");
        queryType.setParameter("shopid", Integer.parseInt(shopid));
        List<String> typeList = queryType.getResultList();
        Query<Dish> queryDish;
        if(type == null){          //全部
            queryDish = session.createQuery("from Dish where shopid= :shopid");
            queryDish.setParameter("shopid", Integer.parseInt(shopid));
        }
        else{               //指定type
            queryDish = session.createQuery("from Dish where shopid= :shopid and type= :type");
            queryDish.setParameter("shopid", Integer.parseInt(shopid));
            queryDish.setParameter("type", type);
        }
        List<Dish> dishList = queryDish.getResultList();
        dishListDto = new DishListDto(typeList, dishList, type);
        return dishListDto;
    }

    @Override
    public String modifyDish(int dishid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount) {
        String result;
        Session session = sessionFactory.openSession();

        Dish dish = session.find(Dish.class, dishid);
        dish.setDishid(dishid);     dish.setName(name);     dish.setType(type);
        dish.setPrice(price);       dish.setBrief(brief);       dish.setNum(num);
        dish.setIslimited(isLimited);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(isLimited == 1){
            try {
                dish.setStarttime(new Date(simpleDateFormat.parse(startTime).getTime()));
                dish.setEndtime(new Date(simpleDateFormat.parse(endTime).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                result = "修改失败，日期错误";
                return result;
            }
        }
        else{
            dish.setStarttime(null);
            dish.setEndtime(null);
        }
        if(isDiscount == 1){
            dish.setActual(discount);
        }
        else{
            dish.setActual(-1.0);
        }
        Transaction transaction = session.beginTransaction();
        session.update(dish);
        transaction.commit();
        result = "修改成功";

        return result;
    }

    @Override
    public String addDish(String shopid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, double discount) {
        String result;
        Session session = sessionFactory.openSession();

        Dish dish = new Dish(Integer.parseInt(shopid), name, type, price, brief, num, isLimited);
        if(isLimited == 1){
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dish.setStarttime(new Date(simpleDateFormat.parse(startTime).getTime()));
                dish.setEndtime(new Date(simpleDateFormat.parse(endTime).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                result = "新增商品失败，日期错误";
                return result;
            }
        }
        else{
            dish.setStarttime(null);
            dish.setEndtime(null);
        }
        if(isDiscount == 1){
            dish.setActual(discount);
        }
        Transaction transaction = session.beginTransaction();
        if(session.save(dish) != null){
            transaction.commit();
            result = "新增商品成功";
        }
        else{
            result = "新增商品失败";
        }

        return result;
    }

    @Override
    public String addType(String shopid, String typeName) {
        String result;
        Session session = sessionFactory.openSession();

        DishType dishType = new DishType(Integer.parseInt(shopid), typeName);
        Transaction transaction = session.beginTransaction();
        if(session.save(dishType) != null){
            transaction.commit();
            result = "新增分类成功";
        }
        else{
            result = "新增分类失败";
        }

        return result;
    }

    @Override
    public ShopListDto loadShopList(String shopType) {
        ShopListDto shopListDto;
        Session session = sessionFactory.openSession();
        Query<Shop> shopQuery;
        if(shopType == null){       //全部
            shopQuery = session.createQuery("from Shop");
        }
        else{
            shopQuery = session.createQuery("from Shop where type= :shopType");
            shopQuery.setParameter("shopType", shopType);
        }
        List<Shop> shopList = shopQuery.getResultList();
        List<ShopDto> shopDtoList = new ArrayList<>();
        for(Shop shop : shopList){          //转换为传输shop对象
            ShopDto shopDto = new ShopDto(shop.getShopid(), shop.getName(), shop.getX(), shop.getY(), shop.getType(), shop.getBrief());
            shopDtoList.add(shopDto);
        }
        shopListDto = new ShopListDto(shopType, shopDtoList);
        return shopListDto;
    }

    @Override
    public Dish getDish(int dishid) {
        Session session = sessionFactory.openSession();
        Dish dish = session.find(Dish.class, dishid);
        return dish;
    }

    @Override
    public int getModifyInfoState(String shopid) {
        int result = 1;
        Session session = sessionFactory.openSession();
        Query<ModifyShopInfo> modifyQuery = session.createQuery("from ModifyShopInfo where shopid= :shopid and isapproved= 0");
        modifyQuery.setParameter("shopid", Integer.parseInt(shopid));
        List<ModifyShopInfo> modifyShopInfoList = modifyQuery.getResultList();
        //判断是否在审批信息
        if(modifyShopInfoList.size() != 0){
            result = 0;
        }
        return result;
    }

    @Override
    public List<ModifyShopInfoDto> loadModifyShopInfoList() {
        Session session = sessionFactory.openSession();
        List<ModifyShopInfoDto> modifyShopInfoDtoList = new ArrayList<>();
        Query<ModifyShopInfo> query = session.createQuery("from ModifyShopInfo where isapproved=0 order by time desc ");
        List<ModifyShopInfo> modifyShopInfoList = query.getResultList();
        for(ModifyShopInfo modifyShopInfo : modifyShopInfoList){
            ModifyShopInfoDto temp = new ModifyShopInfoDto(modifyShopInfo.getId(), modifyShopInfo.getTime().toString(),
                    shopIdToString(modifyShopInfo.getShopid()), modifyShopInfo.getName(), modifyShopInfo.getX(),
                    modifyShopInfo.getY(), modifyShopInfo.getType(), modifyShopInfo.getBrief(), modifyShopInfo.getIsapproved());
            modifyShopInfoDtoList.add(temp);
        }
        return modifyShopInfoDtoList;
    }

    @Override
    public List<String> loadAllDishByStrExceptPackage(int shopid) {
        Session session = sessionFactory.openSession();
        Query<String> dishQuery = session.createQuery("select name from Dish where shopid= :shopid and type <> '套餐'");
        dishQuery.setParameter("shopid", shopid);
        List<String> dishList = dishQuery.getResultList();
        if(dishList == null)    dishList = new ArrayList<>();
        return dishList;
    }

    /**
     * 转换int的shopid为补0的String
     * @param shopid
     * @return
     */
    public String shopIdToString(int shopid){
        String result = String.valueOf(shopid);
        for(int i = 7-result.length(); i > 0; i--){     //补0
            result = '0' + result;
        }
        return result;
    }
}
