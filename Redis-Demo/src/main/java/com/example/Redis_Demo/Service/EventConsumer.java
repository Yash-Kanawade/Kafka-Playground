package com.example.Redis_Demo.Service;

import com.example.Redis_Demo.Entity.ClickEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {
    private final RedisTimeBucketService redis;

    public EventConsumer(RedisTimeBucketService redis) {
        this.redis = redis;
    }

    @KafkaListener(topics = "analytics-events", groupId = "analytics-grp")
    public void consume(ClickEvent event) {
        redis.storeEvent(event);
    }

}
