package com.example.episodicevents.events.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FastForwardData {
    private Long startOffset;
    private Long endOffset;
    private Long speed;

}
