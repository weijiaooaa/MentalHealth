package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Doctor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Select("select id, name from doctor where id = #{id}")
    Doctor getDoctorNameById(Integer id);

    @Select("select * from doctor where doctor_number = #{doctorNumber}")
    Doctor getDoctorByDoctorNumber(String doctorNumber);

    @Insert("insert into doctor(doctor_number,name,gender,age,teach_years,graduated_school,tel,email,password," +
            "gmt_create) values (#{doctorNumber},#{name}, #{gender}, #{age},#{teachYears}, #{graduatedSchool}, #{tel}," +
            "#{email},#{password},#{gmtCreate})")
    void insertDoctor(Doctor doctor);

    @Select("select * from doctor where doctor_number = #{doctorNumber} and password = #{password}")
    Doctor getDoctorByDoctorNumberAndPassword(String doctorNumber, String password);

    @Update("update doctor set state = #{state} where id = #{id}")
    Integer updateDoctorState(boolean state, Integer id);
}
