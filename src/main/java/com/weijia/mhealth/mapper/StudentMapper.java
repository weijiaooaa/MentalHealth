package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Appointment;
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

    @Select("select * from student where id = #{id}")
    Student getStuById(Integer id);

    @Select("select * from student")
    List<Student> getAllStudent();

    @Select("select * from student order by state DESC")
    List<Student> getStuPage();

    @Insert("insert into appointment(stu_id,doctor_id,dates,state,times,content,gmt_create) values (#{stuId},#{doctorId},#{dates},#{state},#{times},#{content},#{gmtCreate})")
    void insertAppointment(Appointment appointment);

    //根据questionId查询医生
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "doctor_id",property = "doctor",one = @One(select = "com.weijia.mhealth.mapper.DoctorMapper.getDoctorNameById",fetchType = FetchType.DEFAULT))
    })
    @Select("select * from appointment where stu_id = #{stuId} order by gmt_create DESC")
    List<Appointment> getMyAppointment(Integer stuId);

    @Delete("delete from appointment where id = #{appointmentId}")
    void removeAppointmentByAppId(Integer appointmentId);
}
