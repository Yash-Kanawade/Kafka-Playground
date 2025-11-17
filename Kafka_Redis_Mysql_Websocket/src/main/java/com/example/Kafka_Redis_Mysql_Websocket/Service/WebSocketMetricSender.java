package com.example.Kafka_Redis_Mysql_Websocket.Service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketMetricSender {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketMetricSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendPageViewUpdate(Object data) {
        messagingTemplate.convertAndSend("/topic/page-views", data);
    }
}
