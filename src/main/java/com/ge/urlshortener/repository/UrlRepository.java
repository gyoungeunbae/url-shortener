package com.ge.urlshortener.repository;

import com.ge.urlshortener.domain.Url;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UrlRepository {

    @Getter
    private static final Map<String, Url> store = new ConcurrentHashMap<>();
    @Getter @Setter
    private static long sequence = 0L;

    public Long save(Url url) {
        String randomString = url.getShortenUrl();

        boolean isExistRandom = isExistRandomString(randomString);
        while (isExistRandom) {
            url = Url.of(url.getDestination());
            isExistRandom = isExistRandomString(url.getShortenUrl());
        }
        synchronized (UrlRepository.class) {
            url.setId(sequence++);
            store.put(url.getDestination(), url);
        }

        return url.getId();
    }

    public Url findDestinationByUrl(String shortenUrl) {
        for (Url url : store.values()) {
            String shorten = url.getShortenUrl();
            if (shorten.equals(shortenUrl)) {
                return url;
            }
        }
        return null;
    }

    protected Url getUrlById(Long id) {
        for (Url url : store.values()) {
            Long urlId = url.getId();
            if (urlId.equals(id)) {
                return url;
            }
        }
        return null;
    }

    private boolean isExistRandomString(String randomUrl) {
        for (Url url : store.values()) {
            String shortenUrl = url.getShortenUrl();
            if (shortenUrl.equals(randomUrl)) {
                return true;
            }
        }
        return false;
    }
}
