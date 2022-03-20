package com.example.strategyzertakehome.shortener;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface URLRepository extends MongoRepository<TinyURL, String> {
    Optional<TinyURL> findByEncodedURL(String encodedURL);
}
