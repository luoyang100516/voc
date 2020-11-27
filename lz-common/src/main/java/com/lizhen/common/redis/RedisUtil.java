package com.lizhen.common.redis;


import com.lizhen.common.constant.Constants;
import com.lizhen.common.exceptions.CustomException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 */
@Component
public class RedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    private static RedisTemplate redisTemplate;


    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }


    /**
     * 获取所有value
     * @param command
     * @return
     */
    public static Collection getValues(String command) {
        Set keys = redisTemplate.keys(command);
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 获取所有key
     *
     * @return
     */
    public static Set getKeys(String command) {
        return redisTemplate.keys(command);
    }

    /**
     * 所有缓存个数
     *
     * @return
     */
    public static int getSize(String command) {
        return redisTemplate.keys(command).size();
    }

    /**
     * 清空所有缓存,慎用
     */
    public static void clear(String command) {
        redisTemplate.keys(command).clear();
    }

    /**
     * 是否存在
     *
     * @param key
     * @return
     */
    public static boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public static Boolean remove(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 添加缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static Object set(String key, String value,long time, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, time,timeUnit);
        } catch (Exception e) {
            LOGGER.error("添加缓存失败,key : {}, value: {}", key, value);
            throw new CustomException(String.format("添加缓存失败,key : %s, value: %s", key, value));
        }
        return Constants.OK;
    }

    public static Object set(String key, String value,long time) {
        try {
            redisTemplate.opsForValue().set(key, value, time,TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            LOGGER.error("添加缓存失败,key : {}, value: {}", key, value);
            throw new CustomException(String.format("添加缓存失败,key : %s, value: %s", key, value));
        }
        return Constants.OK;
    }

    public static Object set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            LOGGER.error("添加缓存失败,key : {}, value: {}", key, value);
            throw new CustomException(String.format("添加缓存失败,key : %s, value: %s", key, value));
        }
        return Constants.OK;
    }



    public static boolean publishMessage(String topic,String msg){
        try {
            redisTemplate.convertAndSend(topic,msg);
        } catch (Exception e) {
            LOGGER.error("发布消息失败,topic : {}, msg: {}", topic, msg);
            throw new CustomException(String.format("发布消息失败,topic : %s, msg: %s", topic, msg));
        }
        return true;
    }

}
