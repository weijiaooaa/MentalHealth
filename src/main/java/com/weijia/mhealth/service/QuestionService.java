package com.weijia.mhealth.service;

import com.weijia.mhealth.entity.Question;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.entity.Tag;
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
}
