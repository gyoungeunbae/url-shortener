package com.ge.urlshortener.service;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.repository.UrlRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url createUrl(String destination) {
        boolean isExist = this.urlRepository.isExistDestination(destination);

        if (isExist) {
            Url url = this.urlRepository.getUrl(destination);
            int count = url.getRequestCount();
            url.setRequestCount(count++);
            return url;
        }
        String random = this.makeRandomString();
        Url url = new Url();
        url.setShortenUrl(random);
        url.setDestination(destination);

        this.urlRepository.save(url);

        return url;
    }

    public Url findDestination(String shortenUrl) {
        Url url = this.urlRepository.findDestination(shortenUrl);
        return url;
    }

    private String makeRandomString() {
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
        String randomString = stringBuilder.toString();

        if (this.urlRepository.isExistRandomString(randomString)) {
            randomString = this.makeRandomString();
        }

        return randomString;
    }
    /*
    private String getHostAddress() {
        String port = environment.getProperty("local.server.port");
        String hostAddress = InetAddress.getLoopbackAddress().getHostAddress();
        return "http://"+ hostAddress + ":" + port + "/";
    }*/
}
