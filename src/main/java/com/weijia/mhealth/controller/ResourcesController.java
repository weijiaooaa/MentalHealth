package com.weijia.mhealth.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.weijia.mhealth.entity.Document;
import com.weijia.mhealth.entity.DocumentsAndTag;
import com.weijia.mhealth.entity.Tag;
import com.weijia.mhealth.service.ResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 23:48
 * @Version 1.0
 */
@Controller
public class ResourcesController {
    private final static Logger logger = LoggerFactory.getLogger(ResourcesController.class);

    @Autowired
    ResourcesService resourcesService;

    @GetMapping(value = "/study/toStudyPage")
    public String toStudyPage(@RequestParam(required = false,defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "8",value = "pageSize") Integer pageSize, Model model){
        PageInfo<Document> documents = resourcesService.getDocumentPage(pageNum,pageSize);
        model.addAttribute("documentPageInfo",documents);
        return "/study/study";
    }

    @GetMapping(value = "/document/toDocPage/{id}")
    public String toDocPage(@PathVariable("id")Integer id, Model model){
        resourcesService.insertViewCount(id);
        Document document = resourcesService.getDocumentById(id);
        logger.info("根据id查到的document->{}", JSON.toJSON(document));
        List<DocumentsAndTag> documentsAndTags = document.getDocumentsAndTags();
        List<Tag> tags = new ArrayList<>();
        for (DocumentsAndTag documentsAndTag : documentsAndTags){
            tags.add(documentsAndTag.getTag());
        }
        model.addAttribute("document",document);
        model.addAttribute("tags",tags);
        return "/study/document";
    }

    @GetMapping(value = "/document/getAll")
    @ResponseBody
    public List<Document> getAllDocuments(){
        return resourcesService.getAllDocument();
    }
}
