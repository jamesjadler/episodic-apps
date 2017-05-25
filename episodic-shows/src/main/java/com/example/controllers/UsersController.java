package com.example.controllers;

import com.example.episodes.Episode;
import com.example.episodes.EpisodesRepo;
import com.example.shows.ShowsRepo;
import com.example.users.User;
import com.example.users.UsersRepo;
import com.example.viewings.RecentlyWatched;
import com.example.viewings.UpdateOrCreateViewing;
import com.example.viewings.Viewing;
import com.example.viewings.ViewingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    ViewingsRepo viewingsRepo;
    @Autowired
    EpisodesRepo episodesRepo;
    @Autowired
    ShowsRepo showsRepo;
    @Autowired
    UpdateOrCreateViewing updateOrCreateViewing;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return usersRepo.save(user);
    }

    @GetMapping
    public List<User> listUsers() {
        return (List<User>) usersRepo.findAll();
    }

    @PatchMapping("/{id}/viewings")
    public void updateViewing(@RequestBody Viewing viewing, @PathVariable("id") Long userId) {
        updateOrCreateViewing.process(viewing,userId);


    }
@GetMapping("/{id}/recently-watched")
    public List<RecentlyWatched> getRecentViewings(@PathVariable("id")Long userId){
    List<RecentlyWatched> recentlyWatchedList= new ArrayList<>();
    List<Viewing> viewingList = viewingsRepo.findByUserIdOrderByUpdatedAt(userId);
    System.out.println(viewingList);

    viewingList.forEach(viewing -> recentlyWatchedList.add(RecentlyWatched.builder()
            .episode(episodesRepo.findById(viewing.getEpisodeId()))
            .show(showsRepo.findOne(viewing.getShowId()))
            .timecode(viewing.getTimecode())
            .updatedAt(viewing.getUpdatedAt())
            .build()));
    System.out.println(recentlyWatchedList);
    return recentlyWatchedList;
}

}
