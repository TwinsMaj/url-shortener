package com.example.strategyzertakehome.counter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Counter {
    @Id
    private String id;
    private long count;

    public Counter(long count) {
        this.count = count;
    }
}
