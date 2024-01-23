package org.example.tobot.service;

import lombok.RequiredArgsConstructor;
import org.example.tobot.repository.StatsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final StatsRepository statsRepository;

    public int getCountOfIncomesThatGreater(BigDecimal amount) {
        return statsRepository.getCountOfIncomesThatGreaterThan(amount);
    }
}
