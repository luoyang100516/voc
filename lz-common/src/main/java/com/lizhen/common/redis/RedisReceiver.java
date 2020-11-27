package com.lizhen.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 接收redis发布消息
 */

//@Component
public class RedisReceiver  {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisReceiver.class);
    /**
     * 接收消息
     *
     * @param message
     */
    public void receiverMessage(String message) {

        try {
            LOGGER.info(message);
        } catch (Exception e) {
            LOGGER.error("失败, 消息体 : {}", message);
            e.printStackTrace();
        }
    }
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
//         MessageListenerAdapter adapter = new MessageListenerAdapter(this,"receiverMessage2");
//        // MessageListenerAdapter adapter2 = new MessageListenerAdapter(this,"receiverMessage");
//        container.addMessageListener(adapter, new PatternTopic("web:del"));
        return container;
    }
}
