package com.weijia.mhealth.service.Imp;

import com.weijia.mhealth.entity.ChatFriends;
import com.weijia.mhealth.entity.ChatMessage;
import com.weijia.mhealth.entity.Doctor;
import com.weijia.mhealth.mapper.ChatFriendsMapper;
import com.weijia.mhealth.mapper.ChatMessageMapper;
import com.weijia.mhealth.service.ChatFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<Integer> getFriendsId(Integer id) {
        return chatFriendsMapper.getFriendsId(id);
    }

    @Override
    public void setChatFriends(Integer userId, Integer doctorId) {
        //建立学生和老师的关系
        ChatFriends chatFriends = new ChatFriends();
        chatFriends.setUserId(userId);
        chatFriends.setFriendId(doctorId);
        chatFriendsMapper.InsertUserFriend(chatFriends);

        //建立老师和学生的关系
        chatFriends.setUserId(doctorId);
        chatFriends.setFriendId(userId);
        chatFriendsMapper.InsertUserFriend(chatFriends);
    }

    @Override
    public List<ChatFriends> findUserAllFriendsInStu(Integer id) {
        List<ChatFriends> chatFriends = new ArrayList<>();
        ChatFriends chatFriend = new ChatFriends();
        List<Doctor> friends = chatFriendsMapper.findUserAllFriendsInStu(id);
        for (Doctor friend: friends){
            chatFriend.setUserId(friend.getId());
            chatFriend.setNickName(friend.getName());
            chatFriends.add(chatFriend);
        }
        return chatFriends;
    }
}
