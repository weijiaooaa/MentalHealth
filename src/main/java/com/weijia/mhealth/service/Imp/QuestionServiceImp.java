package com.weijia.mhealth.service.Imp;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.Question;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.entity.Tag;
import com.weijia.mhealth.mapper.QuestionMapper;
import com.weijia.mhealth.mapper.StudentMapper;
import com.weijia.mhealth.service.QuestionService;
import com.weijia.mhealth.service.RedisService.QuestRedisService;
import com.weijia.mhealth.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/11 22:02
 * @Version 1.0
 */
@Service
public class QuestionServiceImp implements QuestionService {

    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImp.class);

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestRedisService questRedisService;

    @Autowired
    StudentService studentService;


    @Override
    public List<Question> getQuestionByStu(Student student) {
        List<Question> questions = new ArrayList<>();
        List<Integer> questionsId = questionMapper.getQuestionByStuId(student.getId());
        logger.info("questionsId is ->{}", JSON.toJSON(questionsId));
        for(Integer id : questionsId){
            Question question = questionMapper.getQuestionById(id);
            question.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.sql.Date(question.getGmtCreate())));
            questions.add(question);
        }
        return questions;
    }

    @Transactional
    @Override
    public void insertQues(Question question, Student student) {

        question.setGmtCreate(System.currentTimeMillis());
        question.setViewCount(0);
        question.setLikes(0);

        //插入问题
        questionMapper.insertQues(question);
        Integer questionId = questionMapper.getQuestionId(question.getContent(), question.getGmtCreate());

        //插入中间表
        questionMapper.insertQuesWithStu(questionId,student.getId());

        //检查dateList中是否有该日期
        List<Long> dateList = questRedisService.getDateList();
        Long temp = System.currentTimeMillis();
        //设置dateList的过期时间
        if(dateList.size() != 0){
            if(!dateList.contains(temp)){
                questRedisService.setDateListExpire();
                //插入时间到dates表
                questionMapper.insertDateTime(System.currentTimeMillis());
            }
        }
    }

    @Override
    public List<Long> getDates() {
        //先从缓存中取
        List<Long> dateList = questRedisService.getDateList();
        if(dateList.size() != 0){
            return dateList;
        }else{
            List<Long> dates = questionMapper.getDates();
            //将其写入到缓存中
            questRedisService.insertDates(dates);
            return dates;
        }
    }

    @Override
    public List<Tag> getTags() {

        List<Tag> tags = questionMapper.getTags();
        logger.info("db中查到的tags->{}",JSON.toJSON(tags));
        questRedisService.insertTags(tags);
        return tags;
    }

    @Override
    public List<Question> getQuestionByTagId(Integer tagId) {
        List<Question> questions = new ArrayList<>();
        List<Integer> questionIds = questionMapper.getQuestionByTagId(tagId);
        logger.info("根据tagId查询出的questionIds->{}",JSON.toJSON(questionIds));
        for (Integer questionId : questionIds) {
            Question question = questionMapper.getQuestionById(questionId);
            Student stu = studentService.getStuByQuestionId(question.getId());
            question.setStudent(stu);
            questions.add(question);
        }
        return questions;
    }
}
