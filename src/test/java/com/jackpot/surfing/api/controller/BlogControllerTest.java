package com.jackpot.surfing.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackpot.surfing.api.dto.BlogSearchCondition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BlogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void searchBlogsPagingTest_invalidParameters() throws Exception {
        // 누락된 query 값
        mockMvc.perform(post("/api/v1/blogs/search/paging")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BlogSearchCondition(null, "accuracy", 1, 10))))
            .andExpect(status().isBadRequest());

        // 잘못된 sort 값
        mockMvc.perform(post("/api/v1/blogs/search/paging")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BlogSearchCondition("spring", "invalid", 1, 10))))
            .andExpect(status().isBadRequest());

        // 음수로 된 page 값
        mockMvc.perform(post("/api/v1/blogs/search/paging")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BlogSearchCondition("spring", "accuracy", -1, 10))))
            .andExpect(status().isBadRequest());

        // 음수로 된 size 값
        mockMvc.perform(post("/api/v1/blogs/search/paging")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new BlogSearchCondition("spring", "accuracy", 1, -1))))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void getPopularKeywordListTest_invalidParameter() throws Exception {
        mockMvc.perform(get("/api/v1/blogs/popular-search")
                .param("size", String.valueOf(-1)))
            .andExpect(status().isBadRequest());
    }
}
