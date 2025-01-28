package com.investment_aggregator.investment_aggregator.repositories;

import com.investment_aggregator.investment_aggregator.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
