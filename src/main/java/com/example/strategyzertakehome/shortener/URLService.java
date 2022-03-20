package com.example.strategyzertakehome.shortener;

import com.example.strategyzertakehome.Encoder.Base62Encoder;
import com.example.strategyzertakehome.counter.CounterService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        var encodedURL = base62Encoder.toBase62(counterService.getCurrentCount());
        var shortURL = BASE_URL + encodedURL;

        var shortURLData = urlRepository.insert(new TinyURL(encodedURL, shortURL, bigURL, LocalDateTime.now(), getExpiry()));
        counterService.updateCurrentCount();
        log.info(shortURLData.getBigURL());
        return shortURLData;
    }

    private LocalDateTime getExpiry() {
        return LocalDateTime.now().plus(Period.ofMonths(6));
    }
}
