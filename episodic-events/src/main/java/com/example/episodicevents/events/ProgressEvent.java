package com.example.episodicevents.events;

import com.example.episodicevents.events.data.ProgressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressEvent extends Event{
 private ProgressData data;

// public ProgressEvent(String id, Long userId, Long showId, Long episodeId, String createdAt, ProgressData data) {
//  super(id, userId, showId, episodeId, createdAt);
//  this.data = data;
// }
}
