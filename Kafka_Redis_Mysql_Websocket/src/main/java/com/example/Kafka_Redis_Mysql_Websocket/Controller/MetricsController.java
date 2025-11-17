package com.example.Kafka_Redis_Mysql_Websocket.Controller;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
    private final StringRedisTemplate redisTemplate;

    public MetricsController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/page-views")
    public Long getPageViews(@RequestParam String tenantId,
                             @RequestParam String page) {

        String key = "page_views:" + tenantId + ":" + page;

        String value = redisTemplate.opsForValue().get(key);
        return value == null ? 0L : Long.parseLong(value);
    }

}
