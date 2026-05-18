package com.example.freelecwebservice.controller;

import com.example.freelecwebservice.dto.PostsSaveRequestDto;
import com.example.freelecwebservice.service.PostsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class IndexControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    PostsService postsService;

    @Test
    void index() {
        // given
        String title = "테스트 제목";
        String content = "테스트 본문";
        String author = "테스트 계정";

        postsService.save(PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        String url = "http://localhost:" + port + "/";

        // when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains(title);
        assertThat(responseEntity.getBody()).contains(author);

        /*mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(content().string(containsString(title)))
                .andExpect(content().string(containsString(author)));*/
    }

    @Test
    void postsSave() {
        String url = "http://localhost:" + port + "/posts/save";

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("등록");

        /*mockMvc.perform(get("/posts/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("posts-save"));*/
    }

    @Test
    void postsUpdate() {
        // given
        String title = "테스트 제목";
        String content = "테스트 본문";
        String author = "테스트 계정";

        Long updateId = postsService.save(PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        String url = "http://localhost:" + port + "/posts/update/" + updateId;

        // when
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains(title);
        assertThat(responseEntity.getBody()).contains(content);
        assertThat(responseEntity.getBody()).contains(author);

        /*mockMvc.perform(get("/posts/update/{id}", updateId))
                .andExpect(status().isOk())
                .andExpect(view().name("posts-update"))
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post",
                        hasProperty("id", is(updateId))))
                .andExpect(model().attribute("post",
                        hasProperty("title", is(title))))
                .andExpect(model().attribute("post",
                        hasProperty("content", is(content))))
                .andExpect(model().attribute("post",
                        hasProperty("author", is(author))))
                .andExpect(content().string(containsString("테스트 제목")))
                .andExpect(content().string(containsString("테스트 본문")))
                .andExpect(content().string(containsString("테스트 계정")));*/
    }
}