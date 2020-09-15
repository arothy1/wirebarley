package com.arothy.wirebarley.currencylayer.exchangerate.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

@SpringBootTest
class ExchangeRateServiceImplImplTest {

    @Value("${currency-layer.uri}")
    private String uri;

    @Value("${currency-layer.access_key}")
    private String accessKey;

    @Test
    void getExchangeRate() {
        RestTemplate restTemplate = new RestTemplate();

        String source = "USD";
        String format = "1";
        String currencies = "KRW";

        String buildUri = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("access_key", accessKey)
                .queryParam("source", source)
                .queryParam("format", format)
                .queryParam("currencies", currencies)
                .toUriString();

        Map<String, String> responseMap = restTemplate.exchange(buildUri, HttpMethod.GET, null, Map.class)
                .getBody();

        Assert.isTrue(Objects.equals(responseMap.get("success"), true), "currency-layer connected");
    }
}