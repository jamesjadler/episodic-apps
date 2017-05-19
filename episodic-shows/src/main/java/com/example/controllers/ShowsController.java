package com.example.controllers;

import com.example.episodes.Episode;
import com.example.episodes.EpisodesRepo;
import com.example.episodes.PostEpisodeResponse;
import com.example.shows.Show;
import com.example.shows.ShowsRepo;
import com.example.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowsController {

    @Autowired
    ShowsRepo showsRepo;
    @Autowired
    EpisodesRepo episodesRepo;


    @PostMapping
    public Show createUser(@RequestBody Show show) {
        return showsRepo.save(show);

    }

    @GetMapping
    public List<Show> listUsers() {
        return (List<Show>) showsRepo.findAll();
    }

    @GetMapping("/{showId}/episodes")
    public List<Episode> listEpisodesByShowId(@PathVariable("showId") Long inputId) {
        return (List<Episode>) episodesRepo.findByShowId(inputId);
    }


    @PostMapping("/{showId}/episodes")
    public PostEpisodeResponse createEpisode(@RequestBody Episode episode, @PathVariable("showId") Long inputId) {
        episode.setShowId(inputId);
        System.out.println("Attempting to save episode:"+episode);
        Episode savedEpisode = episodesRepo.save(episode);
        return PostEpisodeResponse.builder()
                .id(savedEpisode.getId())
                .seasonNumber(savedEpisode.getSeasonNumber())
                .episodeNumber(savedEpisode.getEpisodeNumber())
                .title("S" + savedEpisode.getSeasonNumber() + " E" + savedEpisode.getEpisodeNumber())
                .build();
    }


}
