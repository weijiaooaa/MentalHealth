package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 23:05
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentsAndTag {
    private int id;
    private Integer documentId;
    private Tag tag;
}
