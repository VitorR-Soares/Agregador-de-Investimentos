package com.investment_aggregator.investment_aggregator.controllers;

import com.investment_aggregator.investment_aggregator.controllers.dto.CreateStockDTO;
import com.investment_aggregator.investment_aggregator.services.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stock")
public class StockController {

    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    @PostMapping("/create")
    public ResponseEntity<Void> createStock(@RequestBody CreateStockDTO createStockDTO){

        stockService.createStock(createStockDTO);

        return ResponseEntity.noContent().build();

    }


}
