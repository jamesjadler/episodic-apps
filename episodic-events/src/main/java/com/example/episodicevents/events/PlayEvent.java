package com.example.episodicevents.events;

import com.example.episodicevents.events.data.PlayData;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PlayEvent extends Event{
    private PlayData data;

    public PlayEvent( String id, Long userId, Long showId, Long episodeId, String createdAt, PlayData data) {
        super(id, userId, showId, episodeId, createdAt);
        this.data = data;
    }

}
