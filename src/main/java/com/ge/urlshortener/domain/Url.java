package com.ge.urlshortener.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import java.util.Random;

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

    public static Url of(String destination, String randomString) {
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
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String number = "0123456789";

        String combined = upper + lower + number;

        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        int length = 7;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(combined.length());
            char randomChar = combined.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
