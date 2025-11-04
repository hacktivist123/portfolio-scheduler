package org.inits.inits_portfolio_scheduler.data.repositories;


import org.inits.inits_portfolio_scheduler.data.models.Portfolio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PortfolioRepositoryTest {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @BeforeEach
    void setUp() {
        portfolioRepository.deleteAll();

        Portfolio portfolio = new Portfolio();
        portfolio.setOwnerEmail("john.doe@example.com");
        portfolio.setPortfolioName("Savings");
        portfolio.setTotalValue(BigDecimal.valueOf(100000));
        portfolio.setInterestRate(BigDecimal.valueOf(0.15));
        portfolioRepository.save(portfolio);
    }

    @Test
    void shouldFindSavedPortfolio() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        assertThat(portfolios).hasSize(1);
        assertThat(portfolios.get(0).getOwnerEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void shouldFindPortfolioById() {
        Portfolio saved = portfolioRepository.findAll().get(0);
        Portfolio found = portfolioRepository.findById(saved.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getPortfolioName()).isEqualTo("Savings");
    }
}