package org.inits.inits_portfolio_scheduler.dtos.requests;

import java.math.BigDecimal;
import lombok.*;

@Data
public class PortfolioRequest {
    private String ownerEmail;
    private String portfolioName;
    private BigDecimal totalValue;
    private BigDecimal interestRate;

}
