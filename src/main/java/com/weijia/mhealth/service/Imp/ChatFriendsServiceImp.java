package com.weijia.mhealth.service.Imp;

import com.weijia.mhealth.entity.ChatFriends;
import com.weijia.mhealth.entity.ChatMessage;
import com.weijia.mhealth.mapper.ChatFriendsMapper;
import com.weijia.mhealth.mapper.ChatMessageMapper;
import com.weijia.mhealth.service.ChatFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/10 14:38
 * @Version 1.0
 */
@Service
public class ChatFriendsServiceImp implements ChatFriendsService {

    @Autowired
    ChatFriendsMapper chatFriendsMapper;

    @Autowired
    ChatMessageMapper chatMessageMapper;

    @Override
    public Object LkUserinfoByUserid(String uid) {
        return null;
    }

    @Override
    public List<ChatFriends> findUserAllFriends(Integer userId) {
        return chatFriendsMapper.findUserAllFriends(userId);
    }
}
