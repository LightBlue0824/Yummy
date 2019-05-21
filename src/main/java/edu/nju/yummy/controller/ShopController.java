package edu.nju.yummy.controller;

import com.alibaba.fastjson.JSON;
import edu.nju.yummy.dto.DishListDto;
import edu.nju.yummy.dto.InfoDto;
import edu.nju.yummy.dto.ShopInfoDto;
import edu.nju.yummy.dto.ShopOrderDto;
import edu.nju.yummy.service.ShopService;
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
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getIndex(HttpSession session, String type) {
        UserType userType = (UserType) session.getAttribute("type");
        if (userType != UserType.SHOP) {
            return new ModelAndView("redirect:/shop/login");
        }

        ModelAndView modelAndView;
        modelAndView = new ModelAndView("shopIndex");
        String shopid = (String) session.getAttribute("id");
        DishListDto dishListDto = shopService.loadShopDishList(shopid, type);
        modelAndView.addObject("dishListDto", dishListDto);
        int modifyInfoState = shopService.getModifyInfoState(shopid);
        modelAndView.addObject("modifyInfoState", modifyInfoState);
        //该店除套餐外所有商品的string list
        List<String> dishList_str = shopService.loadAllDishByStrExceptPackage(Integer.parseInt(shopid));
        modelAndView.addObject("dishList_str", dishList_str);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getLogin() {
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("shopLogin");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(String id, String password, HttpSession session) {
        ModelAndView modelAndView;
        String result = shopService.login(id, password);
        if (result.equals("登录成功")) {
            RememberMeUtil.rememberMe(session, id, UserType.SHOP);
            modelAndView = new ModelAndView("redirect:/shop/index");
        } else {
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
        return new ModelAndView("redirect:/shop/login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getRegister() {
        ModelAndView modelAndView;
        modelAndView = new ModelAndView("shopRegister");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView register(String name, String password, double x, double y, String type, String brief) {
        ModelAndView modelAndView;

        String result = shopService.register(name, password, x, y, type, brief);
        if (result.equals("注册失败")) {
            modelAndView = new ModelAndView("info");
            InfoDto infoDto = new InfoDto("注册失败", "商户注册失败", "");
            modelAndView.addObject("infoDto", infoDto);
        } else {       //成功
            modelAndView = new ModelAndView("info");
            InfoDto infoDto = new InfoDto("注册成功", "商户识别码为：" + result, "该识别码是您商户的标识，用于登录等操作，请牢记！");
            modelAndView.addObject("infoDto", infoDto);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getInfo(HttpSession session) {
        UserType type = (UserType) session.getAttribute("type");
        if (type != UserType.SHOP) {
            return new ModelAndView("redirect:/shop/login");
        }

        ModelAndView modelAndView;
        modelAndView = new ModelAndView("shopInfo");
        String id = (String) session.getAttribute("id");
        ShopInfoDto shopInfoDto = shopService.loadShopInfo(id);
        modelAndView.addObject("shopInfoDto", shopInfoDto);
        return modelAndView;
    }

    /**
     * 修改商户信息
     */
    @RequestMapping(value = "/modifyShopInfo", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView modifyUserInfo(HttpSession session, String name, double x, double y, String type, String brief) {
        ModelAndView modelAndView;

        String id = (String) session.getAttribute("id");
        String result = shopService.modifyShopInfo(id, name, x, y, type, brief);
        modelAndView = new ModelAndView("info");
        InfoDto infoDto = new InfoDto(result, "总经理审批同意即可生效", "");
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/addDish", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addDish(HttpSession session, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, @RequestParam(defaultValue = "-1") double discount){
        ModelAndView modelAndView;
        String shopid = (String) session.getAttribute("id");
        String result = shopService.addDish(shopid, name, type, price, brief, num, isLimited, startTime, endTime, isDiscount, discount);
        InfoDto infoDto;
        if(result.equals("新增商品成功")){
            infoDto = new InfoDto(result, result, "");
        }
        else{
            infoDto = new InfoDto("新增商品失败", result, "请联系管理员");
        }
        modelAndView = new ModelAndView("info");
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    /**
     * 修改商品信息
     */
    @RequestMapping(value = "/modifyDish", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView modifyDish(int dishid, String name, String type, double price, String brief, int num, int isLimited, String startTime, String endTime, int isDiscount, @RequestParam(defaultValue = "-1") double discount){
        ModelAndView modelAndView;
        String result = shopService.modifyDish(dishid, name, type, price, brief, num, isLimited, startTime, endTime, isDiscount, discount);
        InfoDto infoDto;
        if(result.equals("修改成功")){
            infoDto = new InfoDto(result, result, "");
        }
        else{
            infoDto = new InfoDto("修改失败", result, "请联系管理员");
        }
        modelAndView = new ModelAndView("info");
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    /**
     * 新增分类
     */
    @RequestMapping(value = "/addType", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addType(String shopid, String typeName){
        ModelAndView modelAndView;
        String result = shopService.addType(shopid, typeName);
        InfoDto infoDto;
        if(result.equals("新增分类成功")){
            infoDto = new InfoDto(result, result, "");
        }
        else{
            infoDto = new InfoDto("新增分类失败", result, "请联系管理员");
        }
        modelAndView = new ModelAndView("info");
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    /**
     * 统计信息
     */
    @RequestMapping(value = "/statistics")
    @ResponseBody
    public ModelAndView getShopStatistics(HttpSession session, String startTime, String endTime,
                                          @RequestParam(defaultValue = "-1") double minPrice, @RequestParam(defaultValue = "-1") double maxPrice,
                                          @RequestParam(defaultValue = "-1") int userLevel){
        ModelAndView modelAndView = new ModelAndView("shopStatistics");
        int shopid = Integer.parseInt((String) session.getAttribute("id"));
        List<ShopOrderDto> shopOrderDtoList = shopService.loadShopOrderList(shopid, startTime, endTime, minPrice, maxPrice, userLevel);
        modelAndView.addObject("shopOrderDtoList", shopOrderDtoList);
        return modelAndView;
    }

    /**
     * 获取统计图的数据
     */
    @RequestMapping(value = "/statisticsChartData", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getShopStatisticChartData(HttpSession session){
        int shopid = Integer.parseInt((String) session.getAttribute("id"));
        Map<String, List> statisticsChartData = shopService.loadShopStatisticsChartData(shopid);
        String result = JSON.toJSONString(statisticsChartData);
        return result;
    }

}
