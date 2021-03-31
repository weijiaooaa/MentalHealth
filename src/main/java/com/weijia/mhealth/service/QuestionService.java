package com.weijia.mhealth.service;

import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/11 22:01
 * @Version 1.0
 */
@Service
public interface QuestionService {
    List<Question> getQuestionByStu(Student student);

    void insertQues(Question question, Student student);

    List<String> getDates();

    List<Tag> getTags();

    List<Question> getQuestionByTagId(Integer tagId);

    Question getQuestionByIdV2(Integer id);

    List<Question> getQuestionByDate(String dateTime);

    void insertViewCount(Integer id);

    PageData<Question> getPageData(PageData<Question> pageData);

    List<Question> getQuestions(PageData<Question> pageData, int begin, int end);

    Question getQuestionById(Integer questionId);

    void setQuesStatus(Integer questionId,Long updateTime);

    void updateAskAndAns(AskAndAnswer askAndAns, QuestionAndTag questionAndTag);

    PageInfo<Question> getAllQuestion(Integer pageNum, Integer pageSize);
}
