package com.example.users;


import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {
    @Autowired
    UsersRepo usersRepo;

    @Autowired
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        usersRepo.deleteAll();
    }

    @Test
    public void postCreateWorks() throws Exception {
        Long count = usersRepo.count();
        Map<String, Object> payload = new HashMap<String, Object>() {
            {
                put("email", "joe@example.com");
            }
        };

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payload);
        System.out.println(json);
        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("joe@example.com")));

        assertThat(usersRepo.count(), equalTo(count + 1));
    }

    @Test
    public void getListTest() throws Exception {
        usersRepo.save(User.builder()
                .id(77L)
                .email("dumb@email.com")
                .build());

        usersRepo.save(User.builder()
                .id(78L)
                .email("dumb2@email.com")
                .build());

        List<User> output = (List<User>) usersRepo.findAll();
        System.out.println(usersRepo.findAll());
        ObjectMapper mapper = new ObjectMapper();
        String expectedString = mapper.writeValueAsString(output);
        System.out.println("Get Test Expecting:\n" + expectedString);
        mvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedString));
    }

}
