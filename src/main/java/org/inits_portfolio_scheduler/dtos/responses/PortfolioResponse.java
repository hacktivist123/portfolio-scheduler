package org.inits_portfolio_scheduler.dtos.responses;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {

    private UUID id;
    private String ownerEmail;
    private String portfolioName;
    private BigDecimal totalValue;
    private BigDecimal interestRate;
    private LocalDateTime lastUpdated;
}
