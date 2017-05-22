package com.example.episodicevents.events;

import com.example.episodicevents.events.data.RewindData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewindEvent extends Event{
    private RewindData data;

    public RewindEvent(String id, Long userId, Long showId, Long episodeId, LocalDateTime createdAt, RewindData data) {
        super(id, userId, showId, episodeId, createdAt);
        this.data = data;
    }
}
