package com.weijia.mhealth.service.Imp;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.Question;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.entity.Tag;
import com.weijia.mhealth.mapper.QuestionMapper;
import com.weijia.mhealth.service.QuestionService;
import com.weijia.mhealth.service.RedisService.QuestRedisService;
import com.weijia.mhealth.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
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
            Question question = questionMapper.getQuestionByIdV2(id);
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
    public List<String> getDates() {

        HashSet<String> dateSet = new HashSet<>();
        //先从缓存中取
        List<Long> dateLists = questRedisService.getDateList();
        logger.info("从redis中取出的dateList->{}",JSON.toJSON(dateLists));
        if(dateLists.size() != 0){
            for (Long date : dateLists) {
                String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(date));
                dateSet.add(format);
            }
            return new ArrayList<String>(dateSet);
        }else{
            List<Long> dates = questionMapper.getDates();
            logger.info("从db中取出的dateList->{}",JSON.toJSON(dates));
            //将其写入到缓存中
            questRedisService.insertDates(dates);
            for (Long date : dates) {
                String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(date));
                dateSet.add(format);
            }
            return new ArrayList<String>(dateSet);
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
            Question question = questionMapper.getQuestionByIdV2(questionId);
            question.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.sql.Date(question.getGmtCreate())));
            questions.add(question);
        }
        return questions;
    }

    @Override
    public Question getQuestionByIdV2(Integer id) {
        return questionMapper.getQuestionByIdV2(id);
    }

    @Override
    public List<Question> getQuestionByDate(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long startTime = sdf.parse(dateTime).getTime();
            long endTime = startTime + 86399000;
            return questionMapper.getQuestionByDate(startTime,endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("时间转换失败！");
            return null;
        }
    }

    @Override
    public void insertViewCount(Integer id) {
        long updateTime = System.currentTimeMillis();
        questionMapper.insertViewCount(id,1,updateTime);
    }
}
