package com.example.viewings;

import com.example.episodes.Episode;
import com.example.episodes.EpisodesRepo;
import com.example.shows.ShowsRepo;
import com.example.users.UsersRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrCreateViewing {
    @Autowired
    UsersRepo usersRepo;

    @Autowired
    ViewingsRepo viewingsRepo;

    @Autowired
    EpisodesRepo episodesRepo;

    @Autowired
    ShowsRepo showsRepo;


    public void process(Viewing viewing,Long userId){
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
