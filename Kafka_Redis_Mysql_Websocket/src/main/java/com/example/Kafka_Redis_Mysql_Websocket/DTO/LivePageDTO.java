package com.example.Kafka_Redis_Mysql_Websocket.DTO;

import lombok.Data;

@Data
public class LivePageDTO {
    private String tenantId;
    private String page;
    private Long count;
}
