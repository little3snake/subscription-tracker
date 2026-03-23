package com.little3snake.subscriptiontracker.dto;

import com.little3snake.subscriptiontracker.enums.BillingPeriod;
import com.little3snake.subscriptiontracker.enums.Category;
import com.little3snake.subscriptiontracker.enums.Currency;
import com.little3snake.subscriptiontracker.enums.SubscriptionStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class SubscriptionResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Currency currency;
    private BillingPeriod billingPeriod;
    private LocalDate startDate;
    private LocalDate nextPaymentDate;
    private Category category;
    private SubscriptionStatus status;
    private String description;
    private LocalDateTime createdAt;
}