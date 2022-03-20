package com.example.strategyzertakehome.shortener;

import com.example.strategyzertakehome.error.TinyURLNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class URLController {
    private final URLService urlService;

    @CrossOrigin
    @PostMapping
    @RequestMapping("api/v1/url")
    public TinyURL makeShortUrl(@Valid @RequestBody TinyURL url) {
        String bigUrl = url.getBigURL();
        log.info("encoding " + bigUrl + "into base62 short URL");
        return urlService.encodeURL(bigUrl);
    }

    @GetMapping(path = "{encodedURL}")
    public ResponseEntity<Void> resolveShortURL(@PathVariable("encodedURL") String encodedURL) throws TinyURLNotFoundException {
        TinyURL tinyURL = urlService.retrieveURLFrom(encodedURL);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(tinyURL.getBigURL())).build();
    }
}
