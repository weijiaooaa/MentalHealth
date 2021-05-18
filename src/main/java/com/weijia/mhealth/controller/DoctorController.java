package com.weijia.mhealth.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.*;
import com.weijia.mhealth.service.DoctorService;
import com.weijia.mhealth.service.QuestionService;
import com.weijia.mhealth.service.RedisService.UserRedisService;
import com.weijia.mhealth.service.StudentService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

    @Autowired
    private QuestionService questionService;

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
    public String toHomePage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "4",value = "pageSize") Integer pageSize,
                             String doctorNumber,HttpServletRequest request,Model model){
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

        PageInfo<Student> studentPageInfo = studentService.getStuPage(pageNum,pageSize);
        logger.info("首页医生列表分页->{}",JSON.toJSON(studentPageInfo));

        model.addAttribute("studentPageInfo",studentPageInfo);

        return "/doctor/home";
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @GetMapping(value = "/doctor/return")
    public String returnPage(Integer id,HttpServletRequest request){
        logger.info("删除redis中医生在线状态id为->{}",id);
        Doctor doctor = doctorService.getDoctorById(id);
        Boolean state = doctorService.updateDoctorState(false,doctor);
        if (state){
            request.getSession().setAttribute("doctor",null);
            logger.info("医生注销成功！");
            return "redirect:/doctor";
        }else{
            logger.error("修改医生状态失败！");
            return "/error";
        }
    }

    /**
     * 跳转到在线聊天页面
     * @param request
     * @return
     */
    @GetMapping(value = "/doctor/toChatPage")
    public String toChatPage(HttpServletRequest request){
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");

        //获取聊天学生id 和 图像编号
        String stuId = request.getParameter("studentId");
        request.setAttribute("student",studentService.getStuById(Integer.valueOf(stuId)));
        request.setAttribute("iconNum",request.getParameter("iconNum"));

        return "/chat/chats";
    }

    /**
     * 跳转到我的回答页面
     * @return
     */
    @GetMapping(value = "/doctor/toContactPage")
    public String getAllQuestion(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "8",value = "pageSize") Integer pageSize,HttpServletRequest request, Model model){
        Doctor doctor = (Doctor)request.getSession().getAttribute("doctor");
        PageInfo<Question> allQuestion = questionService.getQuestionByDoctorId(pageNum, pageSize,doctor.getId());
        logger.info("分页查出来的question->{}",JSON.toJSON(allQuestion));
        model.addAttribute("pageInfo",allQuestion);
        return "/doctor/contact";
    }

    /**
     * 跳转到问题社区页面
     * @return
     */
    @GetMapping(value = "/doctor/toQuesHood")
    public String toQuesHood(){
        return "/doctor/questionHood";
    }

    /**
     * 跳转到问答页面
     * @param doctor
     * @param request
     * @return
     */
    @GetMapping(value = "/doctor/toAnsHood")
    public String toAnsHood(Doctor doctor,HttpServletRequest request){
        System.out.println("toAnsHood");
        doctor = doctorService.getDoctorById(doctor.getId());

        //问题日期
        List<String> dates = questionService.getDates();
        request.setAttribute("dates",dates);

        //标签
        List<Tag> tags = questionService.getTags();
        request.setAttribute("tags",tags);

        return "/doctor/answerHood";
    }

    /**
     * 跳转到回答页面
     * @param question
     * @param request
     * @return
     */
    @GetMapping(value = "/doctor/toAnsPage")
    public ModelAndView toAnsPage(Question question, HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        question = questionService.getQuestionById(question.getId());
        logger.info("通过id找到的question->{}",JSON.toJSON(question));
        String questionCreateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(question.getGmtCreate()));
        List<AskAndAnswer> answers = question.getAskAndAnsList();
        for (AskAndAnswer answer : answers){
            if (answer.getGmtModified() != null){
                String answerUpdateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(answer.getGmtModified()));
                answer.setUpdateTime(answerUpdateTime);
            }
        }

        modelAndView.addObject("question",question);
        modelAndView.addObject("student",question.getStudent());
        modelAndView.addObject("questionCreateTime",questionCreateTime);

        modelAndView.addObject("askAndAns_s",answers);
        modelAndView.addObject("questionAndTags",question.getQuestionAndTags());
        modelAndView.setViewName("/doctor/answer");

        return modelAndView;
    }

    /**
     *医师提交回答，插入askAndAnswer表
     * @param request
     * @return
     */
    @PostMapping(value = "/doctor/submitAns")
    public String submitAns(HttpServletRequest request){

        String answer = request.getParameter("yourAnswer");
        String questionId = request.getParameter("question_id");

        String studentId = request.getParameter("student_id");
        Student student = new Student();
        student.setId(Integer.valueOf(studentId));

        Doctor doctor = (Doctor)request.getSession().getAttribute("doctor");

        //设置问题和医生回答映射
        AskAndAnswer askAndAns = new AskAndAnswer();
        askAndAns.setAnswer(answer);
        askAndAns.setDoctor(doctor);
        askAndAns.setQuestId(Integer.valueOf(questionId));
        askAndAns.setStudent(student);

        //将该问题设置为已回答
        questionService.setQuesStatus(Integer.valueOf(questionId),System.currentTimeMillis());

        //设置问题和标签映射
        String tagId = request.getParameter("tag");
        QuestionAndTag questionAndTag = new QuestionAndTag();
        questionAndTag.setQuestId(Integer.valueOf(questionId));
        if (StringUtils.isNotBlank(tagId)){
            Tag tag = new Tag();
            tag.setId(Integer.valueOf(tagId));
            questionAndTag.setTag(tag);
        }
        //更新问题和医生的映射
        questionService.updateAskAndAns(askAndAns,questionAndTag);

        return "200";
    }

    @GetMapping(value = "/doctor/toMyAppointment")
    public String toMyAppointment(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize,Model model,HttpServletRequest request){
        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
        logger.info("doctor->{}",JSON.toJSON(doctor));

        PageInfo<Appointment> myAppointments = doctorService.getMyAppointment(pageNum, pageSize,doctor.getId());
        model.addAttribute("myAppointments",myAppointments);
        return "/doctor/myAppointment";
    }

    @PostMapping(value = "/doctor/submitCause")
    public Integer submitCause(HttpServletRequest request){
        String cause = request.getParameter("cause");
        String appointmentId = request.getParameter("appointmentId");
        Integer state = 2;//审核未通过状态为2
        doctorService.insertAppointment(cause,state, Integer.valueOf(appointmentId));
        return 200;

    }

    @GetMapping(value = "/doctor/passAppointment/{appointmentId}")
    public String passAppointment(@PathVariable Integer appointmentId,Model model,@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "5",value = "pageSize") Integer pageSize,HttpServletRequest request){
        Integer state = 1;
        doctorService.insertAppointment(null,state,appointmentId);

        Doctor doctor = (Doctor) request.getSession().getAttribute("doctor");
        logger.info("doctor->{}",JSON.toJSON(doctor));

        PageInfo<Appointment> myAppointments = doctorService.getMyAppointment(pageNum, pageSize,doctor.getId());
        model.addAttribute("myAppointments",myAppointments);
        return "/doctor/myAppointment";
    }

}
