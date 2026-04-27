package project.spring.domain.repository;

import project.spring.domain.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepositoryDomain {

    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    List<Subscription> findAllSubscriptionsWithOptions();
}
