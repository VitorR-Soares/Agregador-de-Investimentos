package com.investment_aggregator.investment_aggregator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class AccountStockId {

    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "account_id")
    private UUID accountId;

}
