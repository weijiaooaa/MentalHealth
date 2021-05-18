package com.weijia.mhealth.service;

import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Document;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 18:15
 * @Version 1.0
 */
@Service
public interface ResourcesService {
    void insertDocument(Document document);

    List<Document> getAllDocument();

    Document getDocumentById(Integer id);

    PageInfo<Document> getDocumentPage(Integer pageNum, Integer pageSize);

    void insertViewCount(Integer id);

    void deleteDocById(Integer docId);
}
