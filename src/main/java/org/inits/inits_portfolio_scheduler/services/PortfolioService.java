package org.inits.inits_portfolio_scheduler.services;

import org.inits.inits_portfolio_scheduler.dtos.requests.PortfolioRequest;
import org.inits.inits_portfolio_scheduler.dtos.responses.PortfolioResponse;

import java.util.List;
import java.util.UUID;

public interface PortfolioService {
    List<PortfolioResponse> getAllPortfolios();
    PortfolioResponse getPortfolioById(UUID id);
    PortfolioResponse createPortfolio(PortfolioRequest request);
    void updateDailyInterest();
}
