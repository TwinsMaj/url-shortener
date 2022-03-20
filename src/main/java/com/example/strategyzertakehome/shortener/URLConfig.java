package com.example.strategyzertakehome.shortener;

import com.example.strategyzertakehome.Encoder.Base62Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URLConfig {

    @Bean
    Base62Encoder base64Encoder() {
        return new Base62Encoder();
    }
}
