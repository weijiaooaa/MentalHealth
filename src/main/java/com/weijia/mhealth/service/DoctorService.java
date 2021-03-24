package com.weijia.mhealth.service;

import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/2/6 20:11
 * @Version 1.0
 */
@Service
public interface DoctorService {
    Doctor getDoctorById(Integer id);

    List<Doctor> getDoctorState(Boolean state);

    Doctor getStuByStuNumber(String doctorNumber);

    void insertDoctor(Doctor doctor);

    List<Student> getStuState(boolean state);

    Doctor doctorChecked(String doctorNumber,String password);
}
