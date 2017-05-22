package com.example.viewings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="viewings")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Viewing {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name="show_id")
    private Long showId;
    @Column(name="episode_id")
    private Long episodeId;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    private int timecode;
}
