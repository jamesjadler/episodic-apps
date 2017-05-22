package com.example.episodicevents.events;

import com.example.episodicevents.events.data.ScrubData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrubEvent extends Event{
private ScrubData data;

    public ScrubEvent(String id, Long userId, Long showId, Long episodeId, LocalDateTime createdAt, ScrubData data) {
        super(id, userId, showId, episodeId, createdAt);
        this.data = data;
    }
}
