package com.weijia.mhealth.service.Imp;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.*;
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
import java.util.Set;

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

    //更新数据库中问题的数目，不必每次都到数据库中查找问题数目
    private static Integer count;


    @Override
    public List<Question> getQuestionByStu(Student student) {
        List<Question> questions = new ArrayList<>();
        List<Integer> questionsId = questionMapper.getQuestionByStuId(student.getId());
        logger.info("questionsId is ->{}", JSON.toJSON(questionsId));
        for(Integer id : questionsId){
            Question question = questionMapper.getQuestionByIdV2(id);
            question.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.sql.Date(question.getGmtCreate())));
            questions.add(question);
        }
        return questions;
    }

    @Transactional
    @Override
    public void insertQues(Question question, Student student) {

        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        question.setViewCount(0);
        question.setLikes(0);

        //插入问题
        questionMapper.insertQues(question);
        Integer questionId = questionMapper.getQuestionId(question.getContent(), question.getGmtCreate());

        //插入中间表
        long createTime = System.currentTimeMillis();
        questionMapper.insertQuesWithStu(questionId,student.getId(),createTime);

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
        //先去Redis中拿tags
        List<Tag> tagList = questRedisService.getTags();
        logger.info("Redis中查到的tags->{}",JSON.toJSON(tagList));
        if (tagList.size() == 0){
            //Redis中无记录，再去db中查询
            List<Tag> tags = questionMapper.getTags();
            logger.info("db中查到的tags->{}",JSON.toJSON(tags));
            questRedisService.insertTags(tags);
            return tags;
        }else {
            return tagList;
        }


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

    @Override
    public PageData<Question> getPageData(PageData<Question> pageData) {
        //count每次需要到数据库中取数据，保证数据的时效性
        count = questionMapper.getQuesCount();
        pageData.setTotalCount(count);
        if(count % pageData.getPageSize() == 0){
            pageData.setPageCount(count / pageData.getPageSize());
        }else{
            pageData.setPageCount(count / pageData.getPageSize() + 1);
        }
        if(pageData.getPageCount() == 0){
            pageData.setPageCount(1);
        }
        return pageData;

    }

    /**
     *分页查询得到问题列表，并将问题按日期保存在redis中。方便在redis中按日期查询。
     * @param pageData
     * @param begin
     * @param end
     * @return
     */
    @Override
    public List<Question> getQuestions(PageData<Question> pageData, int begin, int end) {
        Integer pageNo = pageData.getPageNo();
        //若查找的是前4页数据，并且该数据在缓存中，则从缓存中取,并返回指定页的问题集合
        if(!questRedisService.isQuesEmpty() && begin < 4 * pageData.getPageSize()){
            logger.info("进入Redis中查找问题数据");
            int pageNum = pageData.getPageNo();

            List<Question> questions = questRedisService.getQuesByPage(pageNum - 1);

            return questions;
        }
        //先到数据库中，按日期先取4页数据
        else if(begin < 4 * pageData.getPageSize()){
            logger.info("进入db中查找问题数据");
            List<Question> questions = questionMapper.getQuestions(0, 4 * pageData.getPageSize());

            String time;
            for (Question question: questions) {
                time = String.valueOf(question.getGmtCreate());

                time = "ques:" + time;
                /**------------------------------将问题按日期插入到redis,数据类型为set，并设置过期时间------------------------------------*/
                questRedisService.insertQuesByDate(time,question);

                /**------------------------------将日期插入到redis，数据类型为list，并设置过期时间------------------------------------*/
//                System.out.println(!quesCacheService.isQuesDateEmpty(time) + "  " + time);
                if(!questRedisService.isQuesDateEmpty(time)){
                    questRedisService.insertQuesDate(time);
                    questRedisService.setQuesExpire(time,5);
                }
            }

            questRedisService.setDateExpire("ques:date",5);

            /**------------------------------根据问题集合生成分页表，数据类型是hash，并设置过期时间 ------------------------------------*/
            List<String> dates = questRedisService.getQuesDate();

            for (String date: dates) {
                Set<Question> quesSet = questRedisService.getQuesByDate(date);
                pageData = questRedisService.insertQuesPage(pageData,date,quesSet);

                //若pageData的indexEnd不是-1，则该集合还有元素未遍历，需要重新再来
                while(pageData.getIndexEnd() != -1){
                    pageData = questRedisService.insertQuesPage(pageData,date,quesSet);
                }
            }

            //hash表生成成功,将缓存数据个数count置0
            QuestRedisService.setCount(0);

            //设置页面过期时间
            setPagesExpire(pageData);

            return questRedisService.getQuesByPage(pageNo - 1);
        }
        else{
            logger.info("从db中根据begin和end查找questions");
            List<Question> questions = questionMapper.getQuestions(begin, end);
            return questions;
        }

    }

    @Override
    public Question getQuestionById(Integer questionId) {
        return questionMapper.getQuestionByIdV2(questionId);
    }

    @Override
    public void setQuesStatus(Integer questionId,Long updateTime) {
        questionMapper.setQuesStatus(questionId,updateTime);
    }

    @Transactional
    @Override
    public void updateAskAndAns(AskAndAnswer askAndAns, QuestionAndTag questionAndTag) {
        Integer dId = questionMapper.isDoctorIdEmpty(askAndAns.getQuestId());

        askAndAns.setGmtCreate(System.currentTimeMillis());
        askAndAns.setGmtModified(System.currentTimeMillis());

        //dId为空，更新即可
        if(dId == null){
            questionMapper.updateAskAndAns(askAndAns);
        }
        //dId不空，需要插入
        else{
            questionMapper.insertAskAndAns(askAndAns);
        }

        if (questionAndTag.getTag() != null)
            //插入问题和标签映射
            questionMapper.insertQuesWithTag(questionAndTag.getQuestId(),questionAndTag.getTag().getId());
    }

    @Override
    public PageInfo<Question> getQuestionByDoctorId(Integer pageNum, Integer pageSize,Integer doctorId) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Question> pageInfo = getPageInfo(pageNum, pageSize,doctorId);
        return pageInfo;
    }

    private PageInfo<Question> getPageInfo(Integer pageNum, Integer pageSize,Integer doctorId) {
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
            List<Question> questions = questionMapper.getQuestionByDoctorId(doctorId);
            PageInfo<Question> pageInfo = new PageInfo<>(questions, pageSize);
            pageInfo.setList(questions);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    public void setPagesExpire(PageData<Question> pageData){
        //设置页面过期时间
        for (int i = 0; i < pageData.getPageCount(); i++) {
            questRedisService.setPageExpire("question:" + (i),5);
        }
    }
}
