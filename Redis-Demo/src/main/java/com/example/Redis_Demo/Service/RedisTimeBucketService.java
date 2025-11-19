package com.example.Redis_Demo.Service;

import com.example.Redis_Demo.Entity.ClickEvent;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Long.sum;


@Service
public class RedisTimeBucketService {


    private final StringRedisTemplate redis;

    public RedisTimeBucketService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public void storeEvent(ClickEvent e) {

        long tsSec = e.getTimeStamp() / 1000;

        redis.opsForValue().increment("count:" + tsSec);

        redis.opsForZSet().incrementScore("top-pages", e.getPageName(), 1);
        redis.opsForZSet().incrementScore("top-buttons", e.getButtonId(), 1);

        redis.expire("count:" + tsSec, Duration.ofSeconds(120));
        redis.expire("top-pages", Duration.ofSeconds(120));
        redis.expire("top-buttons", Duration.ofSeconds(120));
    }

    public Map<String, Object> getStats() {

        long now = System.currentTimeMillis() / 1000;

        Map<String, Object> map = new HashMap<>();
        map.put("last10Seconds", sum(now, 10));
        map.put("last30Seconds", sum(now, 30));
        map.put("last1Minute", sum(now, 60));

        map.put("topPages", top("top-pages"));
        map.put("topButtons", top("top-buttons"));

        return map;
    }

    private long sum(long now, int range) {
        long total = 0;
        for (int i = 0; i < range; i++) {
            String v = redis.opsForValue().get("count:" + (now - i));
            if (v != null) total += Long.parseLong(v);
        }
        return total;
    }

    private Map<String, Double> top(String key) {
        Map<String, Double> map = new LinkedHashMap<>();

        Set<String> items = redis.opsForZSet().reverseRange(key, 0, 4);
        if (items == null) return map;

        for (String it : items) {
            Double score = redis.opsForZSet().score(key, it);
            map.put(it, score);
        }
        return map;
    }

}
