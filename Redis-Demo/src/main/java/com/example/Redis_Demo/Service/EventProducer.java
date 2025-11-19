package com.example.Redis_Demo.Service;

import com.example.Redis_Demo.Entity.ClickEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private final KafkaTemplate<String,ClickEvent>  kafkaTemplate;
    public EventProducer(KafkaTemplate<String, ClickEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(ClickEvent event){
        kafkaTemplate.send("analytics-events",event);
    }
}
