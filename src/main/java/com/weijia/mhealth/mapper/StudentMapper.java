package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Student;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

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

    @Update("update student set state = #{state} where id = #{id}")
    Integer updateStudentState(Boolean state,Integer id);

    @Select("select * from student where stu_number = #{stuNumber}")
    Student selectStuByStuNumber(String stuNumber);

    @Select("select id from student where stu_number = #{stuNumber}")
    Integer selectIdByStuNumber(String stuNumber);

    @Select("select distinct stu_id from ask_and_answer where quest_id = #{questionId}")
    Integer getStuIdByQuestionId(Integer questionId);

    //根据questionId查询学生
    @Results({
            @Result(column = "stu_id",property = "id"),
            @Result(column = "stu_id",property = "name",one = @One(select = "com.weijia.mhealth.mapper.StudentMapper.getUsernameById",fetchType = FetchType.DEFAULT))
    })
    @Select("select distinct stu_id from ask_and_answer where quest_id = #{questionId}")
    Student getStuByQuesId(Integer questionId);

    @Select("select name from student where id = #{stu_id}")
    String getUsernameById(String stu_id);

    @Select("select * from student where state = #{state}")
    List<Student> getDoctorState(boolean state);
}
