package com.weijia.mhealth.service.Imp;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.mapper.DoctorMapper;
import com.weijia.mhealth.mapper.StudentMapper;
import com.weijia.mhealth.service.DoctorService;
import com.weijia.mhealth.service.RedisService.UserRedisService;
import com.weijia.mhealth.utils.exception.CMSException;
import com.weijia.mhealth.utils.exception.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserRedisService userRedisService;

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

    @Override
    public Doctor getStuByStuNumber(String doctorNumber) {
        Doctor doctor = doctorMapper.getDoctorByDoctorNumber(doctorNumber);
        logger.info("根据doctorNumber从db中查出的doctor->{}",JSON.toJSON(doctor));
        return doctor;
    }

    @Override
    public void insertDoctor(Doctor doctor) {
        doctorMapper.insertDoctor(doctor);
    }

    @Override
    public List<Student> getStuState(boolean state) {
        return studentMapper.getDoctorState(state);
    }

    @Override
    public Doctor doctorChecked(String doctorNumber, String password) {
        Doctor doctor = doctorMapper.getDoctorByDoctorNumberAndPassword(doctorNumber,password);
        logger.info("get student is->{}", JSON.toJSON(doctor));
        return doctor;
    }

    @Override
    public Boolean updateDoctorState(boolean state, Doctor doctor) {
        //先删除Redis中的在线状态
        try{
            userRedisService.removeDoctorByValue(doctor);
        }catch (Exception e){
            throw new CMSException(ResultCodeEnum.UPDATE_DOCTOR_STATE_IN_REDIS);
        }

        Integer id = doctor.getId();
        //再修改数据库中的状态
        Integer res = doctorMapper.updateDoctorState(state,id);
        if (res == 0)
            throw new CMSException(ResultCodeEnum.UPDATE_DOCTOR_STATE_IN_DB);
        else
            return true;
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
