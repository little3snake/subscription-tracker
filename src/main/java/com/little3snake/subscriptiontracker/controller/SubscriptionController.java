package com.little3snake.subscriptiontracker.controller;

import com.little3snake.subscriptiontracker.dto.SubscriptionResponse;
import com.little3snake.subscriptiontracker.entity.Subscription;
import com.little3snake.subscriptiontracker.service.SubscriptionService;
import com.little3snake.subscriptiontracker.enums.SubscriptionStatus;
import com.little3snake.subscriptiontracker.dto.SubscriptionRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public SubscriptionResponse create(@Valid @RequestBody SubscriptionRequest request) {
        return subscriptionService.create(request);
    }

    @GetMapping
    public List<SubscriptionResponse> getAll() {
        return subscriptionService.getAll();
    }

    @GetMapping("/{id}")
    public SubscriptionResponse getById(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }

    @GetMapping("/active")
    public List<SubscriptionResponse> getActive() {
        return subscriptionService.getActive();
    }

    @GetMapping("/upcoming")
    public List<SubscriptionResponse> getUpcoming(@RequestParam(defaultValue = "7") int days) {
        return subscriptionService.getUpcoming(days);
    }

    @GetMapping("/summary/monthly-cost")
    public BigDecimal getMonthlyCost() {
        return subscriptionService.getMonthlyCost();
    }

    @PatchMapping("/{id}/status")
    public SubscriptionResponse updateStatus(
            @PathVariable Long id,
            @RequestParam SubscriptionStatus status
    ) {
        return subscriptionService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        subscriptionService.delete(id);
    }
}