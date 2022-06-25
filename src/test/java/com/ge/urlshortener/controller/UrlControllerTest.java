package com.ge.urlshortener.controller;

import com.ge.urlshortener.domain.Url;
import com.ge.urlshortener.dto.RequestDTO;
import com.ge.urlshortener.service.UrlService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UrlController.class)
public class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlService urlService;

    private String destination;

    private Url url;

    private RequestDTO requestDTO;

    private Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        destination = "http://www.google.com";
        requestDTO = RequestDTO.builder().destination(destination).build();
        destination = requestDTO.destinationTransformer();
        url = Url.of(destination);
    }

    @Test
    @DisplayName("shorten url 생성")
    void testWithCreate() throws Exception {

        // given

        // when & then
        when(urlService.createUrl(destination)).thenReturn(url);
        mockMvc.perform(post("/urls/new")
                        .content(gson.toJson(requestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }

    @Test
    @DisplayName("redirect 테스트: 알수 없는 url")
    void testWithRedirectFail() throws Exception {

        // given
        String wrongUrl = "test";

        // when & then
        when(urlService.findDestination(wrongUrl)).thenReturn(null);
        mockMvc.perform(get("/to/{shorten}", "shorten")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("redirect 테스트: 성공")
    void testWithRedirect() throws Exception {

        // given
        given(urlService.findDestination(url.getShortenUrl())).willReturn(url);

        // when & then
        mockMvc.perform(get("/to/{shorten}", url.getShortenUrl())
                        .param("shorten", url.getShortenUrl())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("http://" + url.getDestination()));
    }
}
