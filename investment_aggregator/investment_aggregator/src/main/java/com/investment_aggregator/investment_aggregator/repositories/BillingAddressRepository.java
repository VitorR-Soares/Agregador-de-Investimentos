package com.investment_aggregator.investment_aggregator.repositories;

import com.investment_aggregator.investment_aggregator.entities.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}
