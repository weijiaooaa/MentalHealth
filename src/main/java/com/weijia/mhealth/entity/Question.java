package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/2/4 23:14
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer id;

    private String content;

    private Integer viewCount;

    private Integer likes;

    private boolean anonymous;

    private boolean status;

    private Long gmtCreate;

    private Long gmtModified;

    private Student student;
    private List<AskAndAnswer> askAndAnsList;
    private List<QuestionAndTag> questionAndTags;

    private String createTime;

}
