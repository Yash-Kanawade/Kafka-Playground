package com.example.Redis_Demo.Controller;

import com.example.Redis_Demo.Entity.ClickEvent;
import com.example.Redis_Demo.Service.EventProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin
public class EventController {

    private final EventProducer eventProducer;

    public EventController(EventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    @PostMapping("/click")
    public String sendEvent(@RequestBody ClickEvent event){
        event.setTimeStamp(System.currentTimeMillis());
        eventProducer.sendEvent(event);
        return "OK";
    }
}

