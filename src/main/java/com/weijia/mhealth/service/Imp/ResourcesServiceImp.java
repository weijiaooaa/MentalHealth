package com.weijia.mhealth.service.Imp;

import com.weijia.mhealth.entity.Document;
import com.weijia.mhealth.entity.Tag;
import com.weijia.mhealth.mapper.ResourcesMapper;
import com.weijia.mhealth.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 18:16
 * @Version 1.0
 */
@Service
public class ResourcesServiceImp implements ResourcesService {

    @Autowired
    ResourcesMapper resourcesMapper;

    @Transactional
    @Override
    public void insertDocument(Document document) {

        resourcesMapper.insertDocument(document);

        List<Tag> tags = document.getTags();
        Integer id = resourcesMapper.getDocumentByTitleAndCreator(document.getTitle(), document.getCreator());
        for (Tag tag : tags){
            resourcesMapper.insertToTagAndDocument(tag.getId(),id);
        }
    }

    @Override
    public List<Document> getAllDocument() {
        return resourcesMapper.getAllDocument();
    }

    @Override
    public Document getDocumentById(Integer id) {
        return resourcesMapper.getDocumentById(id);
    }
}
