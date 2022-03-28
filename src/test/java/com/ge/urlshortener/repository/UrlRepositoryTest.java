package com.ge.urlshortener.repository;

import com.ge.urlshortener.domain.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class UrlRepositoryTest {

    @InjectMocks
    private UrlRepository urlRepository;

    private String destination;

    private Url url;

    private Map<String, Url> store;

    @BeforeEach
    void setUp() {
        destination = "http://www.google.com";
        url = Url.of(destination, "randomString");
        store = new ConcurrentHashMap<>();
        store.put(destination, url);
    }

    @Test
    @DisplayName("db에 저장")
    void testWithSave() {
        // given

        // when
        Long actualId = this.urlRepository.save(url);

        // then
        Assertions.assertNotNull(actualId);
    }

    @Test
    @DisplayName("destination으로 검색")
    void testWithGetUrl() {
        // given

        // when
        Url actual = this.urlRepository.findDestination(url.getShortenUrl());

        // then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(url.getDestination(), actual.getDestination());
        Assertions.assertEquals(url.getShortenUrl(), actual.getShortenUrl());
    }
}
