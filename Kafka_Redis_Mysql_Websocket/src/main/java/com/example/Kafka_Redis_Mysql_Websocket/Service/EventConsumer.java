package com.example.Kafka_Redis_Mysql_Websocket.Service;

import com.example.Kafka_Redis_Mysql_Websocket.DTO.EventDTO;
import com.example.Kafka_Redis_Mysql_Websocket.DTO.LivePageDTO;
import com.example.Kafka_Redis_Mysql_Websocket.Entity.EventEntity;
import com.example.Kafka_Redis_Mysql_Websocket.Repository.EventRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventConsumer {
    private final EventRepository eventRepository;
    private final int BATCH_SIZE = 5;
    private final List<EventEntity> buffer = new ArrayList<>();
    private final StringRedisTemplate stringRedisTemplate;
    private final WebSocketMetricSender wsSender;

    public EventConsumer(EventRepository eventRepository, StringRedisTemplate stringRedisTemplate, WebSocketMetricSender wsSender) {
        this.eventRepository = eventRepository;
        this.stringRedisTemplate = stringRedisTemplate;
        this.wsSender = wsSender;
    }

    @KafkaListener(topics = "events-topic",groupId = "analytics-group")
    public void consume(EventDTO eventDTO) {
        System.out.println("Received event from Kafka topic: " + eventDTO);

        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType(eventDTO.getEventType());
        eventEntity.setPage(eventDTO.getPage());
        eventEntity.setTimestamp(eventDTO.getTimestamp());
        eventEntity.setTenantId(eventDTO.getTenantId());

        buffer.add(eventEntity);
        System.out.println("Event added in buffer , buffer size: " + buffer.size());
        if (buffer.size() >= BATCH_SIZE) {
            eventRepository.saveAll(buffer);
            System.out.println("Buffer Inserted");
            buffer.clear();
        }
        String key = "page_views:"+eventDTO.getTenantId()+":"+eventDTO.getPage();
        Long newCount = stringRedisTemplate.opsForValue().increment(key);

        LivePageDTO live = new LivePageDTO();
        live.setTenantId(eventDTO.getTenantId());
        live.setPage(eventDTO.getPage());
        live.setCount(newCount);

        wsSender.sendPageViewUpdate(live);

        System.out.println("Redis updated , key : " + key);
    }

}
