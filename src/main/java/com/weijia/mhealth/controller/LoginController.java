package com.weijia.mhealth.controller;

import com.weijia.mhealth.entity.Login;
import com.weijia.mhealth.service.LoginService;
import com.weijia.mhealth.utils.Md5Util;
import com.weijia.mhealth.utils.exception.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 17:00
 * @Version 1.0
 */
@Controller
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService loginService;
    @GetMapping("/")
    public String tologin(){
        return "user/login";
    }
    /**
     * 登陆
     * */
    @PostMapping("/justlogin")
    @ResponseBody
    public R login(@RequestBody Login login, HttpSession session){
        login.setPassword(Md5Util.StringInMd5(login.getPassword()));
        String userId = loginService.justLogin(login);
        if(userId==null){
            return R.error().message("账号或者密码错误");
        }
        session.setAttribute("userId",Integer.valueOf(userId));
        return R.ok().message("登录成功");
    }

}
