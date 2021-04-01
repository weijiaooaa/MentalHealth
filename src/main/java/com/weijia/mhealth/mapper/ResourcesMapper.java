package com.weijia.mhealth.mapper;

import com.weijia.mhealth.entity.Document;
import com.weijia.mhealth.entity.DocumentsAndTag;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @Author Wei Jia
 * @Date 2021/3/29 18:18
 * @Version 1.0
 */
public interface ResourcesMapper {

    @Insert("insert into documents(title, content, gmt_create, gmt_modified, creator,url) " +
            "values (#{title}, #{content}, #{gmtCreate}, #{gmtModified}, #{creator},#{url})")
    void insertDocument(Document document);

    @Select("select id from documents where title = #{title} and creator = #{creator}")
    Integer getDocumentByTitleAndCreator(String title, String creator);

    @Insert("insert into documents_and_tags(document_id,tag_id) values(#{documentId},#{tagId})")
    void insertToTagAndDocument(Integer tagId, Integer documentId);

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "documentsAndTags",many = @Many(select = "com.weijia.mhealth.mapper.ResourcesMapper.getDocumentsAndTagByDocumentId",fetchType=FetchType.DEFAULT)),
    })
    @Select("select * from documents order by gmt_create DESC")
    List<Document> getAllDocument();

    @Results({
            @Result(column = "tag_id",property = "tag",one = @One(select = "com.weijia.mhealth.mapper.QuestionMapper.getTagById",fetchType=FetchType.DEFAULT)),
    })
    @Select("select * from documents_and_tags where document_id = #{documentId}")
    List<DocumentsAndTag> getDocumentsAndTagByDocumentId(Integer documentId);

    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "id",property = "documentsAndTags",many = @Many(select = "com.weijia.mhealth.mapper.ResourcesMapper.getDocumentsAndTagByDocumentId",fetchType=FetchType.DEFAULT)),
    })
    @Select("select * from documents where id = #{id}")
    Document getDocumentById(Integer id);

    @Update("update documents set view_count = view_count + #{viewCount} , gmt_modified = #{updateTime} where id = #{id}")
    void insertViewCount(Integer id, int viewCount, long updateTime);
}
