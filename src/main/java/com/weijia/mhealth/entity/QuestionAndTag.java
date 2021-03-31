package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wei Jia
 * @Date 2021/3/18 14:35
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAndTag {
    private int id;
    private Integer questId;
    private Tag tag;
}
