package com.weijia.mhealth.service.Imp;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Appointment;
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


    @Override
    public PageInfo<Doctor> getAllDoctor(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
    // 获取
    PageInfo<Doctor> pageInfo = getPageInfo(pageNum, pageSize);
        return pageInfo;
}

    @Override
    public PageInfo<Doctor> getDoctorPage(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Doctor> pageInfo = getPageInfoV2(pageNum, pageSize);
        return pageInfo;

    }

    @Override
    public PageInfo<Appointment> getMyAppointment(Integer pageNum, Integer pageSize, Integer doctorId) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Appointment> pageInfo = getPageInfoV3(pageNum, pageSize,doctorId);
        return pageInfo;
    }

    @Override
    public void insertAppointment(String cause,Integer state, Integer appointmentId) {
        doctorMapper.insertAppointment(cause,state,appointmentId);
    }

    @Override
    public void delectDoctorById(Integer doctorId) {
        doctorMapper.delectDoctorById(doctorId);
    }

    private PageInfo<Appointment> getPageInfoV3(Integer pageNum, Integer pageSize, Integer doctorId) {
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
            List<Appointment> appointments = doctorMapper.getMyAppointment(doctorId);
            PageInfo<Appointment> pageInfo = new PageInfo<>(appointments, pageSize);
            pageInfo.setList(appointments);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    private PageInfo<Doctor> getPageInfoV2(Integer pageNum, Integer pageSize) {
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
            List<Doctor> doctors = doctorMapper.getDoctorPage();

            PageInfo<Doctor> pageInfo = new PageInfo<>(doctors, pageSize);
            pageInfo.setList(doctors);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }

    private PageInfo<Doctor> getPageInfo(Integer pageNum, Integer pageSize) {
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
            List<Doctor> doctors = doctorMapper.getAllDoctor();
            PageInfo<Doctor> pageInfo = new PageInfo<>(doctors, pageSize);
            pageInfo.setList(doctors);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
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
