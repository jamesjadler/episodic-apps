package com.example.episodicevents;

import com.example.episodicevents.events.Event;
import com.example.episodicevents.events.PlayEvent;
import com.example.episodicevents.events.data.MongoEventRepo;
import com.example.episodicevents.events.data.PlayData;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    MongoEventRepo eventRepo;
    @Autowired
    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        eventRepo.deleteAll();
    }

    //Post play,pause,fast-forward,rewind,progress,scrub
    @Test
    public void playTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse("2017-11-08T15:59:13.0091745");

        PlayData playData = new PlayData();
        playData.setOffset(0L);
        PlayEvent playEvent = new PlayEvent(
                null,
                52L,
                987L,
                456L,
                "2017-11-08T15:59:13.0091745",
                PlayData.builder()
                        .offset(0L)
                        .build()
        );

        String json = mapper.writeValueAsString(playEvent);
        System.out.println("INPUT JSON:\n" + json);
        mapper.readValue(json, Event.class);

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
        .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.type", equalTo("play")))
//                .andExpect(jsonPath("$.data.offset" ).exists())
//                .andExpect(jsonPath("$.data.startOffset" ).doesNotExist());
        System.out.println(eventRepo.findAll());
    }

    //Get Recent 20
}
