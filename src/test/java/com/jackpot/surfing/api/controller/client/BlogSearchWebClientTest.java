package com.jackpot.surfing.api.controller.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jackpot.surfing.api.client.BlogSearchWebClient;
import java.net.URI;
import org.junit.jupiter.api.Test;

public class BlogSearchWebClientTest {
    private static final String EXPECTED = "expected";

    @Test
    void getBlogsTest() {
        // Given
        BlogSearchWebClient blogSearchWebClient = mock(BlogSearchWebClient.class);
        when(blogSearchWebClient.getBlogs(any(URI.class))).thenReturn(EXPECTED);

        // When
        String result = blogSearchWebClient.getBlogs(URI.create("https://test.com"));

        // Then
        assertEquals(EXPECTED, result);
    }
}
