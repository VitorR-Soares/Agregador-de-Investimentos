package com.investment_aggregator.investment_aggregator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Embeddable
public class AccountStockId {

    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "account_id")
    private UUID accountId;

    public AccountStockId() {
    }

    public AccountStockId(String stockId, UUID accountId) {
        this.stockId = stockId;
        this.accountId = accountId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
