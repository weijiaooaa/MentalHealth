package com.weijia.mhealth.controller;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.PageData;
import com.weijia.mhealth.entity.Question;
import com.weijia.mhealth.entity.Tag;
import com.weijia.mhealth.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/27 15:42
 * @Version 1.0
 */
@Controller
public class QuestionController {

    private final static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    QuestionService questionService;

    /**
     *返回pageData的json数据
     * @param pageData
     * @return
     */
    @GetMapping(value = "/question/pageData")
    @ResponseBody
    public PageData<Question> getPageData(PageData<Question> pageData){

        //设置每页数据条数
        pageData.setPageSize(5);

        pageData = questionService.getPageData(pageData);
        logger.info("pageDate集合->{}", JSON.toJSON(pageData));

        //注意limit语法：select * from table limit (start-1)*pageSize,pageSize
        int begin = (pageData.getPageNo() - 1) * pageData.getPageSize();
        int end  = pageData.getPageSize();
        List<Question> questions = questionService.getQuestions(pageData,begin,end);

        for (Question question : questions){
            question.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.sql.Date(question.getGmtCreate())));
        }

        pageData.setResult(questions);
        logger.info("PageDate->{}",JSON.toJSON(pageData));
        return pageData;
    }

    @GetMapping("/question/getTags")
    @ResponseBody
    public List<Tag> getTags(){
        List<Tag>tags = questionService.getTags();
        logger.info("拿到的tags->{}",JSON.toJSON(tags));
        return tags;
    }
}
