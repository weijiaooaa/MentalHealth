package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author Wei Jia
 * @Date 2021/3/24 22:10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ChatMessage {
    private Integer sendUserId;

    private Integer receiveUserId;

    private Date sendTime;

    private String massageType;

    private String sendText;

    private String sendTimeToString;
}
