package com.example.Kafka_Redis_Mysql_Websocket.DTO;

import lombok.Data;

import java.time.Instant;

@Data
public class EventDTO {
    private String tenantId;
    private String eventType;
    private String page;
    private Instant timestamp;
}
