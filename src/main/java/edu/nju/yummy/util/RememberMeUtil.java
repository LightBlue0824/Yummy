package edu.nju.yummy.util;

import javax.servlet.http.HttpSession;

public class RememberMeUtil {
    public static void rememberMe(HttpSession session, String id, UserType type){
        session.setAttribute("id", id);
        session.setAttribute("type", type);
    }

    public static void forgetMe(HttpSession session){
        session.removeAttribute("id");
        session.removeAttribute("type");
    }
}
