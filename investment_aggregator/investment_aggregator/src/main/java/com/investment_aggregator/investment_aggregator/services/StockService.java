package com.investment_aggregator.investment_aggregator.services;

import com.investment_aggregator.investment_aggregator.controllers.dto.CreateStockDTO;
import com.investment_aggregator.investment_aggregator.entities.Stock;
import com.investment_aggregator.investment_aggregator.repositories.StockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void createStock(CreateStockDTO dto){

        Stock newStock = new Stock(
                dto.id(),
                dto.description()
        );

        stockRepository.save(newStock);

    }

}
