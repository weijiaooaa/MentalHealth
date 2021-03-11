package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 21:42
 * @Version 1.0
 */
public interface StudentMapper {

    @Insert("insert into student(stu_number,name,gender,age,tel,email,password,state,gmt_create,gmt_modified) values (#{stuNumber}, #{name}, #{gender}, #{age},#{tel},#{email},#{password},#{state},#{gmtCreate},#{gmtModified})")
    void insertStu(Student student);

    @Select("select * from student where id = #{id}")
    Student selectStuById(Integer id);

    @Select("select * from student where stu_number = #{stuNumber} and password = #{password}")
    Student selectStu(String stuNumber, String password);

    Integer updateStudentState(Integer id);

    @Select("select * from student where stu_number = #{stuNumber}")
    Student selectStuByStuNumber(String stuNumber);

    @Select("select id from student where stu_number = #{stuNumber}")
    Integer selectIdByStuNumber(String stuNumber);
}
