package com.example.episodicevents;

import com.example.episodicevents.events.*;
import com.example.episodicevents.events.data.*;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
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

        PlayEvent playEvent = new PlayEvent(
                null,
                52L,
                987L,
                456L,
                dateTime,
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo("play")))
                .andExpect(jsonPath("$.data.offset").exists())
                .andExpect(jsonPath("$.data.startOffset").doesNotExist());
        System.out.println(eventRepo.findAll());
    }

    @Test
    public void pauseTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse("2017-11-08T15:59:13.0091745");

        PauseEvent pauseEvent = new PauseEvent(
                null,
                52L,
                987L,
                456L,
                dateTime,
                PauseData.builder()
                        .offset(0L)
                        .build()
        );

        String json = mapper.writeValueAsString(pauseEvent);
        System.out.println("INPUT JSON:\n" + json);
        mapper.readValue(json, Event.class);

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo("pause")))
                .andExpect(jsonPath("$.data.offset").exists())
                .andExpect(jsonPath("$.data.startOffset").doesNotExist());
        System.out.println(eventRepo.findAll());
    }

    @Test
    public void progressTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse("2017-11-08T15:59:13.0091745");

        ProgressEvent progressEvent = new ProgressEvent(
                null,
                52L,
                987L,
                456L,
                dateTime,
                ProgressData.builder()
                        .offset(0L)
                        .build()
        );

        String json = mapper.writeValueAsString(progressEvent);
        System.out.println("INPUT JSON:\n" + json);
        mapper.readValue(json, Event.class);

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo("progress")))
                .andExpect(jsonPath("$.data.offset").exists())
                .andExpect(jsonPath("$.data.startOffset").doesNotExist());
        System.out.println(eventRepo.findAll());
    }

    @Test
    public void FastForwardTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse("2017-11-08T15:59:13.0091745");

        FastForwardEvent fastForwardEvent = new FastForwardEvent(
                null,
                52L,
                987L,
                456L,
                dateTime,
                FastForwardData.builder()
                        .startOffset(0L)
                        .endOffset(20L)
                        .speed(1.5)
                        .build()
        );

        String json = mapper.writeValueAsString(fastForwardEvent);
        System.out.println("INPUT JSON:\n" + json);
        mapper.readValue(json, Event.class);

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo("fastForward")))
                .andExpect(jsonPath("$.data.offset").doesNotExist())
                .andExpect(jsonPath("$.data.startOffset").exists())
                .andExpect(jsonPath("$.data.startOffset", is(0)))
                .andExpect(jsonPath("$.data.endOffset").exists())
                .andExpect(jsonPath("$.data.endOffset", is(20)))
                .andExpect(jsonPath("$.data.speed").exists())
                .andExpect(jsonPath("$.data.speed", is(1.5)));
        System.out.println(eventRepo.findAll());
    }

    @Test
    public void rewindTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse("2017-11-08T15:59:13.0091745");

        RewindEvent rewindEvent = new RewindEvent(
                null,
                52L,
                987L,
                456L,
                dateTime,
                RewindData.builder()
                        .startOffset(0L)
                        .endOffset(20L)
                        .speed(1.5)
                        .build()
        );

        String json = mapper.writeValueAsString(rewindEvent);
        System.out.println("INPUT JSON:\n" + json);
        mapper.readValue(json, Event.class);

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo("rewind")))
                .andExpect(jsonPath("$.data.offset").doesNotExist())
                .andExpect(jsonPath("$.data.startOffset").exists())
                .andExpect(jsonPath("$.data.startOffset", is(0)))
                .andExpect(jsonPath("$.data.endOffset").exists())
                .andExpect(jsonPath("$.data.endOffset", is(20)))
                .andExpect(jsonPath("$.data.speed").exists())
                .andExpect(jsonPath("$.data.speed", is(1.5)))
                .andReturn();

        System.out.println(eventRepo.findAll());
    }

    @Test
    public void scrubTest() throws Exception {
        LocalDateTime dateTime = LocalDateTime.parse("2017-11-08T15:59:13.0091745");

        ScrubEvent scrubEvent = new ScrubEvent(
                null,
                52L,
                987L,
                456L,
                dateTime,
                ScrubData.builder()
                        .startOffset(0L)
                        .endOffset(20L)
                        .build()
        );

        String json = mapper.writeValueAsString(scrubEvent);
        System.out.println("INPUT JSON:\n" + json);
        mapper.readValue(json, Event.class);

        MockHttpServletRequestBuilder request = post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", equalTo("scrub")))
                .andExpect(jsonPath("$.data.offset").doesNotExist())
                .andExpect(jsonPath("$.data.startOffset").exists())
                .andExpect(jsonPath("$.data.startOffset", is(0)))
                .andExpect(jsonPath("$.data.endOffset").exists())
                .andExpect(jsonPath("$.data.endOffset", is(20)))
                .andExpect(jsonPath("$.data.speed").doesNotExist())
                .andReturn();

        System.out.println(eventRepo.findAll());
    }

    @Test
    public void getRecentTest() throws Exception {
        for (int i = 0; i < 30; i++) {
            ProgressEvent progressEvent = new ProgressEvent(
                    null,
                    52L,
                    987L,
                    456L,
                    LocalDateTime.now(),
                    ProgressData.builder()
                            .offset(Integer.toUnsignedLong(i))
                            .build()

            );
            eventRepo.save(progressEvent);
        }
        System.out.println("SAVED 30 PROGRESS EVENTS:");
        System.out.println(eventRepo.findAll());

        mvc.perform(get("/recent"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(20)));

    }

}
