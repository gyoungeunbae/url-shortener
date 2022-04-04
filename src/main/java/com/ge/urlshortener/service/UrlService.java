package com.ge.urlshortener.service;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url createUrl(String destination) {
        Url url = Url.of(destination);
        this.urlRepository.save(url);

        return url;
    }

    public Url findDestination(String shortenUrl) {
        Url url = this.urlRepository.findDestination(shortenUrl);
        return url;
    }

}

