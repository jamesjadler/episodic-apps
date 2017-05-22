package com.example.episodicevents;

import com.example.episodicevents.events.Event;
import com.example.episodicevents.events.data.MongoEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    MongoEventRepo eventRepo;

    @PostMapping("/")
    public Event postEvent(@RequestBody Event inputEvent){
        Event event = inputEvent;
        System.out.println("CONTROLLER:"+event);
        Event eventResponse = eventRepo.save(event);
        System.out.println("SAVED RESPONSE:"+eventResponse);
        return eventResponse;
    }
}
