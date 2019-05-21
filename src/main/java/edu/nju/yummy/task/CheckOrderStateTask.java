package edu.nju.yummy.task;

import edu.nju.yummy.data.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckOrderStateTask {
    @Autowired
    private OrderDao orderDao;

    @Scheduled(cron = "0 0/1 * * * ?")      //每1分钟检查一次
    public void checkOrderStateTask() {
//        System.out.println("执行task");
        orderDao.checkAllOrderState();
    }
}
