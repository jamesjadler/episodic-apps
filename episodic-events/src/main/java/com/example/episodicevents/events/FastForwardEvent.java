package com.example.episodicevents.events;

import com.example.episodicevents.events.data.FastForwardData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastForwardEvent extends Event{
    private FastForwardData data;

//    public FastForwardEvent(String id, Long userId, Long showId, Long episodeId, String createdAt, FastForwardData data) {
//        super(id, userId, showId, episodeId, createdAt);
//        this.data = data;
//    }

}
