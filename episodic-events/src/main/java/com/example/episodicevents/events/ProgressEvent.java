package com.example.episodicevents.events;

import com.example.episodicevents.events.data.ProgressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressEvent extends Event{
 private ProgressData data;

 public ProgressEvent(String id, Long userId, Long showId, Long episodeId, LocalDateTime createdAt, ProgressData data) {
  super(id, userId, showId, episodeId, createdAt);
  this.data = data;
 }
}
