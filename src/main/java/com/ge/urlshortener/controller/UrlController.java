package com.ge.urlshortener.controller;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class UrlController {

    private UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/urls/new", params = "destination")
    public String create(@Valid Url url, Model model) {
        Url created = this.urlService.createUrl(url.getDestination());
        model.addAttribute("createdUrl", created.getShortenUrl());
        return "/home";
    }

    @GetMapping(value = "/to/{shorten}")
    @ResponseBody
    public RedirectView redirectDestination(@PathVariable String shorten) {
        Url url = this.urlService.findDestination(shorten);
        if (url != null) {
            return new RedirectView(url.getDestination());
        }
        return new RedirectView("/");
    }
}
