package com.example.episodes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name="episodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Episode {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name="show_id")
    private Long showId;
    @Column(name="season_number")
    private int seasonNumber;
    @Column(name="episode_number")
    private int episodeNumber;
}
