package com.korotkov.main.controller.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InterceptorController {
    
    @RequestMapping(value = "/interceptorCall/subLevel", method = RequestMethod.GET)
    public ModelAndView interceptorCall(){
        return new ModelAndView("security/login");
    }
}
