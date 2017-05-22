package com.example.episodicevents.events;

import com.example.episodicevents.events.data.FastForwardData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastForwardEvent extends Event{
    private FastForwardData data;

    public FastForwardEvent(String id, Long userId, Long showId, Long episodeId, LocalDateTime createdAt, FastForwardData data) {
        super(id, userId, showId, episodeId, createdAt);
        this.data = data;
    }
}
