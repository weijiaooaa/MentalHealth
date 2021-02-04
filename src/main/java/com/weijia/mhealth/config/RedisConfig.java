package com.weijia.mhealth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weijia.mhealth.entity.Question;
import com.weijia.mhealth.entity.Tag;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.time.Duration;

/**
 * @author: wangxiaoxi
 * @create: 2020-03-07 16:55
 **/
@Configuration
public class RedisConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60))
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(
            name = {"redisTemplate"}
    )
    public RedisTemplate<String, Question> quesRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 解决key的序列化方式，使用String。
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        //解决hash和value的序列化方式，使用string
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);

        // 解决value的序列化方式，使用Json。其中的日期再另外处理。
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 如果序列化对象中有日期格式，可以自定义日期的序列化类，处理日期的序列化方式。其他需要序列化的类型，也可以按此处理。
        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(DateTime.class, new JodaDateTimeJsonSerializer());
//        simpleModule.addDeserializer(DateTime.class, new JodaDateTimeJsonDeserializer());

        // 在序列化中增加类信息，否则无法反序列化。
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        objectMapper.registerModule(simpleModule);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(
            name = {"redisTemplate"}
    )
    public RedisTemplate<String, Tag> tagRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 解决key的序列化方式，使用String。
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);

        //解决hash和value的序列化方式，使用string
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);

        // 解决value的序列化方式，使用Json。其中的日期再另外处理。
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 如果序列化对象中有日期格式，可以自定义日期的序列化类，处理日期的序列化方式。其他需要序列化的类型，也可以按此处理。
        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(DateTime.class, new JodaDateTimeJsonSerializer());
//        simpleModule.addDeserializer(DateTime.class, new JodaDateTimeJsonDeserializer());

        // 在序列化中增加类信息，否则无法反序列化。
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        objectMapper.registerModule(simpleModule);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

}
