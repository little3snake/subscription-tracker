package com.little3snake.subscriptiontracker.repository;

import com.little3snake.subscriptiontracker.entity.Subscription;
import com.little3snake.subscriptiontracker.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByStatus(SubscriptionStatus status);
}
