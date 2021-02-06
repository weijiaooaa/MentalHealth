package com.weijia.mhealth.service.Imp;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.controller.StudentController;
import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.mapper.DoctorMapper;
import com.weijia.mhealth.service.DoctorService;
import com.weijia.mhealth.service.RedisService.UserRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/2/6 20:09
 * @Version 1.0
 */
@Service
public class DoctorServiceImp implements DoctorService {

    private final static Logger logger = LoggerFactory.getLogger(DoctorServiceImp.class);

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public Doctor getDoctorById(Integer id) {
        Doctor doctor = doctorMapper.getDoctorById(id);
        logger.info("doctor is ->{}", JSON.toJSON(doctor));
        return doctor;
    }

    @Override
    public List<Doctor> getDoctorState(Boolean state) {
        List<Doctor> doctorList = doctorMapper.getDoctorState(state);
        logger.info("在线的医生列表->{}",JSON.toJSON(doctorList));
        return doctorList;
    }

    /**
     *得到数据库中所有Doctor Id
     * @return
     */
    private List<Integer> getDoctorIds() {
        List<Integer> ids = doctorMapper.getAll();
        logger.info("所有id->{}",JSON.toJSON(ids));
        return ids;
    }
}
