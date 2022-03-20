package com.example.strategyzertakehome.counter;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
@RequiredArgsConstructor
public class CounterService {

    private final CounterRepository counterRepository;
    private Long currentCount;
    private Long initialCount;
    private final Long COUNTER_INTERVAL = 1000L;

    @Transactional
    public void initializeCounter() {
        var counter = counterRepository.findAll().stream().findFirst().get();

        setInitialCount(counter.getCount() + COUNTER_INTERVAL);
        setCurrentCount(getInitialCount() - COUNTER_INTERVAL);

        counter.setCount(getInitialCount());
        counterRepository.save(counter);
    }

    public boolean isCounterExhausted() {
        return getCurrentCount() == getInitialCount();
    }

    public void updateCurrentCount() {
        setCurrentCount(getCurrentCount() + 1);
    }
}