package com.weijia.mhealth.service.RedisService;

import com.weijia.mhealth.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 22:38
 * @Version 1.0
 */
@Service
public class UserRedisService {

    @Autowired
    private StringRedisTemplate redisUserTemplate; //加载个人在线信息，聊天信息进redis缓存

    //格式：user:stu,1614010822
    public void insertStu(Integer id){
        redisUserTemplate.opsForList().leftPush("user:stu",String.valueOf(id));
        redisUserTemplate.expire("user:stu",5, TimeUnit.MINUTES);
    }

    //检查该学生是否在线
    public Boolean isStuExist(Integer id){
        List<String> stu_ids = redisUserTemplate.opsForList().range("user:stu", 0, -1);
        assert stu_ids != null : "stu_ids is null";
        return stu_ids.contains(String.valueOf(id));
    }

    //得到在线医生
    public List<Doctor> getDoctorsOnline() {
        List<String> doctor_ids = getDoctorsIds();
        List<Doctor> doctors = new ArrayList<>();
        for (String id: doctor_ids) {
            doctors.add(doctorService.getDoctorById(id));
        }
        return doctors;
    }
}
