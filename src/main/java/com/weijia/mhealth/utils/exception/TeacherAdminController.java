package com.weijia.mhealth.utils.exception;

import com.wangxiaoxi.mheal.service.ChatFriendsService;
import com.wangxiaoxi.mheal.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TeacherAdminController {
    @Autowired
    ChatFriendsService chatFriendsService;
    @Autowired
    LoginService loginService;
    @GetMapping("/lk/{username}")
    @ResponseBody
    public R list(@PathVariable("username")String username) {
        String uid = loginService.lkUseridByUsername(username);
        if(uid==null){
            return R.error().message("未查询到此用户");
        }
        return R.ok().data("userinfo",chatFriendsService.LkUserinfoByUserid(uid)).message("用户信息");
    }
}