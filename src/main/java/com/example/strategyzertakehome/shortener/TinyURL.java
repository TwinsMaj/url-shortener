package com.example.strategyzertakehome.shortener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TinyURL implements Serializable {
    @Id
    private String id;
    private String encodedURL;
    private String shortURL;
    @URL(message = "please provide valid URL")
    private String bigURL;
    private LocalDateTime created;
    private LocalDateTime expiry;

    public TinyURL(String encodedURL, String shortURL, String bigURL, LocalDateTime created, LocalDateTime expiry) {
        this.encodedURL = encodedURL;
        this.shortURL = shortURL;
        this.bigURL = bigURL;
        this.created = created;
        this.expiry = expiry;
    }
}
