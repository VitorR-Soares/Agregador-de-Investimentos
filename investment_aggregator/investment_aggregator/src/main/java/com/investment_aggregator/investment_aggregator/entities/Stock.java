package com.investment_aggregator.investment_aggregator.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_stock")
public class Stock {

    @Id
    @Column(name = "stock_id")
    private String id;

    @Column(name = "description")
    private String description;
}
