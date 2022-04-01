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

        // when
        Url actual = Url.of(destination);

        // then
        Assertions.assertEquals(destination, actual.getDestination());
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
