package com.ge.urlshortener.service;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UrlServiceTest {

    @InjectMocks
    private UrlService service;

    @Mock
    private UrlRepository repository;

    private String destination;

    private Url url;

    @BeforeEach
    void setUp() {
        destination = "http://www.google.com";
        url = Url.of(destination, "randomString");
    }

    @Test
    @DisplayName("신규 shorten url 생성")
    void testWithCreateUrl() {

        // given

        // when
        Url actual = this.service.createUrl(destination);

        // then
        Assertions.assertEquals(destination, actual.getDestination());
        Assertions.assertEquals(0, actual.getRequestCount());
    }

    @Test
    @DisplayName("findDestination")
    void testWithFindDestination() {

        // given

        // when
        when(repository.findDestination(url.getShortenUrl())).thenReturn(url);
        Url actual = this.service.findDestination(url.getShortenUrl());

        // then
        Assertions.assertEquals(url.getDestination(), actual.getDestination());
        Assertions.assertEquals(url.getShortenUrl(), actual.getShortenUrl());
        Assertions.assertEquals(url.getRequestCount(), actual.getRequestCount());
    }
}
