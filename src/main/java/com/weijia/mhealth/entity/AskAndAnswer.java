package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wei Jia
 * @Date 2021/3/18 14:33
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskAndAnswer {
    private Integer id;
    private String quest_id;
    private Doctor doctor;
    private Student student;
    private String answer;
    private Long gmtCreate;

    private Long gmtModified;
}
