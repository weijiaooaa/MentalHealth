package com.weijia.mhealth.service;

import com.weijia.mhealth.entity.ChatFriends;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/10 14:37
 * @Version 1.0
 */
@Service
public interface ChatFriendsService {
    Object LkUserinfoByUserid(String uid);

    List<ChatFriends> findUserAllFriends(Integer userId);
}
