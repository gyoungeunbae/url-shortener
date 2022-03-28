package com.ge.urlshortener.service;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.ge.urlshortener.domain.Url.makeRandomString;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url createUrl(String destination) {
        boolean isExist = this.isExistDestination(destination);
        if (isExist) {
            Url url = this.urlRepository.getUrl(destination);
            url.countRequest();
            return url;
        }

        String randomString = makeRandomString();
        boolean isExistRandom = isExistRandomString(randomString);
        while (isExistRandom == true) {
            randomString = makeRandomString();
            isExistRandom = isExistRandomString(randomString);
        }
        Url url = Url.of(destination, randomString);
        this.urlRepository.save(url);

        return url;
    }

    public Url findDestination(String shortenUrl) {
        Url url = this.urlRepository.findDestination(shortenUrl);
        return url;
    }

    public boolean isExistRandomString(String randomUrl) {
        Map<String, Url> store = this.urlRepository.getStore();
        for (Url url : store.values()) {
            String shortenUrl = url.getShortenUrl();
            if (shortenUrl.equals(randomUrl)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExistDestination(String address) {
        Map<String, Url> store = this.urlRepository.getStore();

        for (Url u : store.values()) {
            String url = u.getDestination();
            if (url.equals(address)) {
                return true;
            }
        }
        return false;
    }
}
