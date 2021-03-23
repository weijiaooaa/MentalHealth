package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Doctor;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/2/6 20:15
 * @Version 1.0
 */
public interface DoctorMapper {

    //根据id查询
    @Select("select * from doctor where id = #{id}")
    Doctor getDoctorById(Integer id);

    //获取所有id数据
    @Select("select id from doctor")
    List<Integer> getAll();

    @Select("select * from doctor where state = #{state}")
    List<Doctor> getDoctorState(Boolean state);

    //查出id + username
    @Select("select id, username from doctor where id = #{id}")
    Doctor getDoctorNameById(Integer id);
}
