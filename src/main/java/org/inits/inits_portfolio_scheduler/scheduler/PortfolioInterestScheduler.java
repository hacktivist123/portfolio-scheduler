package org.inits.inits_portfolio_scheduler.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inits.inits_portfolio_scheduler.services.PortfolioService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortfolioInterestScheduler {

    private final PortfolioService portfolioService;

    @Scheduled(cron = "${portfolio.interest.schedule}")
    public void runDailyInterestUpdate() {
        log.info("Running daily portfolio interest update...");
        portfolioService.updateDailyInterest();
    }
}