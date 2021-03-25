package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/24 22:07
 * @Version 1.0
 */
public interface ChatMessageMapper {

    //插入聊天记录
    @Insert("insert into chat_message (send_user_id, receive_user_id,  message_type, send_text,send_time)\n" +
            "    values (#{sendUserId}, #{receiveUserId}, #{massageType}, #{sendText},#{sendTime})")
    void InsertChatMassage(ChatMessage chatMessage);

    //查询聊天记录
    @Select("select * from chat_message where\n" +
            "    send_user_id=#{sendUserId} and receive_user_id=#{receiveUserId} or\n" +
            "    send_user_id=#{receiveUserId} and receive_user_id=#{sendUserId}")
    List<ChatMessage> findTwoUserMsg(ChatMessage chatMsg);

}
