package com.ge.urlshortener.repository;

import com.ge.urlshortener.domain.Url;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UrlRepository {

    private static Map<String, Url> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public Long save(Url url) {
        url.setId(sequence++);
        store.put(url.getDestination(), url);
        return url.getId();
    }

    public Url getUrl(String destination) {
        return store.get(destination);
    }

    public static Map<String, Url> getStore() {
        return store;
    }

    public Url findDestination(String shortenUrl) {
        for (Url url : store.values()) {
            String shorten = url.getShortenUrl();
            if (shorten.equals(shortenUrl)) {
                return url;
            }
        }
        return null;
    }
}
