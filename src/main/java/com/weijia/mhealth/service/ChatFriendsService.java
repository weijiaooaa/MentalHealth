package com.weijia.mhealth.service;

import org.springframework.stereotype.Service;

/**
 * @Author Wei Jia
 * @Date 2021/3/10 14:37
 * @Version 1.0
 */
@Service
public interface ChatFriendsService {
    Object LkUserinfoByUserid(String uid);
}
