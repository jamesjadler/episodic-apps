package com.example.controllers;

import com.example.episodes.Episode;
import com.example.episodes.EpisodesRepo;
import com.example.users.User;
import com.example.users.UsersRepo;
import com.example.viewings.Viewing;
import com.example.viewings.ViewingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("INPUTS: VIEWING:" + viewing + " ID:" + userId);
        Viewing foundViewing = viewingsRepo.findByUserId(userId);
        Episode foundEpisode = episodesRepo.findById(viewing.getEpisodeId());
        Viewing patch = viewing;
        if (foundViewing != null) {
            if (foundEpisode != null) {
                System.out.println("FOUND PATCH TARGET: " + foundViewing);
                patch.setId(foundViewing.getId());
                patch.setUserId(userId);
                patch.setShowId(foundEpisode.getShowId());
                System.out.println("REPLACING PATCH TARGET WITH: " + patch);
                viewingsRepo.save(patch);
            } else{
                System.out.println("FOUND VIEWING BUT NO VALID EPISODE FOUND");
            }
        } else {
            System.out.println("NO PATCH TARGET FOUND: ");
            if (foundEpisode != null) {
                patch.setUserId(userId);
                patch.setShowId(foundEpisode.getShowId());
                System.out.println("CREATING VIEWING WITH: " + patch);
                viewingsRepo.save(patch);
            } else {
                System.out.println(" NO VIEWING FOUND AND NO MATCHING EPISODE FOUND");
            }
        }
    }
}
