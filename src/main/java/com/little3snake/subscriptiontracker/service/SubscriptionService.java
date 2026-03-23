package com.little3snake.subscriptiontracker.service;

import com.little3snake.subscriptiontracker.dto.SubscriptionResponse;
import com.little3snake.subscriptiontracker.entity.Subscription;
import com.little3snake.subscriptiontracker.enums.BillingPeriod;
import com.little3snake.subscriptiontracker.enums.SubscriptionStatus;
import com.little3snake.subscriptiontracker.repository.SubscriptionRepository;
import com.little3snake.subscriptiontracker.dto.SubscriptionRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse create(SubscriptionRequest request) {
        Subscription subscription = Subscription.builder()
                .name(request.getName())
                .price(request.getPrice())
                .currency(request.getCurrency())
                .billingPeriod(request.getBillingPeriod())
                .category(request.getCategory())
                .startDate(request.getStartDate())
                .status(request.getStatus() != null ? request.getStatus() : SubscriptionStatus.ACTIVE)
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        Subscription saved = subscriptionRepository.save(subscription);
        return toResponse(saved);
    }

    public List<SubscriptionResponse> getAll() {
        return subscriptionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public SubscriptionResponse getById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + id));

        return toResponse(subscription);
    }

    public List<SubscriptionResponse> getActive() {
        return subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SubscriptionResponse> getUpcoming(int days) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(days);

        return subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE).stream()
                .map(subscription -> {
                    LocalDate nextPaymentDate = calculateNextPaymentDate(subscription, today);
                    return new SubscriptionWithNextDate(subscription, nextPaymentDate);
                })
                .filter(item -> !item.nextPaymentDate().isBefore(today) && !item.nextPaymentDate().isAfter(endDate))
                .map(item -> toResponse(item.subscription(), item.nextPaymentDate()))
                .toList();
    }

    public SubscriptionResponse updateStatus(Long id, SubscriptionStatus status) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found with id: " + id));

        subscription.setStatus(status);

        Subscription updated = subscriptionRepository.save(subscription);

        return toResponse(updated);
    }

    public BigDecimal getMonthlyCost() {
        return subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE).stream()
                .map(subscription -> {
                    if (subscription.getBillingPeriod() == BillingPeriod.YEARLY) {
                        return subscription.getPrice()
                                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);
                    }
                    return subscription.getPrice();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void delete(Long id) {
        subscriptionRepository.deleteById(id);
    }

    private SubscriptionResponse toResponse(Subscription subscription) {
        LocalDate nextPaymentDate = calculateNextPaymentDate(subscription, LocalDate.now());
        return toResponse(subscription, nextPaymentDate);
    }

    private SubscriptionResponse toResponse(Subscription subscription, LocalDate nextPaymentDate) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .name(subscription.getName())
                .price(subscription.getPrice())
                .currency(subscription.getCurrency())
                .billingPeriod(subscription.getBillingPeriod())
                .startDate(subscription.getStartDate())
                .nextPaymentDate(nextPaymentDate)
                .category(subscription.getCategory())
                .status(subscription.getStatus())
                .description(subscription.getDescription())
                .createdAt(subscription.getCreatedAt())
                .build();
    }

    private LocalDate calculateNextPaymentDate(Subscription subscription, LocalDate today) {
        LocalDate startDate = subscription.getStartDate();

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            return null;
        }

        if (subscription.getBillingPeriod() == BillingPeriod.MONTHLY) {
            LocalDate nextDate = startDate;
            while (nextDate.isBefore(today)) {
                nextDate = nextDate.plusMonths(1);
            }
            return nextDate;
        }

        if (subscription.getBillingPeriod() == BillingPeriod.YEARLY) {
            LocalDate nextDate = startDate;
            while (nextDate.isBefore(today)) {
                nextDate = nextDate.plusYears(1);
            }
            return nextDate;
        }

        throw new IllegalStateException("Unsupported billing period: " + subscription.getBillingPeriod());
    }

    private record SubscriptionWithNextDate(Subscription subscription, LocalDate nextPaymentDate) {
    }
}