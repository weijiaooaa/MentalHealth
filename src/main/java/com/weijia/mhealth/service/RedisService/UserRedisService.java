package com.weijia.mhealth.service.RedisService;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 22:38
 * @Version 1.0
 */
@Service
public class UserRedisService {
    private final static Logger logger = LoggerFactory.getLogger(UserRedisService.class);

    @Autowired
    private StringRedisTemplate redisUserTemplate; //聊天信息进redis缓存

    @Autowired
    private RedisTemplate<String,Student> studentRedisTemplate;//将Student在线信息存入redis

    @Autowired
    private RedisTemplate<String,Doctor> doctorRedisTemplate;//将Doctor在线信息存入redis


    //格式：user:stu,Student student
    public void insertStu(Student student){
        studentRedisTemplate.opsForList().leftPush("user:stu",student);
        studentRedisTemplate.expire("user:stu",1, TimeUnit.DAYS);
    }

    //格式：user:doctor,Doctor doctor
    public void insertDoctor(Doctor doctor){
        doctorRedisTemplate.opsForList().leftPush("user:doctor",doctor);
        doctorRedisTemplate.expire("user:doctor",1, TimeUnit.DAYS);
    }

    public void removeStudentByValue(Student student) {
        studentRedisTemplate.opsForList().remove("user:stu",0,student);
        List<Student> students = studentRedisTemplate.opsForList().range("user:stu", 0, -1);
        logger.info("删除Redis中在线状态后的list->{}",JSON.toJSON(students));
    }

    //检查该学生是否在线
    public Boolean isStuExist(Student student){
        logger.info("student is->{}",JSON.toJSON(student));
        List<Student> students= studentRedisTemplate.opsForList().range("user:stu", 0, -1);
        assert students != null : "stu_ids is null";
        return students.contains(student);
    }

    //得到在线医生
    public List<Doctor> getDoctorsOnline() {
        List<Doctor> doctors = doctorRedisTemplate.opsForList().range("user:doctor", 0, 1);
        logger.info("所有在线医生列表->{}",JSON.toJSON(doctors));
        return doctors;
    }
}
