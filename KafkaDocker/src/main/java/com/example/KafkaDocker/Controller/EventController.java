package com.example.KafkaDocker.Controller;

import com.example.KafkaDocker.Service.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produce")
public class EventController {

    private final EventProducer producer;

    @Autowired
    public EventController(EventProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String publish(@RequestBody String msg) {
        producer.sendEvent(msg);
        return "Message sent!";
    }
}
