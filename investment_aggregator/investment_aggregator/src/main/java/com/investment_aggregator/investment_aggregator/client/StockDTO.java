package com.investment_aggregator.investment_aggregator.client;

import org.springframework.web.bind.annotation.ResponseBody;

public record StockDTO(double regularMarketPrice) {
}
