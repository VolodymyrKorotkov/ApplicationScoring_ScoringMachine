package com.korotkov.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PortalController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView mainPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/welcome");
        return modelAndView;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/welcome");
        return modelAndView;
    }


    @RequestMapping(value = "/error/403", method = RequestMethod.GET)
    public ModelAndView error403() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/403");
        return modelAndView;
    }

    @RequestMapping(value = "/error/404", method = RequestMethod.GET)
    public ModelAndView error404() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/404");
        return modelAndView;
    }

    @RequestMapping(value = "/error/406", method = RequestMethod.GET)
    public ModelAndView error406() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/406");
        return modelAndView;
    }

    @RequestMapping(value = "/error/500", method = RequestMethod.GET)
    public ModelAndView error500() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/500");
        return modelAndView;
    }

}
