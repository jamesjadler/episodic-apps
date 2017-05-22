package com.example.episodicevents.events.data;


import com.example.episodicevents.events.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoEventRepo extends MongoRepository<Event,String>{
}
