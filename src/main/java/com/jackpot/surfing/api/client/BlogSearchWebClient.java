package com.jackpot.surfing.api.client;

import java.net.URI;

public interface BlogSearchWebClient {
    public <T> T getBlogs(URI uri);
}
