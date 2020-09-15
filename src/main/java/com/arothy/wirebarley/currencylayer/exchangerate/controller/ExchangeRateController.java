package com.arothy.wirebarley.currencylayer.exchangerate.controller;

import com.arothy.wirebarley.currencylayer.exchangerate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ExchangeRateController {

    final ExchangeRateService exchangeRateService;

    @GetMapping
    ResponseEntity getExchangeRate() {
        try {
            return ResponseEntity.ok(exchangeRateService.getExchangeRate());
        } catch (Exception e) {
            log.error("{}", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

}
