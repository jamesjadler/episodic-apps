package com.example.episodicevents.events.data;


import com.example.episodicevents.events.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoEventRepo extends MongoRepository<Event,String>{
    List<Event> findTop20ByOrderByCreatedAtDesc();
}
