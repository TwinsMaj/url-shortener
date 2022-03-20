package com.example.strategyzertakehome.counter;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, Long> {
}
