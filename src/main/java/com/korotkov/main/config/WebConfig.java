package com.korotkov.main.config;

import com.korotkov.main.interceptor.PortalInterceptor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;

import java.util.Locale;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = "com.korotkov")
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("/res/");
    }

    @Bean
    ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Autowired
    PortalInterceptor portalInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getLocalChangeInterceptor());
    }

    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor getLocalChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("languageVar");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public CookieLocaleResolver getLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(new Locale("en"));
        cookieLocaleResolver.setCookieMaxAge(100000);
        return cookieLocaleResolver;
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:/locales/messages");
        resource.setCacheSeconds(1);
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/security/login").setViewName("/security/login");
    }

    /*
     *  Java Mail Configuration
     */

    String mailUsername = "volodymyr0korotkov@gmail.com";
    String mailPassword = "q1o72upvsQ";
    String mailHost = "smtp.gmail.com";

    @Bean(name = "mailSender")
    public JavaMailSenderImpl getJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);
        javaMailSender.setPort(587);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.starttls.required",true);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.host",mailHost);
        javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        javaMailProperties.put("mail.smtp.port", "587");

        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean(name = "velocityEngine")
    public VelocityEngineFactoryBean getVelocityEngineFactoryBean(){
        VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
        bean.setResourceLoaderPath("/WEB-INF/email-templates/");
        return bean;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        CommonsMultipartResolver cmr = new CommonsMultipartResolver();
        cmr.setMaxUploadSize(100000000);
        return cmr;
    }

    @SuppressWarnings("deprecation")
    @Bean(name = "xmlViewResolver")
    public XmlViewResolver getXmlViewResolver() {
        XmlViewResolver xmlViewResolver = new XmlViewResolver();
        Resource resource = new ClassPathResource("excel-pdf-config.xml");
        xmlViewResolver.setOrder(0);
        xmlViewResolver.setLocation(resource);
        return xmlViewResolver;
    }

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30000);
        httpRequestFactory.setConnectTimeout(30000);
        httpRequestFactory.setReadTimeout(30000);
        return new RestTemplate(httpRequestFactory);
    }


    @Bean("scoringExecutor")
    public TaskExecutor scoringExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(1000);
        executor.setThreadNamePrefix("scoring-executor-");
        executor.initialize();
        return executor;
    }

}
