package com.ge.urlshortener.domain;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

public class Url {

    private long id;
    @NotEmpty
    @URL
    private String destination;
    private String shortenUrl;
    private int requestCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }

    public void setShortenUrl(String shortenUrl) {
        this.shortenUrl = shortenUrl;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }
}
