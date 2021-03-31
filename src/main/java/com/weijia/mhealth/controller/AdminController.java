package com.weijia.mhealth.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.*;
import com.weijia.mhealth.service.AdminService;
import com.weijia.mhealth.service.DoctorService;
import com.weijia.mhealth.service.ResourcesService;
import com.weijia.mhealth.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 16:39
 * @Version 1.0
 */
@Controller
public class AdminController {
    private final static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    ResourcesService resourcesService;

    @Autowired
    StudentService studentService;

    @Autowired
    DoctorService doctorService;

    /**
     * 跳转到登录页面
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/admin")
    public String loginPage(HttpServletRequest httpServletRequest){
        String s = httpServletRequest.getParameter("success");
        httpServletRequest.setAttribute("success",s);
        return "/user/sign-in-admin";
    }

    /**
     * 校验管理员身份（id + password）
     * @return
     */
    @GetMapping("/admin/adminChecked")
    @ResponseBody
    public Admin AdminChecked(Admin admin){
        logger.info("admin的信息->{}", JSON.toJSON(admin));
        return adminService.checkAdmin(admin);
    }

    /**
     * 跳转到主管理员主页
     * @param admin
     * @return
     */
    @GetMapping("/admin/toHomePage")
    public String toHomePage(Admin admin,HttpServletRequest request){
        admin = adminService.getAdminByName(admin.getName());
        request.getSession().setAttribute("admin",admin);
        return "/admin/home";
    }

    /**
     * 跳转到用户管理
     * @return
     */
    @GetMapping("/admin/toUserManager")
    public String toUserManager(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize, Model model){

        PageInfo<Student> students = studentService.getAllStudent(pageNum, pageSize);
        PageInfo<Doctor> doctors = doctorService.getAllDoctor(pageNum, pageSize);
        PageInfo<Admin> admins = adminService.getAllAdmin(pageNum, pageSize);

        model.addAttribute("stuPageInfo",students);
        model.addAttribute("docPageInfo",doctors);
        model.addAttribute("admPageInfo",admins);
        return "/admin/userManager";
    }

    @GetMapping("/admin/findStuPage")
    public String stuPage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "2",value = "pageSize") Integer pageSize,Model model){
        PageInfo<Student> allStudent = studentService.getAllStudent(pageNum, pageSize);
        logger.info("分页查找->{}",JSON.toJSON(allStudent));
        model.addAttribute("stuPageInfo",allStudent);
        return "/admin/stuPage";
    }

    @GetMapping("/admin/findDocPage")
    public String docPage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,Model model){
        PageInfo<Doctor> docPageInfo = doctorService.getAllDoctor(pageNum, pageSize);
        logger.info("分页查找->{}",JSON.toJSON(docPageInfo));
        model.addAttribute("docPageInfo",docPageInfo);
        return "/admin/doctorPage";
    }

    @GetMapping("/admin/findAdminPage")
    public String adminPage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "1",value = "pageSize") Integer pageSize,Model model){
        PageInfo<Admin> adminPageInfo = adminService.getAllAdmin(pageNum, pageSize);
        logger.info("分页查找->{}",JSON.toJSON(adminPageInfo));
        model.addAttribute("admPageInfo",adminPageInfo);
        return "/admin/adminPage";
    }

    /**
     * 跳转到资源管理
     * @return
     */
    @GetMapping("/admin/toResourceManager")
    public String toResourceManager(Model model){

        List<Document> documents = resourcesService.getAllDocument();
        model.addAttribute("documents",documents);
        return "/admin/resourceManager";
    }

    @GetMapping("/admin/toActivities")
    public String toActivityPage(HttpServletRequest request){
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin",admin);
        return "/admin/activities";
    }

    @GetMapping("/admin/toActivitiesUpload")
    public String toActivitiesUpload(){
        return "/admin/activities-upload";
    }

    @GetMapping("/admin/toDocumentUpload")
    public String toDocumentUpload(){
        return "/admin/document-upload";
    }

    /**
     * 跳转到上传专栏
     * @param request
     * @throws IOException
     */
    @PostMapping("/admin/postDocument")
    public String postFile(HttpServletRequest request) throws IOException {
        //得到文章标题
        String title = request.getParameter("title");
        //得到文章作者
        String creator = request.getParameter("creator");
        //得到专栏标签
        String tags = request.getParameter("tags");
        //得到文章内容
        String content = request.getParameter("content");
        String url = request.getParameter("url");


        Document document = new Document();
        document.setTitle(title);
        document.setContent(content);
        document.setCreator(creator);
        document.setUrl(url);
        document.setGmtCreate(System.currentTimeMillis());
        document.setGmtModified(System.currentTimeMillis());


        String token[] = tags.split(",");
        List<Tag> tagList = new ArrayList<>();
        for (String t:token){
            Tag tag = new Tag();
            tag.setId(Integer.valueOf(t));
            tagList.add(tag);
        }
        document.setTags(tagList);

        resourcesService.insertDocument(document);
        return "200";
    }
}
