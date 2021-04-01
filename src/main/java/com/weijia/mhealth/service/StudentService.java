package com.weijia.mhealth.service;

import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Student;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 21:12
 * @Version 1.0
 */
public interface StudentService {

    int insertStudentAndLogin(Student student);

    Student stuChecked(String stuNumber, String password);

    Student getStuById(Integer id);

    Student getStuByStuNumber(String stuNumber);

    Boolean updateStudentState(Boolean state,Student student);

    Student getStuByQuestionId(Integer questionId);

    PageInfo<Student> getAllStudent(Integer pageNum, Integer pageSize);

    PageInfo<Student> getStuPage(Integer pageNum, Integer pageSize);
}
