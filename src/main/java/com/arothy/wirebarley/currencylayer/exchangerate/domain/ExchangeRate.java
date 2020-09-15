package com.arothy.wirebarley.currencylayer.exchangerate.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ExchangeRate {

    String source;
    Map<String, String> quotes;
    String timestamp;   //version

}
