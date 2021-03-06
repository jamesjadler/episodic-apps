package com.example.episodes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEpisodeResponse {
    private Long id;
    private int seasonNumber;
    private int episodeNumber;
    private String title;
}
