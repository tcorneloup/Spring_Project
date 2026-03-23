package recruitment.itsf.domain.repository;

import recruitment.itsf.domain.model.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepositoryDomain {

    Subscription save(Subscription subscription);

    Optional<Subscription> findById(Long id);

    List<Subscription> findAllSubscriptionsWithOptions();
}
