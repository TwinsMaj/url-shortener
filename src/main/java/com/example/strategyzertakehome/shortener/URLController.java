package com.example.strategyzertakehome.shortener;

import com.example.strategyzertakehome.error.TinyURLNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class URLController {
    private final URLService urlService;

    @PostMapping
    @RequestMapping("api/v1/url")
    public TinyURL makeShortUrl(@Valid @RequestBody TinyURL url) {
        String bigUrl = url.getBigURL();
        log.info("encoding " + bigUrl + "into base62 short URL");
        return urlService.encodeURL(bigUrl);
    }

    @GetMapping(path = "{encodedURL}")
    public TinyURL resolveShortURL(@PathVariable("encodedURL") String encodedURL) throws TinyURLNotFoundException {
//        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("https://www.yahoo.com")).build();
        return urlService.retrieveURLFrom(encodedURL);
    }
}
