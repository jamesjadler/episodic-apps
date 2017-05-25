package com.example.viewings;

import com.example.episodes.Episode;
import com.example.episodes.EpisodesRepo;
import com.example.episodes.EpisodicProgressEvent;
import com.example.shows.ShowsRepo;
import com.example.users.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrCreateViewingFromEpisodicProgressEvent {
    @Autowired
    UsersRepo usersRepo;

    @Autowired
    ViewingsRepo viewingsRepo;

    @Autowired
    EpisodesRepo episodesRepo;

    @Autowired
    ShowsRepo showsRepo;


    public void process(EpisodicProgressEvent progressEvent){
        if (progressEvent.getEpisodeId()!=null && progressEvent.getUserId() !=null) {
            System.out.println("INPUTS: VIEWING:" + progressEvent + " ID:" + progressEvent.getUserId());
            Viewing foundViewing = viewingsRepo.findByUserId(progressEvent.getUserId());
            Episode foundEpisode = episodesRepo.findById(progressEvent.getEpisodeId());

            Viewing patch = Viewing.builder().build();
            if (foundViewing != null) {
                if (foundEpisode != null) {
                    System.out.println("FOUND PATCH TARGET: " + foundViewing);
                    patch=foundViewing;
                    patch.setUpdatedAt(progressEvent.getCreatedAt());
                    patch.setTimecode(progressEvent.getOffset());
                    System.out.println("REPLACING PATCH TARGET WITH: " + patch);
                    viewingsRepo.save(patch);
                } else {
                    System.out.println("FOUND VIEWING BUT NO VALID EPISODE FOUND");
                }
            } else {
                System.out.println("NO PATCH TARGET FOUND: ");
                if (foundEpisode != null) {
                    patch.setUserId(progressEvent.getUserId());
                    patch.setShowId(foundEpisode.getShowId());
                    patch.setTimecode(progressEvent.getOffset());
                    patch.setEpisodeId(progressEvent.getEpisodeId());
                    patch.setUpdatedAt(progressEvent.getCreatedAt());
                    System.out.println("CREATING VIEWING WITH: " + patch);
                    viewingsRepo.save(patch);
                } else {
                    System.out.println(" NO VIEWING FOUND AND NO MATCHING EPISODE FOUND");
                }
            }
        }

    }
}
