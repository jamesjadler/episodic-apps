package com.example.episodes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodicProgressEvent {
    private Long userId;
    private Long episodeId;
    private LocalDateTime createdAt;
    private Long offset;

}
