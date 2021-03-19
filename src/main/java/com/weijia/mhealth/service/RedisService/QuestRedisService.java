package com.weijia.mhealth.service.RedisService;

import com.alibaba.fastjson.JSON;
import com.weijia.mhealth.entity.Student;
import com.weijia.mhealth.entity.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author Wei Jia
 * @Date 2021/3/18 10:53
 * @Version 1.0
 */
@Service
public class QuestRedisService {

    private final static Logger logger = LoggerFactory.getLogger(UserRedisService.class);

    @Autowired
    private RedisTemplate<String,Long> dateRedisTemplate;

    @Autowired
    private RedisTemplate<String,Tag> tagRedisTemplate;

    //得到dateList
    public List<Long> getDateList(){

        List<Long> dateList = dateRedisTemplate.opsForList().range("ques:dateList", 0, -1);
        logger.info("redis中存储的dateList->{}",dateList);
        return dateList;
    }

    //设置"ques:dateList"的过期时间
    public void setDateListExpire(){
        dateRedisTemplate.expire("ques:dateList",1, TimeUnit.MINUTES);
    }

    //将数据库中查到的日期保存在缓存中
    public void insertDates(List<Long> dates){
        for(Long date : dates){
            dateRedisTemplate.opsForList().leftPush("ques:dateList",date);
        }
    }

    //将tags插入缓存
    public void insertTags(List<Tag> tags){

        for (Tag tag:tags) {
            try{
                if(!isTagExist(tag)){
                    tagRedisTemplate.opsForList().leftPush("tags",tag);
                    tagRedisTemplate.expire("tags",1, TimeUnit.DAYS);
                }
            }catch (Exception e){
                logger.error("tag信息存入Redis失败");
                logger.error(String.valueOf(e.getCause()));
            }
        }
    }

    //检查该tag是否在redis中
    public Boolean isTagExist(Tag tag){
        logger.info("即将插入Redis中的tag is->{}", JSON.toJSON(tag));
        List<Tag> tags= tagRedisTemplate.opsForList().range("tags", 0, -1);
        assert tags != null : "tags is null";
        return tags.contains(tag);
    }
}
