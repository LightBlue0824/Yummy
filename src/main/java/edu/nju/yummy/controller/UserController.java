package edu.nju.yummy.controller;

import com.alibaba.fastjson.JSON;
import edu.nju.yummy.dto.*;
import edu.nju.yummy.model.Cart;
import edu.nju.yummy.service.UserService;
import edu.nju.yummy.util.RememberMeUtil;
import edu.nju.yummy.util.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getLogin(){
        ModelAndView modelAndView;

        modelAndView = new ModelAndView("userLogin");

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(String email, String password, HttpSession session){
        ModelAndView modelAndView;
        String result = userService.login(email, password);
        if(result.equals("登录成功")){
            modelAndView = new ModelAndView("redirect:/user/index");
            RememberMeUtil.rememberMe(session, email, UserType.USER);
        }
        else{
            modelAndView = new ModelAndView("info");
            InfoDto infoDto = new InfoDto("登录失败", result, "");
            modelAndView.addObject("infoDto", infoDto);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView logout(HttpSession session){
        session.removeAttribute("id");
        session.removeAttribute("type");
        return new ModelAndView("redirect:/user/login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRegister(){
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("userRegister");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView register(String email, String password){
        ModelAndView modelAndView;
        String result = userService.register(email, password);
        InfoDto infoDto;
        if(result.equals("注册成功")){
            infoDto = new InfoDto(result, "注册成功，请到邮箱里查看验证邮件", "");
        }
        else{
            infoDto = new InfoDto("注册失败", result, "");
        }
        modelAndView = new ModelAndView("info");
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView verify(String code){
        ModelAndView modelAndView;
        String result = userService.verify(code);
        InfoDto infoDto;
        if(result.equals("验证成功")){
            infoDto = new InfoDto(result, "验证成功，请点击返回主页", "");
        }
        else{
            infoDto = new InfoDto("验证失败", result, "");
        }
        modelAndView = new ModelAndView("info");
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView destroy(HttpSession session){
        ModelAndView modelAndView;
        String email = (String) session.getAttribute("id");
        String result = userService.destroy(email);
        InfoDto infoDto;
        if(result.contains("成功")){
            infoDto = new InfoDto(result, "注销账户成功，请点击返回主页", "");
            RememberMeUtil.forgetMe(session);       //登出
        }
        else{
            infoDto = new InfoDto("注销账户失败", result, "");
        }
        modelAndView = new ModelAndView("info");
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getUserIndex(HttpSession session, String shopType){
        UserType type = (UserType) session.getAttribute("type");
        if(type != UserType.USER){
            return new ModelAndView("redirect:/user/login");
        }

        ModelAndView modelAndView;
        modelAndView = new ModelAndView("userIndex");
        //加载商店列表
        ShopListDto shopListDto = userService.loadShopList(shopType);
        modelAndView.addObject("shopListDto", shopListDto);
        return modelAndView;
    }

    /**
     * 加载用户个人信息
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getUserInfo(HttpSession session){
        UserType type = (UserType) session.getAttribute("type");
        if(type != UserType.USER){
            return new ModelAndView("redirect:/user/login");
        }
        String email = (String) session.getAttribute("id");
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("userInfo");
        //用户基本信息
        UserInfoDto userInfoDto = userService.loadUserInfo(email);
        modelAndView.addObject("userInfoDto", userInfoDto);
        //用户送餐地址
        List<DeliveryAddressDto> addressDtoList = userService.loadUserDeliveryAddress(email);
        modelAndView.addObject("addressDtoList", addressDtoList);
        return modelAndView;
    }

    /**
     * 修改用户个人信息
     */
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView modifyUserInfo(HttpSession session, String name, String phone){
        ModelAndView modelAndView;

        String email = (String) session.getAttribute("id");
        String result = userService.modifyUserInfo(email, name, phone);
        if(result.equals("修改成功")){
            modelAndView = new ModelAndView("redirect:/user/info");
        }
        else{
            InfoDto infoDto = new InfoDto("修改失败", result, "");
            modelAndView = new ModelAndView("info");
            modelAndView.addObject("infoDto", infoDto);
        }
        return modelAndView;
    }

    /**
     * 新增送餐地址
     */
    @RequestMapping(value = "/addDeliveryAddress", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addDeliveryAddress(HttpSession session, String name, String phone, double x, double y){
        ModelAndView modelAndView;

        String email = (String) session.getAttribute("id");
        boolean result = userService.addUserDeliveryAddress(email, name, phone, x, y);
        if(result){
            modelAndView = new ModelAndView("redirect:/user/info");
        }
        else{
            InfoDto infoDto = new InfoDto("新增失败", "新增送餐地址失败", "");
            modelAndView = new ModelAndView("info");
            modelAndView.addObject("infoDto", infoDto);
        }

        return modelAndView;
    }

    /**
     * 修改支付密码
     */
    @RequestMapping(value = "/modifyPayPassword", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView modifyPayPassword(HttpSession session, String oldPassword, String newPassword){
        ModelAndView modelAndView;
        String email = (String) session.getAttribute("id");
        String result = userService.modifyPayPassword(email, oldPassword, newPassword);
        modelAndView = new ModelAndView("info");
        InfoDto infoDto;
        if(result.contains("成功")){
            infoDto = new InfoDto("修改支付密码成功", result, "");
        }
        else{
            infoDto = new InfoDto("修改支付密码失败", result, "");
        }
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/shopDetail", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getShopDetail(HttpSession session, @RequestParam(defaultValue = "-1") int shopid, String dishType){
        ModelAndView modelAndView;

        UserType type = (UserType) session.getAttribute("type");
        if(type != UserType.USER){
            return new ModelAndView("redirect:/user/login");
        }
        else if(shopid == -1){
            return new ModelAndView("redirect:/user/index");
        }

        modelAndView = new ModelAndView("shopDetail");
        ShopDetailDto shopDetailDto = userService.loadShopDetail(shopid, dishType);
        modelAndView.addObject("shopDetailDto", shopDetailDto);
        return modelAndView;
    }

    @RequestMapping(value = "/addDishToCart", method = RequestMethod.POST)
    @ResponseBody
    public String addDishToCart(HttpSession session, int shopid, int dishid){
        String result;
        Cart cart = (Cart) session.getAttribute("cart");
        cart = userService.addDishToCart(cart, shopid, dishid);
        session.setAttribute("cart", cart);
        result = "{success: 'true'}";
        return result;
    }

    @RequestMapping(value = "/cartDetail", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getCartDetail(HttpSession session, int shopid, @RequestParam(defaultValue = "-1") int addressid){
        UserType type = (UserType) session.getAttribute("type");
        if(type != UserType.USER){
            return new ModelAndView("redirect:/user/login");
        }

        ModelAndView modelAndView;
        modelAndView = new ModelAndView("cartDetail");
        Cart cart = (Cart) session.getAttribute("cart");
        String email = (String) session.getAttribute("id");
        CartDto cartDto = userService.loadCartDetail(cart, shopid, email, addressid);
        modelAndView.addObject("cartDto", cartDto);
        return modelAndView;
    }

    @RequestMapping(value = "/addDishNumOfCart", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addDishNumOfCart(HttpSession session, int shopid, int dishid){
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("redirect:/user/cartDetail?shopid="+shopid);
        Cart cart = (Cart) session.getAttribute("cart");
        cart = userService.addDishToCart(cart, shopid, dishid);
        session.setAttribute("cart", cart);
        return modelAndView;
    }

    @RequestMapping(value = "/subDishNumOfCart", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView subDishNumOfCart(HttpSession session, int shopid, int dishid){
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("redirect:/user/cartDetail?shopid="+shopid);
        Cart cart = (Cart) session.getAttribute("cart");
        cart = userService.subDishToCart(cart, shopid, dishid);
        session.setAttribute("cart", cart);
        return modelAndView;
    }

    @RequestMapping(value = "/delDishOfCart", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView delDishOfCart(HttpSession session, int shopid, int dishid){
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("redirect:/user/cartDetail?shopid="+shopid);
        Cart cart = (Cart) session.getAttribute("cart");
        cart = userService.delDishToCart(cart, shopid, dishid);
        session.setAttribute("cart", cart);
        return modelAndView;
    }

    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addOrder(HttpSession session, int shopid, String address, int deliveryTime){
        ModelAndView modelAndView;
        Cart cart = (Cart) session.getAttribute("cart");
        String email = (String) session.getAttribute("id");
        String result = userService.addOrder(email, cart, shopid, address, deliveryTime);
        if(result.contains("失败")){
            modelAndView = new ModelAndView("info");
            InfoDto infoDto = new InfoDto("下订单失败", result, "");
            modelAndView.addObject("infoDto", infoDto);
        }
        else{           //成功
            modelAndView = new ModelAndView("redirect:/user/orderDetail?orderid="+result);
            cart.delItemOfThisShop(shopid);         //清空该店购物车
            session.setAttribute("cart", cart);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getOrderDetail(HttpSession session, int orderid) {
        UserType type = (UserType) session.getAttribute("type");
        if(type != UserType.USER){
            return new ModelAndView("redirect:/user/login");
        }

        ModelAndView modelAndView;
        OrderDetailDto orderDetailDto = userService.loadOrderDetail(orderid);
        //不能查看其它人的订单详情
        if(!(session.getAttribute("id")).equals(orderDetailDto.getEmail())){
             return new ModelAndView("redirect:/user/index");
        }
        modelAndView = new ModelAndView("orderDetail");
        modelAndView.addObject("orderDetailDto", orderDetailDto);
        return modelAndView;
    }

    @RequestMapping(value = "/getEBankBalance", method = RequestMethod.GET)
    @ResponseBody
    public String getEBankBalance(HttpSession session){
        String email = (String) session.getAttribute("id");
        double balance = userService.getEBankBalance(email);
        return "{\"balance\": \""+balance+"\"}";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView pay(HttpSession session, int orderid, String payPassword){
        ModelAndView modelAndView;
        String email = (String) session.getAttribute("id");
        String result = userService.pay(email, orderid, payPassword);
        modelAndView = new ModelAndView("info");
        if(result.contains("成功")){
            InfoDto infoDto = new InfoDto("支付成功", result, "请等待商品送达");
            modelAndView.addObject("infoDto", infoDto);
        }
        else{           //失败
            InfoDto infoDto = new InfoDto("支付失败", result, "");
            modelAndView.addObject("infoDto", infoDto);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView cancelOrder(HttpSession session, int orderid){
        ModelAndView modelAndView;
        String email = (String) session.getAttribute("id");
        String result = userService.cancelOrder(email, orderid);
        modelAndView = new ModelAndView("info");
        InfoDto infoDto;
        if(result.contains("成功")){
            infoDto = new InfoDto("取消订单成功", result, "");
        }
        else{
            infoDto = new InfoDto("取消订单失败", result, "");
        }
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getUserOrder(HttpSession session){
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("userOrder");
        String email = (String) session.getAttribute("id");
        List<UserOrderDto> userOrderDtoList = userService.loadUserOrders(email);
        modelAndView.addObject("userOrderDtoList", userOrderDtoList);
        return modelAndView;
    }

    @RequestMapping(value = "/sureToFinishOrder", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView sureToFinishOrder(HttpSession session, int orderid){
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("info");
        String email = (String) session.getAttribute("id");
        String result = userService.sureToFinishOrder(email, orderid);
        InfoDto infoDto;
        if(result.contains("成功")){
            infoDto = new InfoDto(result, result, "订单已送达, 感谢使用Yummy! 请慢用");
        }
        else{
            infoDto = new InfoDto("确认送达失败", result, "请稍后再试");
        }
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/statistics")
    @ResponseBody
    public ModelAndView getUserStatistics(HttpSession session,String orderType, String startTime, String endTime, @RequestParam(defaultValue = "-1") double minPrice,
                                          @RequestParam(defaultValue = "-1") double maxPrice, String shopType){
        ModelAndView modelAndView = new ModelAndView("userStatistics");
        String email = (String) session.getAttribute("id");
        List<UserOrderDto> userOrderDtoList = userService.loadUserStatisticsDetail(email, orderType, startTime, endTime, minPrice, maxPrice, shopType);
        modelAndView.addObject("userOrderDtoList", userOrderDtoList);
        Map<String, List> statisticsData = userService.loadUserStatisticsChartData(email);
        return modelAndView;
    }

    @RequestMapping(value = "/statisticsChartData", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getStatisticsChartData(HttpSession session){
        Map<String, List> statisticsChartData = userService.loadUserStatisticsChartData((String) session.getAttribute("id"));
        String result = JSON.toJSONString(statisticsChartData);
        return result;
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(){
        userService.test();
        return "test";
    }
}
