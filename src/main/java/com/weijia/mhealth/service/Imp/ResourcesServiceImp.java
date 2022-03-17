package com.weijia.mhealth.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Document;
import com.weijia.mhealth.entity.Question;
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

    @Override
    public PageInfo<Document> getDocumentPage(Integer pageNum, Integer pageSize) {
        //使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        // 获取
        PageInfo<Document> pageInfo = getPageInfo(pageNum, pageSize);
        return pageInfo;
    }

    @Override
    public void insertViewCount(Integer id) {
        long updateTime = System.currentTimeMillis();
        resourcesMapper.insertViewCount(id,1,updateTime);
    }

    @Override
    public void deleteDocById(Integer docId) {
        resourcesMapper.deleteDocById(docId);
    }

    private PageInfo<Document> getPageInfo(Integer pageNum, Integer pageSize) {
        //判断非空
        if (pageNum == null) {
            pageNum = 1; //设置默认当前页
        }
        if (pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 5; //设置默认每页显示的数据数
        }
        //1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
        try {
            List<Document> documents = resourcesMapper.getAllDocument();
            PageInfo<Document> pageInfo = new PageInfo<>(documents, pageSize);
            pageInfo.setList(documents);
            return pageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage(); //清理 ThreadLocal 存储的分页参数,保证线程安全
        }
        return null;
    }
}
