package com.arothy.wirebarley.currencylayer.exchangerate.service;

import com.arothy.wirebarley.currencylayer.exchangerate.domain.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Value("${currency-layer.uri}")
    private String uri;

    @Value("${currency-layer.access_key}")
    private String accessKey;

    @Override
    public ExchangeRate getExchangeRate() {

        RestTemplate restTemplate = new RestTemplate();

        String source = "USD";
        String format = "1";
        String currencies = "KRW,JPY,PHP";

        String buildUri = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("access_key", accessKey)
                .queryParam("source", source)
                .queryParam("format", format)
                .queryParam("currencies", currencies)
                .toUriString();

        Map<String, Object> responseMap = restTemplate.exchange(buildUri, HttpMethod.GET, null, Map.class)
                .getBody();

        String timestamp = String.valueOf(responseMap.get("timestamp"));
        Map<String, String> quotes = (Map<String, String>)responseMap.get("quotes");
        Map<String, String> formattedQuotes = quotes.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> getFormattedValue(entry.getValue())));

        ExchangeRate exchangeRate = ExchangeRate.builder()
                .timestamp(timestamp)
                .source(source)
                .quotes(formattedQuotes)
                .build();

        return exchangeRate;
    }

    private String getFormattedValue(Object inputValue) {
        DecimalFormat formatter = new DecimalFormat("###,###.##");
        return formatter.format(inputValue);
    }
}
