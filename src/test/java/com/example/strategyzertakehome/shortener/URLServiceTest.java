package com.example.strategyzertakehome.shortener;

import com.example.strategyzertakehome.Encoder.Base62Encoder;
import com.example.strategyzertakehome.counter.CounterService;
import com.example.strategyzertakehome.error.TinyURLNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class URLServiceTest {
    @Autowired
    private URLService urlService;

    @MockBean
    private URLRepository urlRepository;

    @MockBean
    private Base62Encoder base62Encoder;

    @MockBean
    private CounterService counterService;

    private TinyURL tinyURL = TinyURL.builder()
            .bigURL("http://www.yahoo.com")
            .encodedURL("1L9zQeA")
            .shortURL("maja.herokuapp.com/1L9zQeA")
            .created(LocalDateTime.now())
            .expiry(LocalDateTime.now().plus(Period.ofMonths(6)))
            .build();

    @BeforeEach
    void setUp() {
        when(urlRepository.insert(any(TinyURL.class))).thenReturn(tinyURL);
        when(urlRepository.findByEncodedURL("1L9zQeA")).thenReturn(Optional.of(tinyURL));
        when(base62Encoder.toBase62(100000000000L)).thenReturn("1L9zQeA");
        when(counterService.isCounterExhausted()).thenReturn(false);
        when(counterService.getCurrentCount()).thenReturn(100000001000L);
        Mockito.doNothing().when(counterService).updateCurrentCount();
    }

    @Test
    public void whenValidURLGiven_thenTinyURLReturned() {
        var bigURL = "http://www.yahoo.com";
        var expectedEncodedURL = "1L9zQeA";
        var expectedShortURL = "maja.herokuapp.com/1L9zQeA";

        var tinyURL = urlService.encodeURL(bigURL);

        assertEquals(tinyURL.getBigURL(), bigURL);
        assertEquals(tinyURL.getEncodedURL(), expectedEncodedURL);
        assertEquals(tinyURL.getShortURL(), expectedShortURL);
    }

    @Test
    public void whenEncodedURLGive_thenRetrieveTinyURL() throws TinyURLNotFoundException {
        var encodedURL = "1L9zQeA";
        var bigURL = "http://www.yahoo.com";
        var expectedEncodedURL = "1L9zQeA";
        var expectedShortURL = "maja.herokuapp.com/1L9zQeA";
        var tinyURL = urlService.retrieveURLFrom(encodedURL);

        assertEquals(tinyURL.getBigURL(), bigURL);
        assertEquals(tinyURL.getEncodedURL(), expectedEncodedURL);
        assertEquals(tinyURL.getShortURL(), expectedShortURL);
    }
}