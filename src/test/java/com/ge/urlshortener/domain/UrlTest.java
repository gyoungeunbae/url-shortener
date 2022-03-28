package com.ge.urlshortener.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlTest {

    @Test
    void testWithOf() {
        // given
        String destination = "http://www.google.com";
        String randomString = "randomString";

        // when
        Url actual = Url.of(destination, randomString);

        // then
        Assertions.assertEquals(destination, actual.getDestination());
        Assertions.assertEquals(randomString, actual.getShortenUrl());
        Assertions.assertEquals(0, actual.getRequestCount());
    }

    @Test
    void testWithMakeRandomString() {
        // given

        // when
        String randomString = Url.makeRandomString();

        // then
        Assertions.assertNotNull(randomString);
    }
}
