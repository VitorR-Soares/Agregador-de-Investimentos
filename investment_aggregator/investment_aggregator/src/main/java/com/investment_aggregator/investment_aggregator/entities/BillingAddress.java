package com.investment_aggregator.investment_aggregator.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_billing_address")
public class BillingAddress {

    @Id
    @Column(name = "billing_address_id")
    private UUID id;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private int number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id")
    @MapsId
    private Account account;

}
