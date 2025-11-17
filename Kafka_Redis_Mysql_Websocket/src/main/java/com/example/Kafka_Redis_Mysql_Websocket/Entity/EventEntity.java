package com.example.Kafka_Redis_Mysql_Websocket.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name="events")
@Data
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tenantId;
    private String eventType;
    private String page;
    private Instant timestamp;

}
