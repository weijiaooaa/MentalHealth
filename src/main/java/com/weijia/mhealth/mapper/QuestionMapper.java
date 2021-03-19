package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Question;
import com.weijia.mhealth.entity.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/11 22:04
 * @Version 1.0
 */
public interface QuestionMapper {

    @Select("select distinct quest_id from ask_and_answer where stu_id = #{id}")
    List<Integer> getQuestionByStuId(Integer id);

    @Select("select * from question where id = #{id}")
    Question getQuestionById(Integer id);

    @Insert("insert into question(content,view_count,likes,anonymous,status,gmt_create,gmt_Modified) values (#{content},#{viewCount},#{likes},#{anonymous},#{status},#{gmtCreate},#{gmtModified})")
    void insertQues(Question question);

    @Insert("insert into ask_and_answer (quest_id, stu_id) values (#{quesId}, #{stuId})")
    void insertQuesWithStu(Integer quesId, Integer stuId);

    @Insert("insert into dates(time) values (#{time})")
    void insertDateTime(long time);

    @Select("select time from dates")
    List<Long> getDates();

    @Select("select id from question where content = #{content} and gmt_create = #{gmtCreate}")
    Integer getQuestionId(String content, Long gmtCreate);

    //得到不含"无"的标签
    @Select("select * from tag where tag_Name != '无'")
    List<Tag> getTags();

    //根据tagId找出所有的question集合
    @Select("select quest_id from questions_and_tags where tag_id = #{tagId}")
    List<Integer> getQuestionByTagId(Integer tagId);
}
