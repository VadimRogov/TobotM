package org.example.tobot.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.example.tobot.service.CentralRussianBankService;
import org.example.tobot.dto.ValuteCursOnDate;
import org.example.tobot.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CentralRussianBankService centralRussianBankService;
    private final StatsService statsService;
    @GetMapping("/getCurrencies")
    public List<ValuteCursOnDate> getValuteCursOnDate() throws Exception {
        return centralRussianBankService.getCurrenciesFromCbr();
    }

    @GetMapping("/getCurrency/{code}")
    @ApiOperation(value = "Получение курса определенно валюты на текущий день")
    public ValuteCursOnDate getCourseForCurrency(@PathVariable String code) throws Exception {
        return centralRussianBankService.getCourseForCurrency(code);
    }

    @GetMapping("/getStats")
    @ApiOperation(value = "Получение количества пополнений, которые превышают определённую сумму")
    public int getStatsAbountIncomesThatGreater(@RequestParam(value = "amount") BigDecimal amount) {
        return statsService.getCountOfIncomesThatGreater(amount);
    }
}