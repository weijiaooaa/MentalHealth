package com.weijia.mhealth.service.Imp;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.controller.StudentController;
import com.weijia.mhealth.entity.Login;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.entity.UserInfo;
import com.weijia.mhealth.mapper.LoginMapper;
import com.weijia.mhealth.mapper.StudentMapper;
import com.weijia.mhealth.service.RedisService.UserRedisService;
import com.weijia.mhealth.service.StudentService;
import com.weijia.mhealth.utils.Md5Util;
import com.weijia.mhealth.utils.exception.CMSException;
import com.weijia.mhealth.utils.exception.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 21:33
 * @Version 1.0
 */
@Service
public class StudentServiceImp implements StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImp.class);

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    UserRedisService userRedisService;

    @Transactional
    @Override
    public int insertStudentAndLogin(Student student) {
        studentMapper.insertStu(student);

        String stuNumber = student.getStuNumber();
        Integer accountId = studentMapper.selectIdByStuNumber(stuNumber);

        //插入login
        Login login = new Login();
        login.setAccountId(accountId);
        login.setAccountName(student.getName());
        login.setPassword(Md5Util.StringInMd5(student.getPassword()));
        login.setGmtCreate(System.currentTimeMillis());
        logger.info("login is->{}",JSON.toJSON(login));
        loginMapper.insertLogin(login);

        //插入userInfo
        UserInfo userinfo = new UserInfo();
        userinfo.setNickName(student.getName());
        userinfo.setUserId(accountId);
        userinfo.setGmtCreate(System.currentTimeMillis());
        logger.info("userinfo is->{}",JSON.toJSON(userinfo));
        loginMapper.insertUserInfo(userinfo);

        return 1;
    }

    @Override
    public Student stuChecked(String stuNumber, String password) {
        Student student = studentMapper.selectStu(stuNumber,password);
        logger.info("get student is->{}", JSON.toJSON(student));
        return student;
    }

    @Override
    public Student getStuById(Integer id) {
        return studentMapper.getStuById(id);
    }

    @Override
    public Student getStuByStuNumber(String stuNumber) {
        Student student = studentMapper.selectStuByStuNumber(stuNumber);
        logger.info("select student by id is ->{}",JSON.toJSON(student));
        return student;
    }

    @Override
    public Boolean updateStudentState(Boolean state,Student student) {
        //先删除Redis中的在线状态
        try{
            userRedisService.removeStudentByValue(student);
        }catch (Exception e){
            throw new CMSException(ResultCodeEnum.UPDATE_STU_STATE_IN_REDIS);
        }

        Integer id = student.getId();
        //再修改数据库中的状态为离线
        Integer res = studentMapper.updateStudentState(state,id);
        if (res == 0)
            throw new CMSException(ResultCodeEnum.UPDATE_STU_STATE_IN_DB);
        else
            return true;
    }

    @Override
    public Student getStuByQuestionId(Integer questionId) {
        Integer stuId = studentMapper.getStuIdByQuestionId(questionId);
        Student student = studentMapper.selectStuById(stuId);
        return student;
    }
}
