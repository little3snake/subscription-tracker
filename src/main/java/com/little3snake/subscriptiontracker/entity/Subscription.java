package com.little3snake.subscriptiontracker.entity;

import com.little3snake.subscriptiontracker.enums.BillingPeriod;
import com.little3snake.subscriptiontracker.enums.Currency;
import com.little3snake.subscriptiontracker.enums.Category;
import com.little3snake.subscriptiontracker.enums.SubscriptionStatus;

import jakarta.persistence.*; // jpa lib, connect java and db
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // it's table in db
@Table(name = "subscriptions") // table name, without table name will be like class
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillingPeriod billingPeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
