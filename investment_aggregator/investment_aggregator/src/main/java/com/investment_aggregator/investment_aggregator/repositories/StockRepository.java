package com.investment_aggregator.investment_aggregator.repositories;

import com.investment_aggregator.investment_aggregator.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}
