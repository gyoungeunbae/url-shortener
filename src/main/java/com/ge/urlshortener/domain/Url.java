package com.ge.urlshortener.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.util.Random;
import java.util.UUID;

@Getter
@Builder
public class Url {
    @Setter
    private Long id;
    @NotEmpty
    @URL
    private String destination;
    private String shortenUrl;
    @Setter
    private Integer requestCount;

    public static Url of(String destination) {
        String randomString = makeRandomString();
        return builder()
                .destination(destination)
                .requestCount(0)
                .shortenUrl(randomString)
                .build();
    }

    public Url countRequest() {
        int count = this.requestCount++;
        this.setRequestCount(count);
        return this;
    }

    public static String makeRandomString() {
        return UUID.randomUUID().toString()
                .replace("-", "");
    }
}
