package com.example.freelecwebservice.controller;

import com.example.freelecwebservice.dto.HelloResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void hello(@Autowired MockMvc mvc) throws Exception {
        String body = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(body));

    }

    @Test
    void hello2(@Autowired MockMvcTester mvcTester) {
        String body = "hello";

        assertThat(mvcTester.get().uri("/hello"))
                .hasStatusOk()
                .hasBodyTextEqualTo(body);
    }

    @Test
    void helloDto(@Autowired MockMvc mvc) throws Exception {
        String name = "test";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }

    @Test
    void helloDto2(@Autowired MockMvcTester mvcTester) {
        String name = "test";
        int amount = 1000;

        assertThat(mvcTester.get().uri("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .hasStatusOk()
                .hasBodyTextEqualTo(objectMapper.writeValueAsString(new HelloResponseDto(name, amount)));

    }
}