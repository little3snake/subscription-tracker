package com.little3snake.subscriptiontracker.dto;

import com.little3snake.subscriptiontracker.enums.BillingPeriod;
import com.little3snake.subscriptiontracker.enums.Category;
import com.little3snake.subscriptiontracker.enums.Currency;
import com.little3snake.subscriptiontracker.enums.SubscriptionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SubscriptionRequest {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Currency must not be null")
    private Currency currency;

    @NotNull(message = "Billing period must not be null")
    private BillingPeriod billingPeriod;

    @NotNull(message = "Category must not be null")
    private Category category;

    @NotNull(message = "Start date must not be null")
    private LocalDate startDate;

    private SubscriptionStatus status;

    private String description;
}