package org.inits_portfolio_scheduler.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inits_portfolio_scheduler.data.models.Portfolio;
import org.inits_portfolio_scheduler.data.repositories.PortfolioRepository;
import org.inits_portfolio_scheduler.dtos.requests.PortfolioRequest;
import org.inits_portfolio_scheduler.dtos.responses.PortfolioResponse;
import org.inits_portfolio_scheduler.exceptions.PortfolioNotFoundException;
import org.inits_portfolio_scheduler.utils.PortfolioMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j

public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Value("${portfolio.interest.rate:0.15}")
    private BigDecimal annualInterestRate;
    @Override
    public List<PortfolioResponse> getAllPortfolios() {
        return PortfolioMapper.toResponseDTOList(portfolioRepository.findAll());
    }

    @Override
    public PortfolioResponse getPortfolioById(UUID id) {
        Portfolio portfolio = portfolioRepository.findById(id)
                .orElseThrow(() -> new PortfolioNotFoundException("Portfolio not found with id: " + id));
        return PortfolioMapper.toResponse(portfolio);
    }

    @Override
    public PortfolioResponse createPortfolio(PortfolioRequest request) {
        Portfolio portfolio = PortfolioMapper.toEntity(request);
        Portfolio saved = portfolioRepository.save(portfolio);
        return PortfolioMapper.toResponse(saved);
    }

    @Override
    public void updateDailyInterest() {
        List<Portfolio> portfolios = portfolioRepository.findAll();
        if (portfolios.isEmpty()) {
            log.warn("No portfolios found for interest update.");
            return;
        }

        BigDecimal dailyRate = annualInterestRate.divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);

        for (Portfolio portfolio : portfolios) {
            BigDecimal interestRate = portfolio.getTotalValue().multiply(dailyRate);
            portfolio.setTotalValue(portfolio.getTotalValue().add(interestRate));
            portfolio.setLastUpdated(LocalDateTime.now());
        }

        portfolioRepository.saveAll(portfolios);
        log.info("Daily interest updated for {} portfolios", portfolios.size());
    }
}
