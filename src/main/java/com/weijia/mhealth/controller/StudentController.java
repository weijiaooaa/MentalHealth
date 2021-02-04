package com.weijia.mhealth.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.entity.Login;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.service.LoginService;
import com.weijia.mhealth.service.RedisService.UserRedisService;
import com.weijia.mhealth.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 20:54
 * @Version 1.0
 */
@Controller
public class StudentController {

    private final static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRedisService userRedisService;
    /**
     * 跳转到登录页面
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/stu")
    public String loginPage(HttpServletRequest httpServletRequest){
        String s = httpServletRequest.getParameter("success");
        httpServletRequest.setAttribute("success",s);
        return "/user/sign-in-stu";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @GetMapping(value = "/stu/toRegister")
    public String registerPage(){
        return "/user/sign-up-stu";
    }

//    /**
//     * 重定向到注册页面
//     * @return
//     */
//    @GetMapping(value = "/stu/toReg")
//    public String toRegisterPage(){
//        return "redirect:/stu/toRegister";
//    }

    /**
     * 完成注册，跳转到登录页面
     * @param student
     * @return
     */
    @PostMapping(value = "/stu/register")
    public void register(Student student){
        logger.info("student->{}", JSON.toJSON(student));

        student.setGmtCreate(System.currentTimeMillis());
        try{
            //插入学生登录帐号和聊天帐号
            int res = studentService.insertStudentAndLogin(student);
            if (res != 0){
                logger.info("成功插入数据，res->{}",res);
            }
        }catch (Exception e){
                logger.error("插入数据失败");
        }

    }

    /**
     * 校验学生身份（id）
     * @return
     */
    @GetMapping(value = "/stu/stuChecked1")
    @ResponseBody
    public Student stuCheckedById(Student student){
        logger.info("student's id is ->{}",student.getId());
        Integer id = student.getId();
        return studentService.getStuById(id);
    }

    /**
     * 校验学生身份（id + password）
     * @return
     */
    @GetMapping(value = "/stu/stuChecked")
    @ResponseBody
    public Student stuChecked(Student student){
        Integer id = student.getId();
        String password = student.getPassword();
        logger.info("id->{},password->{}",id,password);
        return studentService.stuChecked(id,password);
    }

    /**
     * 跳转到学生主页
     * @param student
     * @return
     */
    @GetMapping(value = "/stu/toHomePage")
    public String toHomePage(Student student, HttpServletRequest request){
        logger.info("跳转到学生主页,student->{}",JSONUtils.toJSONString(student));
        student = studentService.getStuById(student.getId());

        //往session中存入学生聊天帐号
        Login login = loginService.getLoginFromStu(student);
        logger.info("login from Stu->{}",JSONUtils.toJSONString(login) );
        String userid = loginService.justLogin(login);
        request.getSession().setAttribute("userid",userid);
        request.getSession().setAttribute("student",student);

        //个人信息注册到redis中
        try{
            if(!userRedisService.isStuExist(student.getId())){
                userRedisService.insertStu(student.getId());
            }
        }catch (Exception e){
            logger.error("个人信息存入Redis失败");
        }


        //获取在线医生
        List<Doctor> doctorsOnline = userRedisService.getDoctorsOnline();
        logger.info("在线医生->{}",JSON.toJSON(doctorsOnline));
        request.setAttribute("doctorsOnline",doctorsOnline);

        //获取离线医生
        List<Doctor> doctorsOffline = userRedisService.getDoctorsOffline();
        logger.info("在线医生->{}",JSON.toJSON(doctorsOnline));
        request.setAttribute("doctorsOffline",doctorsOffline);

        return "/stu/home";
    }

}
