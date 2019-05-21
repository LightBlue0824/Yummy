package edu.nju.yummy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView doGet(HttpSession session){
        ModelAndView modelAndView;

        modelAndView = new ModelAndView("index");

        return modelAndView;
    }
}
