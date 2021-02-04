package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wei Jia
 * @Date 2021/1/24 23:00
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Integer id;

    private String nickName;

    private Integer userId;

    private Long gmtCreate;

    private Long gmtModified;
}
