package com.weijia.mhealth.controller;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.service.DoctorService;
import com.weijia.mhealth.service.RedisService.UserRedisService;
import com.weijia.mhealth.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/23 21:06
 * @Version 1.0
 */
@Controller
public class DoctorController {
    private final static Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    DoctorService doctorService;

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private StudentService studentService;

    /**
     * 医生登录页面
     * @return
     */
    @GetMapping("/doctor")
    public String loginPage(){
        return "/user/sign-in-doctor";
    }

    /**
     * 医生注册页面
     * @return
     */
    @GetMapping("/doctor/toRegister")
    public String registerPage(){
        return "/user/sign-up-doctor";
    }

    /**
     * 校验医生身份（id）
     * @return
     */
    @GetMapping(value = "/doctor/doctorChecked1")
    @ResponseBody
    public Doctor stuChecked1(String doctorNumber){
        logger.info("前端传来的doctor参数->{}",doctorNumber);
        return doctorService.getStuByStuNumber(doctorNumber);
    }

    /**
     * 校验医生身份（id + password）
     * @return
     */
    @GetMapping(value = "/doctor/doctorChecked")
    @ResponseBody
    public Doctor stuChecked(String doctorNumber,String password){

        return doctorService.doctorChecked(doctorNumber,password);
    }

    /**
     * 完成注册，跳转到登录页面
     * @param doctor
     * @return
     */
    @PostMapping(value = "/doctor/register")
    public void register(Doctor doctor){
        logger.info("前端登录传的doctor->{}",JSON.toJSON(doctor));
        doctor.setGmtCreate(System.currentTimeMillis());
        doctorService.insertDoctor(doctor);
    }

    @GetMapping(value = "/doctor/toHomePage")
    public String toHomePage(String doctorNumber,HttpServletRequest request){
        logger.info("跳转到医生主页,doctorNumber->{}",doctorNumber);
        Doctor doctor = doctorService.getStuByStuNumber(doctorNumber);
        request.getSession().setAttribute("doctor",doctor);
        request.getSession().setAttribute("userid",doctor.getId());

        doctorService.updateDoctorState(true,doctor);
        doctor = doctorService.getStuByStuNumber(doctorNumber);

        //个人信息注册到redis中
        try{
            if(!userRedisService.isDoctorExist(doctor)){
                userRedisService.insertDoctor(doctor);
            }
        }catch (Exception e){
            logger.error("医生个人信息存入Redis失败");
        }


        //获取在线学生,先在Redis中去拿，如果找不到，再去数据库拿
        List<Student> studentsOnline = userRedisService.getStudentsOnline();
        if (studentsOnline.size() != 0){
            logger.info("Redis中在线学生->{}",JSON.toJSON(studentsOnline));
            request.setAttribute("studentsOnline",studentsOnline);
        }else{
            logger.info("Redis查无结果，即将查询数据库");
            studentsOnline = doctorService.getStuState(true);
            request.setAttribute("studentsOnline",studentsOnline);
        }

        //获取离线学生
        List<Student> studentsOffline = doctorService.getStuState(false);
        logger.info("离线学生->{}",JSON.toJSON(studentsOffline));
        request.setAttribute("studentsOffline",studentsOffline);

        return "/doctor/home";
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @GetMapping(value = "/doctor/return")
    public String returnPage(HttpServletRequest request){
        request.getSession().setAttribute("doctor",null);
        return "redirect:/doctor";
    }

    @GetMapping(value = "/doctor/toChatPage")
    public String toChatPage(HttpServletRequest request){
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");

        //获取聊天学生id 和 图像编号
        String stuId = request.getParameter("studentId");
        request.setAttribute("student",studentService.getStuById(Integer.valueOf(stuId)));
        request.setAttribute("iconNum",request.getParameter("iconNum"));

        return "/chat/chats";
    }
}
