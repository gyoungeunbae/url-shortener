package com.ge.urlshortener.service;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public Url createUrl(String destination) {
        Url url = Url.of(destination);
        this.urlRepository.save(url);
        return url;
    }

    public Url findDestination(String shortenUrl) {
        Url url = Optional.ofNullable(this.urlRepository.findDestinationByUrl(shortenUrl))
                .orElseThrow(() -> {
                    throw new RuntimeException("알수 없는 url 입니다.");
                }
        );
        return url;
    }

}

