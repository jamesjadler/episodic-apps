package com.example.episodicevents;

import com.example.episodicevents.events.Event;
import com.example.episodicevents.events.ProgressEvent;
import com.example.episodicevents.events.data.MongoEventRepo;
import com.example.episodicevents.events.rabbit.ProgressPublisher;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    MongoEventRepo eventRepo;

    @Autowired
    ProgressPublisher progressPublisher;

    @PostMapping("/")
    public Event postEvent(@RequestBody Event inputEvent) {
        Event event = inputEvent;


        System.out.println("CONTROLLER:" + event);
        Event eventResponse = eventRepo.save(event);
        System.out.println("SAVED RESPONSE:" + eventResponse);
        if(inputEvent instanceof ProgressEvent){
            progressPublisher.process((ProgressEvent)inputEvent);
        }


        return eventResponse;
    }

    @GetMapping("/recent")
    public List<Event> getTop20Events() {
        System.out.println("FETCHING TOP 20 RECORDS");
        List<Event> top20 = eventRepo.findTop20ByOrderByCreatedAtDesc();
        System.out.println(top20);
        return top20;
    }
}
