package com.example.episodicevents.events;

import com.example.episodicevents.events.data.RewindData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewindEvent extends Event{
    private RewindData data;

//    public RewindEvent(String id, Long userId, Long showId, Long episodeId, String createdAt, RewindData data) {
//        super(id, userId, showId, episodeId, createdAt);
//        this.data = data;
//    }
}
