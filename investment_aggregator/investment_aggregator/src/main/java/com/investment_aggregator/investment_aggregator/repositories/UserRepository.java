package com.investment_aggregator.investment_aggregator.repositories;

import com.investment_aggregator.investment_aggregator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
