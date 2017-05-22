package com.example.viewings;


import com.example.episodes.Episode;
import com.example.shows.Show;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
public class RecentlyWatched {
private Show show;
private Episode episode;
private LocalDateTime updatedAt;
private Long timecode;
}
