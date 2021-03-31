package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 18:09
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    private Integer id;
    private String title;
    private String creator;
    private String content;
    private String url;
    private Long gmtCreate;
    private Long gmtModified;
    private List<Tag> tags;
    private List<DocumentsAndTag> documentsAndTags;
}
