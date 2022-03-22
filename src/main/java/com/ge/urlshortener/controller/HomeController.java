package com.ge.urlshortener.controller;

import com.ge.urlshortener.domain.Url;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {
    @GetMapping
    public String home(@ModelAttribute Url url) {
        return "home";
    }
}
