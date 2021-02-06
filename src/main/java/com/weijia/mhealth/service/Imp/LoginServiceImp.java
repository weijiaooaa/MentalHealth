package com.weijia.mhealth.service.Imp;

import com.weijia.mhealth.entity.Login;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.mapper.LoginMapper;
import com.weijia.mhealth.service.LoginService;
import com.weijia.mhealth.utils.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 17:31
 * @Version 1.0
 */
@Service
public class LoginServiceImp implements LoginService {

    private final static Logger logger = LoggerFactory.getLogger(LoginServiceImp.class);

    @Autowired
    LoginMapper loginMapper;

    @Override
    public String justLogin(Login login) {
        return loginMapper.justLogin(login);
    }

    @Override
    public Login getLoginFromStu(Student student) {
        Login login = new Login();
        login.setAccountId(student.getId());
        login.setAccountName(student.getName());
        login.setPassword(Md5Util.StringInMd5(student.getPassword()));
        return login;
    }
}
