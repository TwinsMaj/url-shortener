package com.example.strategyzertakehome.shortener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class URLController {
    private final URLService urlService;

    @PostMapping
    @RequestMapping("api/v1/url")
    public TinyURL makeShortUrl(@RequestBody TinyURL url) {
        var bigUrl = url.getBigURL();
        log.info("encoding " + bigUrl + "into base62 short URL");
        return urlService.encodeURL(bigUrl);
    }
}
