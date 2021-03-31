package com.weijia.mhealth.service;

import com.weijia.mhealth.entity.Login;
import com.weijia.mhealth.entity.Student;
import org.springframework.stereotype.Service;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 17:25
 * @Version 1.0
 */
@Service
public interface LoginService {

    String justLogin(Login login);

    Login getLoginFromStu(Student student);

    String lkUseridByUsername(String username);

}
