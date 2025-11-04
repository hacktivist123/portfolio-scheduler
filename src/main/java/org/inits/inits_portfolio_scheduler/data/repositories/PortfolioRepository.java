package org.inits.inits_portfolio_scheduler.data.repositories;

import org.inits.inits_portfolio_scheduler.data.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
}