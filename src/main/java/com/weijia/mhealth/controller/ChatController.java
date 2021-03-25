package com.weijia.mhealth.controller;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.ChatFriends;
import com.weijia.mhealth.entity.ChatMessage;
import com.weijia.mhealth.service.ChatFriendsService;
import com.weijia.mhealth.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/24 18:13
 * @Version 1.0
 */
@Controller
public class ChatController {
    private final static Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    ChatFriendsService chatFriendsService;

    @Autowired
    ChatMessageService chatMessageService;

    /**
     *查询用户的好友
     * @param session
     * @return
     */
    @PostMapping("/chat/findUserFriends")
    @ResponseBody
    public List<ChatFriends> findUserFriends(HttpSession session){
        Integer userId=(Integer)session.getAttribute("userid");
        List<ChatFriends> allFriends = chatFriendsService.findUserAllFriends(userId);
        logger.info("聊天页面查找的所有好友->{}", JSON.toJSON(allFriends));
        return allFriends;
    }

    /***
     * 查询两个用户之间的聊天记录
     * */
    @PostMapping("/chat/findUserChatMsg/{receiveUserId}")
    @ResponseBody
    public List<ChatMessage> findFriendsChatMsg(HttpSession session, @PathVariable("receiveUserId")String receiveUserId){
        Integer userId=(Integer)session.getAttribute("userid");
        return chatMessageService.findTwoUserMsg(new ChatMessage().setSendUserId(userId).setReceiveUserId(Integer.valueOf(receiveUserId)));
    }
}
