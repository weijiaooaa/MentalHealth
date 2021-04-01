package com.weijia.mhealth.service.Imp;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Login;
import com.weijia.mhealth.entity.Question;
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

import java.util.List;

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

    @Override
    public PageInfo<Student> getAllStudent(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Student> pageInfo = getPageInfo(pageNum, pageSize);
        return pageInfo;
    }

    @Override
    public PageInfo<Student> getStuPage(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Student> pageInfo = getPageInfoV2(pageNum, pageSize);
        return pageInfo;
    }

    private PageInfo<Student> getPageInfoV2(Integer pageNum, Integer pageSize) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Student> students = studentMapper.getStuPage();
            PageInfo<Student> pageInfo = new PageInfo<>(students, pageSize);
            pageInfo.setList(students);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    private PageInfo<Student> getPageInfo(Integer pageNum, Integer pageSize) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Student> students = studentMapper.getAllStudent();
            PageInfo<Student> pageInfo = new PageInfo<>(students, pageSize);
            pageInfo.setList(students);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }
}
