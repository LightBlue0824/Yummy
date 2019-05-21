package edu.nju.yummy.controller;

import com.alibaba.fastjson.JSON;
import edu.nju.yummy.dto.InfoDto;
import edu.nju.yummy.dto.ModifyShopInfoDto;
import edu.nju.yummy.service.AdminService;
import edu.nju.yummy.util.RememberMeUtil;
import edu.nju.yummy.util.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getLogin(){
        ModelAndView modelAndView = new ModelAndView("adminLogin");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView login(HttpSession session, String password){
        ModelAndView modelAndView;
        String reuslt = adminService.login(password);
        if(reuslt.contains("成功")){
            modelAndView = new ModelAndView("redirect:/admin/index");
            RememberMeUtil.rememberMe(session, "admin", UserType.ADMIN);
        }
        else{
            modelAndView = new ModelAndView("info");
            InfoDto infoDto = new InfoDto("登录失败", reuslt, "");
            modelAndView.addObject("infoDto", infoDto);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView logout(HttpSession session){
        RememberMeUtil.forgetMe(session);
        return new ModelAndView("redirect:/admin/index");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getIndex(){
        ModelAndView modelAndView = new ModelAndView("adminIndex");
        List<ModifyShopInfoDto> modifyShopInfoDtoList = adminService.loadModifyShopInfoList();
        modelAndView.addObject("modifyShopInfoDtoList", modifyShopInfoDtoList);
        return modelAndView;
    }

    @RequestMapping(value = "/approveModifyShopInfo", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView approveModifyShopInfo(int modifyShopInfoId){
        ModelAndView modelAndView = new ModelAndView("info");
        String result = adminService.approveModifyShopInfo(modifyShopInfoId);
        InfoDto infoDto;
        if(result.contains("成功")){
            infoDto = new InfoDto("审批成功", result, "");
        }
        else{
            infoDto = new InfoDto("审批失败", result, "");
        }
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/rejectModifyShopInfo", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView rejectModifyShopInfo(int modifyShopInfoId){
        ModelAndView modelAndView = new ModelAndView("info");
        String result = adminService.rejectModifyShopInfo(modifyShopInfoId);
        InfoDto infoDto;
        if(result.contains("成功")){
            infoDto = new InfoDto("拒绝成功", result, "");
        }
        else{
            infoDto = new InfoDto("拒绝失败", result, "");
        }
        modelAndView.addObject("infoDto", infoDto);
        return modelAndView;
    }

    @RequestMapping(value = "/statistics")
    @ResponseBody
    public ModelAndView getAdminStatistics(){
        ModelAndView modelAndView = new ModelAndView("adminStatistics");
        //数据统计信息
        int shopNum = adminService.loadShopNum();
        modelAndView.addObject("shopNum", shopNum);
        int userNum = adminService.loadUserNum();
        modelAndView.addObject("userNum", userNum);
        double totalInconme = adminService.loadTotalIncome();
        modelAndView.addObject("totalIncome", String.format("%.2f", totalInconme));
        double monthIncome = adminService.loadMonthIncome();
        modelAndView.addObject("monthIncome", String.format("%.2f", monthIncome));
        return modelAndView;
    }

    @RequestMapping(value = "/statisticsChartData", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getStatisticsChartData(){
        Map<String, List> statisticsChartData = adminService.loadStatisticsChartData();
        String result = JSON.toJSONString(statisticsChartData);
        return result;
    }
}
