package edu.nju.yummy.interceptor;

import edu.nju.yummy.util.UserType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShopInterceptor implements HandlerInterceptor {
    //返回值：是否将当前的请求拦截下来
    //如果返回true：请求会被继续执行
    //如果返回false：请求将会被拦截
    //Object org ：表示的是被拦截的请求的目标对象
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object org) throws Exception {
        //根据session查询用户信息，如果用户不为空返回true
        UserType userType = (UserType) request.getSession().getAttribute("type");
        if (userType == UserType.SHOP) {
            return true;
        } else {
            //这里可以写登录超时返回给页面的错误信息。或者跳转页面
            response.sendRedirect("login");
            return false;
        }
    }
}
