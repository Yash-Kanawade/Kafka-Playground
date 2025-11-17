package com.example.Kafka_Redis_Mysql_Websocket.Controller;

import com.example.Kafka_Redis_Mysql_Websocket.DTO.EventDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/events")
public class EventController {
    private final KafkaTemplate<String, EventDTO> kafkaTemplate;

    public EventController(KafkaTemplate<String, EventDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String publish(@RequestBody EventDTO eventDTO) {
        if(eventDTO.getTimestamp() == null){
            eventDTO.setTimestamp(Instant.now());
        }
        kafkaTemplate.send("events-topic", eventDTO);
        return "OK";
    }

}
