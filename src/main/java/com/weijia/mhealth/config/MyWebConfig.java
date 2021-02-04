package com.weijia.mhealth.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new CorsFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    //配置一个静态文件的路径 否则css和js无法使用，虽然默认的静态资源是放在static下，但是没有配置里面的文件夹
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new PreHandlerInterceptor());
        //设置拦截页面,只允许学生，医生，管理员登录和注册
//        registration.addPathPatterns("/chat/*");
//        registration.addPathPatterns("/stu/*");
//        registration.addPathPatterns("/doctor/*");
//        registration.addPathPatterns("/admin/*");
//        registration.addPathPatterns("/question/*");

        //允许访问的静态资源
        registration.excludePathPatterns("/assets/**");
        registration.excludePathPatterns("/doc/**");

        //允许访问的controller
        registration.excludePathPatterns("/stu","/doctor","/admin");
        registration.excludePathPatterns("/stu1","/doctor1","/admin1");
        registration.excludePathPatterns("/stu/toReg","/doctor/toReg","/admin/toReg");
        registration.excludePathPatterns("/stu/register","/doctor/register","/admin/register");
        registration.excludePathPatterns("/stu/stuChecked","/doctor/doctorChecked","/admin/adminChecked");
        registration.excludePathPatterns("/stu/stuChecked1","/doctor/doctorChecked1","/admin/adminChecked1");
        registration.excludePathPatterns("/stu/toHomePage","/doctor/toHomePage","/admin/toHomePage");
    }
}