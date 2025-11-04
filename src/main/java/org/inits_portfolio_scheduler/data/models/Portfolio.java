package org.inits_portfolio_scheduler.data.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String ownerEmail;
    private String portfolioName;
    private BigDecimal totalValue;
    private BigDecimal interestRate;
    private LocalDateTime lastUpdated;
}
