package com.weijia.mhealth.service;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.ChatMessage;
import com.weijia.mhealth.mapper.ChatMessageMapper;
import com.weijia.mhealth.service.Imp.DoctorServiceImp;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @Author Wei Jia
 * @Date 2021/3/24 22:04
 * @Version 1.0
 */
@Service
public class ChatMessageService {
    private final static Logger logger = LoggerFactory.getLogger(ChatMessageService.class);
    @Autowired
    ChatMessageMapper chatMessageMapper;
    @Async
    public void InsertChatMsg(ChatMessage chatMessage){
        chatMessage.setSendTime(new Date(System.currentTimeMillis()));
        chatMessageMapper.InsertChatMassage(chatMessage);
    }
    public List<ChatMessage> findTwoUserMsg(ChatMessage chatMessage){
        List<ChatMessage> twoUserMsg = chatMessageMapper.findTwoUserMsg(chatMessage);
        for (ChatMessage chatMsg : twoUserMsg){
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(chatMsg.getSendTime());
            if (DateUtils.isSameDay(chatMsg.getSendTime(),new Date(System.currentTimeMillis()))){
                chatMsg.setSendTimeToString(format.substring(11));
            }else{
                chatMsg.setSendTimeToString(format.substring(5));
            }
        }
        logger.info("从db中查出的聊天记录->{}", JSON.toJSON(twoUserMsg));
        return twoUserMsg;
    }

}
