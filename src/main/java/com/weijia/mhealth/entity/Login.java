package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wei Jia
 * @Date 2021/1/24 22:08
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private Integer id;

    private Integer accountId;

    private String accountName;

    private String password;

    private Long gmtCreate;

    private Long gmtModified;
}
