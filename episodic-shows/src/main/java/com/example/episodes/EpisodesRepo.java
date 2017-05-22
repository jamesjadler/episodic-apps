package com.example.episodes;


import com.example.shows.Show;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EpisodesRepo extends CrudRepository<Episode,Long>{
    List<Episode> findByShowId(Long showId);
    Episode findById(Long episodeId);

}
