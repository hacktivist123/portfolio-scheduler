package org.inits.inits_portfolio_scheduler.utils;

import org.inits.inits_portfolio_scheduler.data.models.Portfolio;
import org.inits.inits_portfolio_scheduler.dtos.requests.PortfolioRequest;
import org.inits.inits_portfolio_scheduler.dtos.responses.PortfolioResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PortfolioMapper {

    public static Portfolio toEntity(PortfolioRequest request) {
        if (request == null) return null;
        return Portfolio.builder()
                .ownerEmail(request.getOwnerEmail())
                .portfolioName(request.getPortfolioName())
                .totalValue(request.getTotalValue())
                .interestRate(request.getInterestRate())
                .lastUpdated(LocalDateTime.now())
                .build();
    }

    public static PortfolioResponse toResponse(Portfolio response) {
        if (response == null) return null;
        return PortfolioResponse.builder()
                .id(response.getId())
                .ownerEmail(response.getOwnerEmail())
                .portfolioName(response.getPortfolioName())
                .totalValue(response.getTotalValue())
                .interestRate(response.getInterestRate())
                .lastUpdated(response.getLastUpdated())
                .build();
    }

    public static List<PortfolioResponse> toResponseDTOList(List<Portfolio> responses) {
        return responses.stream()
                .map(PortfolioMapper::toResponse)
                .collect(Collectors.toList());
    }

}
