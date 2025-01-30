package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.repositories.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
}
