INSERT INTO portfolio (id, owner_email, total_value, portfolio_name, interest_rate, last_updated)
VALUES
    (RANDOM_UUID(), 'john.doe@example.com', 100000.00, 'Savings', 0.15, CURRENT_TIMESTAMP),
    (RANDOM_UUID(), 'jane.smith@example.com', 250000.00, 'Real Estate', 0.15, CURRENT_TIMESTAMP);
