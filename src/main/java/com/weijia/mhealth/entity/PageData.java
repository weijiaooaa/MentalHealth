package com.weijia.mhealth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/27 15:50
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> implements Serializable {
    /** 数据集合 */
    protected List<T> result = new ArrayList();
    /** 数据总数 */
    protected int totalCount = 0;
    /** 总页数 */
    protected long pageCount = 0;
    /** 每页记录 */
    protected int pageSize = 5;
    /** 初始当前页 */
    protected int pageNo = 1;
    /**当前遍历问题集合的下标,默认到尾元素*/
    protected int indexEnd = -1;
}
