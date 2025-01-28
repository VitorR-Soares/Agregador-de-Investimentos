package com.investment_aggregator.investment_aggregator.repositories;

import com.investment_aggregator.investment_aggregator.entities.AccountStock;
import com.investment_aggregator.investment_aggregator.entities.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
