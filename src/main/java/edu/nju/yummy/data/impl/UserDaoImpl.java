package edu.nju.yummy.data.impl;

import edu.nju.yummy.data.UserDao;
import edu.nju.yummy.dto.DeliveryAddressDto;
import edu.nju.yummy.dto.ShopListDto;
import edu.nju.yummy.dto.ShopDto;
import edu.nju.yummy.dto.UserInfoDto;
import edu.nju.yummy.model.DeliveryAddress;
import edu.nju.yummy.model.Ebank;
import edu.nju.yummy.model.Shop;
import edu.nju.yummy.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String register(String email, String password) {
        String result;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("from User where email= :email", User.class);
        query.setParameter("email", email);
        if(query.list().size() == 0){
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setLevel(1);
            user.setIsverified(0);
            user.setName("");
            String code = UUID.randomUUID().toString();
            user.setCode(code);
            user.setPhone("");
            session.save(user);

            //分配EBank
            Ebank ebank = new Ebank();
            ebank.setEmail(email);
            session.save(ebank);

            transaction.commit();
            result = code;
        }
        else{
            result = "注册失败，该邮箱已被注册";
        }
        return result;
    }

    @Override
    public String login(String email, String password) {
        String result;

        Session session = sessionFactory.openSession();
        User user = session.find(User.class, email);
        if(user == null){
            result = "登录失败，用户名不存在";
        }
        else if(user.getIsverified() == 0){
            result = "登录失败，请先验证邮箱";
        }
        else if(user.getIsdestroyed() == 1){
            result = "登录失败，该账户已注销";
        }
        else if(user.getPassword().equals(password)){
            result = "登录成功";
        }
        else{
            result = "登录失败，密码错误";
        }

        return result;
    }

    @Override
    public String verify(String code) {
        String result;
        Session session = sessionFactory.openSession();
        Query<User> query = session.createQuery("from User where code= :code");
        query.setParameter("code", code);
        List<User> list = query.getResultList();
        if(list.size() > 0){
            User user = list.get(0);
            user.setIsverified(1);
            user.setCode("");
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            result = "验证成功";
        }
        else{
            result = "验证失败";
        }
        return result;
    }

    @Override
    public String destroy(String email) {
        String result;
        Session session = sessionFactory.openSession();
        User user = session.find(User.class, email);
        if(user != null){
            Transaction transaction = session.beginTransaction();
            user.setIsdestroyed(1);
            session.update(user);
            transaction.commit();
            result = "注销账户成功";
        }
        else{
            result = "注销账户失败, 账户不存在";
        }
        session.close();
        return result;
    }

    /**
     * 加载用户个人信息
     * @param email
     * @return
     */
    @Override
    public UserInfoDto loadUserInfo(String email) {
        UserInfoDto userInfoDto = null;
        Session session = sessionFactory.openSession();
        User user = session.find(User.class, email);
        if(user != null){
            userInfoDto = new UserInfoDto();
            userInfoDto.setEmail(email);
            userInfoDto.setName(user.getName());
            userInfoDto.setPhone(user.getPhone());
            userInfoDto.setLevel(user.getLevel());
            //是否已设置支付密码
            Ebank ebank = session.find(Ebank.class, email);
            if(ebank.getPaypassword() != null){
                userInfoDto.setHasPayPassword(true);
            }
            else{
                userInfoDto.setHasPayPassword(false);
            }
        }
        return userInfoDto;
    }

    /**
     * 修改用户基本信息
     * @param email
     * @param name
     * @param phone
     * @return
     */
    @Override
    public String modifyUserInfo(String email, String name, String phone) {
        String result;
        Session session = sessionFactory.openSession();
        User user = session.find(User.class, email);
        if(user != null){
            Transaction transaction = session.beginTransaction();
            user.setName(name);
            user.setPhone(phone);
            session.update(user);
            transaction.commit();
            result = "修改成功";
        }
        else{
            result = "修改失败";
        }

        return result;
    }

    @Override
    public List<DeliveryAddressDto> loadUserDeliveryAddress(String email) {
        Session session = sessionFactory.openSession();
        Query<DeliveryAddress> query = session.createQuery("from DeliveryAddress where email= :email");
        query.setParameter("email", email);
        List<DeliveryAddress> list = query.getResultList();
        List<DeliveryAddressDto> resultList = new ArrayList<>();
        for(DeliveryAddress temp : list){
            resultList.add(new DeliveryAddressDto(temp.getAddressid(), temp.getName(), temp.getPhone(), temp.getX(), temp.getY()));
        }
        return resultList;
    }

    @Override
    public boolean addUserDeliveryAddress(String email, String name, String phone, double x, double y) {
        Session session = sessionFactory.openSession();
        DeliveryAddress address = new DeliveryAddress(email, name, phone, x, y);
        Transaction transaction = session.beginTransaction();
        session.save(address);
        transaction.commit();

        return true;
    }

    @Override
    public String modifyPayPassword(String email, String oldPassword, String newPassword) {
        String result;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Ebank ebank = session.find(Ebank.class, email);
        String payPassword = ebank.getPaypassword();
        if(payPassword == null || payPassword.equals(oldPassword)){        //新用户初次设定       //旧密码相同
            ebank.setPaypassword(newPassword);
            session.update(ebank);
            transaction.commit();
            result = "修改成功";
        }
        else{
            result = "修改失败，原密码错误";
        }
        session.close();
        return result;
    }

    @Override
    public Ebank getEbank(String email) {
        Session session = sessionFactory.openSession();
        Ebank ebank = session.find(Ebank.class, email);
        session.close();
        return ebank;
    }

    @Override
    public int getUserLevel(String email) {
        int result = -1;
        Session session = sessionFactory.openSession();
        User user = session.find(User.class, email);
        if(user != null){
            result = user.getLevel();
        }
        return result;
    }
}
