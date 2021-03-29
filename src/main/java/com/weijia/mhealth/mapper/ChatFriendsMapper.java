package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.ChatFriends;
import com.weijia.mhealth.entity.ChatMessage;
import com.weijia.mhealth.entity.Doctor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/24 18:20
 * @Version 1.0
 */
public interface ChatFriendsMapper {

    @Select("select user_id,nick_name,user_img from user_info where user_id in " +
            "(select a.friend_id from chat_friends a where a.user_id=#{userId})")
    List<ChatFriends> findUserAllFriends(Integer userId);

    //查询所有好友的id
    @Select("select friend_id from chat_friends where user_id = #{id}")
    List<Integer> getFriendsId(Integer id);

    //插入好友
    @Insert(" insert into chat_friends (user_id, friend_id) value (#{userId},#{friendId})")
    void InsertUserFriend(ChatFriends chatFriends);

    @Select("select id,name from doctor where id in " +
            "(select a.friend_id from chat_friends a where a.user_id=#{id})")
    List<Doctor> findUserAllFriendsInStu(Integer id);
}
