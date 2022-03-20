package com.example.strategyzertakehome.shortener;

import com.example.strategyzertakehome.Encoder.Base62Encoder;
import com.example.strategyzertakehome.counter.CounterService;
import com.example.strategyzertakehome.error.TinyURLNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class URLService {
    private final URLRepository urlRepository;
    private final CounterService counterService;
    private final Base62Encoder base62Encoder;
    private final String BASE_URL = "maja.herokuapp.com/";

    public TinyURL encodeURL(String bigURL) {
        if(counterService.isCounterExhausted()) {
            counterService.initializeCounter();
        }

        String encodedURL = base62Encoder.toBase62(counterService.getCurrentCount());
        String shortURL = BASE_URL + encodedURL;

        TinyURL shortURLData = urlRepository.insert(new TinyURL(encodedURL, shortURL, bigURL, LocalDateTime.now(), getExpiry()));
        counterService.updateCurrentCount();
        log.info(shortURLData.getBigURL());
        return shortURLData;
    }

    @Cacheable(cacheNames = {"urldata"}, key = "#encodedURL")
    public TinyURL retrieveURLFrom(String encodedURL) throws TinyURLNotFoundException {
        log.info("i got here");
        TinyURL tinyUrl = fetchURL(encodedURL);

        if(isTinyURLExpired(tinyUrl.getExpiry())) {
            deleteURLWith(encodedURL, tinyUrl);
            String msg = "Tiny url " + encodedURL + "is expired";
            throw new TinyURLNotFoundException(msg);
        }

        return tinyUrl;
    }

    @CacheEvict(value = "urldata", key = "#encodedURL")
    public void deleteURLWith(String encodedUrl, TinyURL tinyURL) {
        urlRepository.delete(tinyURL);
    }

    private TinyURL fetchURL(String encodedURL) throws TinyURLNotFoundException {
        return urlRepository.findByEncodedURL(encodedURL)
                .orElseThrow(() -> {
                    String msg = "Tiny url " + encodedURL + " does not exist";
                    log.error(msg);
                    return new TinyURLNotFoundException(msg);
                });
    }

    private LocalDateTime getExpiry() {
        return LocalDateTime.now().plus(Period.ofMonths(6));
    }
    private boolean isTinyURLExpired(LocalDateTime expiry) {
        return LocalDateTime.now().isAfter(expiry);
    }
}
