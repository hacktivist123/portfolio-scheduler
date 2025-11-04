package org.inits_portfolio_scheduler.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.inits_portfolio_scheduler.data.repositories.PortfolioRepository;
import org.inits_portfolio_scheduler.dtos.responses.PortfolioResponse;
import org.inits_portfolio_scheduler.utils.PortfolioMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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



}
