package com.example.Redis_Demo.Controller;


import com.example.Redis_Demo.Service.RedisTimeBucketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class AnalyticsWebSocketPublisher {

    private final RedisTimeBucketService redis;
    private final SimpMessagingTemplate template;

    public AnalyticsWebSocketPublisher(RedisTimeBucketService redis, SimpMessagingTemplate template) {
        this.redis = redis;
        this.template = template;
    }

    @Scheduled(fixedRate = 1000)
    public void pushStats() {
        template.convertAndSend("/topic/analytics", redis.getStats());
    }
}