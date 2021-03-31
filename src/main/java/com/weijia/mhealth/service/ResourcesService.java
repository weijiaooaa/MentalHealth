package com.weijia.mhealth.service;

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
}
