package com.example.episodicevents.events.rabbit;


import com.example.episodicevents.events.ProgressEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProgressPublisher {
    @Autowired
    RabbitTemplate rabbitTemplate;


    public void process(ProgressEvent progressEvent) {
        if (progressEvent.getEpisodeId() != null && progressEvent.getUserId() != null) {
            EpisodicProgressEvent episodicProgressEvent = EpisodicProgressEvent.builder()
                    .userId(progressEvent.getUserId())
                    .offset(progressEvent.getData().getOffset())
                    .episodeId(progressEvent.getEpisodeId())
                    .createdAt(progressEvent.getCreatedAt())
                    .build();
            System.out.println("SENDING PROGRESS EVENT TO RABBIT QUEUE:"+episodicProgressEvent);
            rabbitTemplate.convertAndSend("my-exchange", "my-routing-key", episodicProgressEvent);
        }

    }


}




