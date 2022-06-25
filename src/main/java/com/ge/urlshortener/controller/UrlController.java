package com.ge.urlshortener.controller;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.dto.RequestDTO;
import com.ge.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final String PROTOCOL = "http://";

    private final UrlService urlService;

    @PostMapping(value = "/urls/new")
    public ResponseEntity create(@RequestBody @Valid RequestDTO url) {
        String requestedUrl = url.destinationTransformer();
        Url created = this.urlService.createUrl(requestedUrl);
        return ResponseEntity.status(HttpStatus.OK).body(created);
    }

    @GetMapping(value = "/to/{shorten}")
    @ResponseBody
    public RedirectView redirectDestination(@PathVariable String shorten) {
        Url url = this.urlService.findDestination(shorten);
        if (url != null) {
            return new RedirectView(PROTOCOL + url.getDestination());
        }
        return new RedirectView("/");
    }
}
