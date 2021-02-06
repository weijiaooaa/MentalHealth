package com.weijia.mhealth.service;

import com.weijia.mhealth.entity.Doctor;
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
}
