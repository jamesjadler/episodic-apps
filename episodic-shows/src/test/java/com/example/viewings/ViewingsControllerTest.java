package com.example.viewings;

import com.example.episodes.Episode;
import com.example.episodes.EpisodesRepo;
import com.example.shows.Show;
import com.example.shows.ShowsRepo;
import com.example.users.User;
import com.example.users.UsersRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ViewingsControllerTest {
    @Autowired
    ShowsRepo showsrepo;
    @Autowired
    EpisodesRepo episodesRepo;
    @Autowired
    ViewingsRepo viewingsRepo;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        showsrepo.deleteAll();
        episodesRepo.deleteAll();
        viewingsRepo.deleteAll();
        usersRepo.deleteAll();
    }

    @Test
    public void patchViewingByIdTest() throws Exception {
        User expectedUser =usersRepo.save(User.builder()
                .email("email@dish.com")
                .build());
        System.out.println("Expected user: "+expectedUser);
        Show expectedShow = showsrepo.save(Show.builder()
                .name("show1")
                .build());
        System.out.println("Expected Show: " + expectedShow);
        Episode expectedEpisode = episodesRepo.save(Episode.builder()
                .showId(expectedShow.getId())
                .episodeNumber(1)
                .seasonNumber(1)
                .build());
        System.out.println("Expected Episode: "+expectedEpisode);
        Long count = episodesRepo.count();
        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("episodeId", expectedEpisode.getId());
                put("updatedAt", "2017-05-04T11:45:34.9182");
                put("timecode", 79);

            }
        };

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);
        System.out.println("JSON CONTENT FOR PATCH:\n"+json);

        //Perform the first call, no patch target should be found. So a new viewings will be created
        MockHttpServletRequestBuilder request = patch("/users/{id}/viewings", expectedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());

        //Second time an update will be done on the first viewing
        Map<String, Object> payload2 = new HashMap<String, Object>() {
            {
                put("episodeId", expectedEpisode.getId());
                put("updatedAt", "2017-05-04T11:50:34.9182");
                put("timecode", 80);

            }
        };
        String json2 = mapper.writeValueAsString(payload2);
        System.out.println("JSON CONTENT FOR PATCH2:\n"+json);

        MockHttpServletRequestBuilder request2 = patch("/users/{id}/viewings", expectedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json2);

        mvc.perform(request2)
                .andExpect(status().isOk())
                .andDo(print());
        System.out.println("Inserted value: \n"+viewingsRepo.findByUserId(expectedUser.getId()));
        System.out.println(payload2);
        System.out.println(viewingsRepo.findAll());
    }

}
