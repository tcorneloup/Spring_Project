package recruitment.itsf.infra.adapter;

import recruitment.itsf.domain.repository.SubscriptionRepositoryDomain;
import recruitment.itsf.domain.model.Subscription;
import recruitment.itsf.infra.entity.SubscriptionEntity;
import recruitment.itsf.infra.mapper.SubscriptionEntityMapper;
import org.springframework.stereotype.Component;
import recruitment.itsf.infra.repository.SubscriptionRepositoryInfra;

import java.util.List;
import java.util.Optional;

@Component
public class SubscriptionRepositoryDomainAdapter implements SubscriptionRepositoryDomain {

    private final SubscriptionRepositoryInfra subscriptionRepositoryInfra;

    public SubscriptionRepositoryDomainAdapter(SubscriptionRepositoryInfra subscriptionRepositoryInfra) {
        this.subscriptionRepositoryInfra = subscriptionRepositoryInfra;
    }

    @Override
    public Subscription save(Subscription subscriptionDomainToSave) {
        SubscriptionEntity subscriptionEntityToSave = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomainToSave);
        SubscriptionEntity savedSubscriptionEntity = subscriptionRepositoryInfra.save(subscriptionEntityToSave);
        return SubscriptionEntityMapper.subscriptionEntityToDomain(savedSubscriptionEntity);
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionRepositoryInfra.findById(id)
                .map(SubscriptionEntityMapper::subscriptionEntityToDomain);
    }

    @Override
    public List<Subscription> findAllSubscriptionsWithOptions() {
        return subscriptionRepositoryInfra.findAllSubscriptions().stream()
                .map(SubscriptionEntityMapper::subscriptionEntityToDomain)
                .toList();
    }
}

