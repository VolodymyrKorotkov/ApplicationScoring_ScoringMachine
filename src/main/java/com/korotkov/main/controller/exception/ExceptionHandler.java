package com.korotkov.main.controller.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@ControllerAdvice
public class ExceptionHandler implements HandlerExceptionResolver {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                         Object o, Exception e){
        logger.error("Error log: ", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("500");
        return modelAndView;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
        public ModelAndView handleNoHandlerFoundException(final NoHandlerFoundException exception){
        logger.error("Error log: ", exception);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        return modelAndView;
        }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(final AccessDeniedException exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("403");
        return modelAndView;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public ModelAndView handleAnyException(final Exception exception){
        logger.error("Error log: ", exception);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("500");
        return modelAndView;
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    @org.springframework.web.bind.annotation.ExceptionHandler({DataIntegrityViolationException.class})
    public ModelAndView handleUnexpectedMvcExceptions(Exception exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("406");
        return modelAndView;
    }
}

