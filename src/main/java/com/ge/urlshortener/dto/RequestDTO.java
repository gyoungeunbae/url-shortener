package com.ge.urlshortener.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class RequestDTO {
    @NotBlank
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String destination;

    @Tolerate
    RequestDTO() {}

    public String destinationTransformer() {
        if (destination.contains("http://")) {
            destination = destination.replaceAll("http://", "");
        } else if (destination.contains("https://")) {
            destination = destination.replaceAll("https://", "");
        }
        return destination;
    }
}

