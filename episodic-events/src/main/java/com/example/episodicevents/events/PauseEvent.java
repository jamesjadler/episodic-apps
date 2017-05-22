package com.example.episodicevents.events;

import com.example.episodicevents.events.data.PauseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PauseEvent extends Event{
    private PauseData data;

    public PauseEvent(String id, Long userId, Long showId, Long episodeId, LocalDateTime createdAt, PauseData data) {
        super(id, userId, showId, episodeId, createdAt);
        this.data = data;
    }
}
