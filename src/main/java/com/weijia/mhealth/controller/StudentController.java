package com.weijia.mhealth.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.*;
import com.weijia.mhealth.service.DoctorService;
import com.weijia.mhealth.service.LoginService;
import com.weijia.mhealth.service.QuestionService;
import com.weijia.mhealth.service.RedisService.UserRedisService;
import com.weijia.mhealth.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
    private DoctorService doctorService;

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private QuestionService questionService;
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

    /**
     * 完成注册，跳转到登录页面
     * @param student
     * @return
     */
    @PostMapping(value = "/stu/register")
    @ResponseBody
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
                logger.error(String.valueOf(e.getCause()));
        }

    }

    /**
     * 校验学生身份（id）
     * @return
     */
    @GetMapping(value = "/stu/stuChecked1")
    @ResponseBody
    public Student stuCheckedById(Student student){
        logger.info("student's stuNumber is ->{}",student.getStuNumber());
        String stuNumber = student.getStuNumber();
        return studentService.getStuByStuNumber(stuNumber);
    }

    /**
     * 校验学生身份（id + password）
     * @return
     */
    @GetMapping(value = "/stu/stuChecked")
    @ResponseBody
    public Student stuChecked(String stuNumber,String password){

        logger.info("stuNumber->{},password->{}",stuNumber,password);
        return studentService.stuChecked(stuNumber,password);
    }

    /**
     * 跳转到学生主页
     * @param stuNumber
     * @return
     */
    @GetMapping(value = "/stu/toHomePage")
    public String toHomePage(String stuNumber, HttpServletRequest request){
        logger.info("跳转到学生主页,student->{}",stuNumber);
        Student student = studentService.getStuByStuNumber(stuNumber);

        //往session中存入学生聊天帐号
        Login login = loginService.getLoginFromStu(student);
        logger.info("login from Stu->{}",JSON.toJSON(login) );
        String userid = loginService.justLogin(login);
        request.getSession().setAttribute("userid",userid);
        request.getSession().setAttribute("student",student);

        studentService.updateStudentState(true,student);
        student = studentService.getStuByStuNumber(stuNumber);

        //个人信息注册到redis中
        try{
            if(!userRedisService.isStuExist(student)){
                userRedisService.insertStu(student);
            }
        }catch (Exception e){
            logger.error("学生个人信息存入Redis失败");
        }

        //获取在线医生,先在Redis中去拿，如果找不到，再去数据库拿
        List<Doctor> doctorsOnline = userRedisService.getDoctorsOnline();
        if (doctorsOnline.size() != 0){
            logger.info("Redis中在线医生->{}",JSON.toJSON(doctorsOnline));
            request.setAttribute("doctorsOnline",doctorsOnline);
        }else{
            logger.info("Redis查无结果，即将查询数据库");
            doctorService.getDoctorState(true);
        }

        //获取离线医生
        List<Doctor> doctorsOffline = doctorService.getDoctorState(false);
        logger.info("离线医生->{}",JSON.toJSON(doctorsOffline));
         request.setAttribute("doctorsOffline",doctorsOffline);
        return "stu/home";
    }

    /**
     * 学生注销登录
     * @param id
     * @param request
     * @return
     */
    @GetMapping(value = "/stu/return")
    public String returnPage(Integer id,HttpServletRequest request){
        logger.info("删除redis中学生在线状态id为->{}",id);
        Student stu = studentService.getStuById(id);
        Boolean state = studentService.updateStudentState(false,stu);
        if (state){
            request.getSession().setAttribute("student",null);
            logger.info("学生注销成功！");
            return "redirect:/stu";
        }else{
            logger.error("修改学生状态失败！");
            return "/error";
        }
    }

    /**
     * 跳转到我的问题页面
     * @return
     */
    @GetMapping(value = "/stu/toQuesPage")
    public String toQuesPage(HttpServletRequest request){
        Student student = (Student) request.getSession().getAttribute("student");
        logger.info("student->{}",JSON.toJSON(student));

        List<Question> questions = questionService.getQuestionByStu(student);
        request.setAttribute("questions",questions);
        logger.info("根据stuId查询出的question->{}",JSON.toJSON(questions));
        return "/stu/question";
    }

    /**
     * 提交问题
     * @param question
     * @param request
     * @return
     */
    @PostMapping(value = "/stu/question")
    public String submitQuestion(Question question, HttpServletRequest request){
        logger.info("提交的问题->{}",JSON.toJSON(question));
        Student student = (Student) request.getSession().getAttribute("student");
        try{
            questionService.insertQues(question,student);
        }catch (Exception e){
            logger.error("插入信息失败！");
            return "redirect:/error";
        }
        request.setAttribute("success","success");
        return "redirect:/stu/toQuesPage";
    }

    /**
     * 跳转到问答社区
     * @param request
     * @return
     */
    @GetMapping(value = "/stu/toAnsHood")
    public String toAnsHood(HttpServletRequest request){
        Student student = (Student) request.getSession().getAttribute("student");
        System.out.println("toAnsHood " + student);
//        request.setAttribute("student",student);
        //问题日期
        List<String> dates = questionService. getDates();
        request.setAttribute("dates",dates);

        //标签
        List<Tag> tags = questionService.getTags();
        request.setAttribute("tags",tags);

        return "/stu/answerHood";
    }

    /**
     * 获取对应标签的已回答的问题
     * @param tagId
     * @return
     */
    @GetMapping("/stu/getQuesByTag")
    @ResponseBody
    public List<Question> getQuesByTag(@RequestParam(value="tag") Integer tagId){
        return questionService.getQuestionByTagId(tagId);
    }

    /**
     *跳转到浏览问题详页
     * @param question
     * @return
     */
    @GetMapping(value = "/stu/toViewPage")
    public ModelAndView toAnsPage(Question question){
        logger.info("开始跳转到浏览问题详页");

        ModelAndView modelAndView = new ModelAndView();
        question = questionService.getQuestionByIdV2(question.getId());
        //添加浏览数
        questionService.insertViewCount(question.getId());
        logger.info("question的id->{},查出的question->{}",question.getId(),JSON.toJSON(question));
        String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(question.getGmtCreate()));

        List<AskAndAnswer> answers = question.getAskAndAnsList();

        for (AskAndAnswer answer :answers){
            if (answer.getGmtModified() != null)
                answer.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(answer.getGmtModified())));
        }

        modelAndView.addObject("updateTime",createTime);
        modelAndView.addObject("question",question);
        modelAndView.addObject("student",question.getStudent());

        modelAndView.addObject("askAndAns_s",answers);
        modelAndView.addObject("questionAndTags",question.getQuestionAndTags());
        modelAndView.setViewName("/stu/viewQuestion");

        return modelAndView;
    }

    /**
     * 获取对应日期的已回答的问题
     * @param dateTime
     * @return
     */
    @GetMapping("/stu/getQuesByDate")
    @ResponseBody
    public List<Question> getQuesByDate(String dateTime){

        List<Question> questionByDate = questionService.getQuestionByDate(dateTime);
        logger.info("通过date->{}查询出的question->{}",dateTime,JSON.toJSON(questionByDate));
        return questionByDate;
    }

    /**
     * 跳转到活动页面
     * @return
     */
    @GetMapping(value = "/stu/toActivities")
    public String toActivityPage() {
        return "/stu/activities";
    }

}
