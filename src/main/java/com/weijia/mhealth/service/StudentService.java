package com.weijia.mhealth.service;

import com.weijia.mhealth.entity.Student;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 21:12
 * @Version 1.0
 */
public interface StudentService {

    int insertStudentAndLogin(Student student);

    Student stuChecked(Integer id, String password);

    Student getStuById(Integer id);
}
