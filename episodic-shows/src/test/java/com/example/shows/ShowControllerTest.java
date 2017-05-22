package com.example.shows;


import com.example.episodes.Episode;
import com.example.episodes.EpisodesRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShowControllerTest {
    @Autowired
    ShowsRepo showsrepo;
    @Autowired
    EpisodesRepo episodesRepo;
    @Autowired
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        showsrepo.deleteAll();
        episodesRepo.deleteAll();
    }

    @Test
    public void postCreateShowTest() throws Exception {
        Long count = showsrepo.count();
        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("name", "show1");
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);
        System.out.println(json);
        MockHttpServletRequestBuilder request = post("/shows")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("show1")))
                .andDo(print());


        assertThat(showsrepo.count(), equalTo(count + 1));
    }

    @Test
    public void getShowListTest() throws Exception {
        showsrepo.save(Show.builder()
                .id(77L)
                .name("show1")
                .build());

        showsrepo.save(Show.builder()
                .id(78L)
                .name("show2")
                .build());

        List<Show> output = (List<Show>) showsrepo.findAll();
        System.out.println(showsrepo.findAll());
        ObjectMapper mapper = new ObjectMapper();
        String expectedString = mapper.writeValueAsString(output);
        System.out.println("Get Show Test Expecting:\n" + expectedString);
        mvc.perform(get("/shows")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedString))
                .andDo(print());

    }

    @Test
    public void getEpisodesListTest() throws Exception {
        //Insert example shows
        showsrepo.save(Show.builder()
                .name("show1")
                .build());

        showsrepo.save(Show.builder()
                .name("show2")
                .build());
        //Lookup one show id
        Show expectedShow =showsrepo.findByName("show1");
        System.out.println("Expected Show: " +expectedShow);
        //Assign 2 episodes to that id
        episodesRepo.save(Episode.builder()
                .seasonNumber(1)
                .episodeNumber(1)
                .showId(expectedShow.getId())
                .build());

        episodesRepo.save(Episode.builder()
                .seasonNumber(1)
                .episodeNumber(2)
                .showId(expectedShow.getId())
                .build());

        List<Episode> output = (List<Episode>) episodesRepo.findAll();
        System.out.println(episodesRepo.findAll());
        ObjectMapper mapper = new ObjectMapper();
        String expectedString = mapper.writeValueAsString(output);
        System.out.println("Get Episode Test Expecting:\n" + expectedString);
        mvc.perform(get("/shows/{id}/episodes",expectedShow.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedString))
                .andDo(print());

    }


    @Test
    public void postEpisodeToShowByIdTest()throws Exception{
        Show expectedShow = showsrepo.save(Show.builder()
                .name("show1")
                .build());
        System.out.println("Expected Show: " +expectedShow);
        Long count = episodesRepo.count();
        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("seasonNumber", 1L);
                put("episodeNumber", 2L);
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);
        System.out.println(json);
        MockHttpServletRequestBuilder request = post("/shows/{showId}/episodes", expectedShow.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seasonNumber", equalTo(1)))
                .andExpect(jsonPath("$.episodeNumber", equalTo(2)))
                .andExpect(jsonPath("$.title",equalTo("S1 E2")))
        .andDo(print());

        assertThat(episodesRepo.count(), equalTo(count + 1));    }


}
