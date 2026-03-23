package recruitment.itsf.infra.mapper;

import recruitment.itsf.domain.model.Option;
import recruitment.itsf.domain.model.Subscription;
import recruitment.itsf.infra.entity.OptionEntity;
import recruitment.itsf.infra.entity.SubscriptionEntity;

import java.util.ArrayList;

public final class SubscriptionEntityMapper {

    private SubscriptionEntityMapper() {
    }

    public static Subscription subscriptionEntityToDomain(SubscriptionEntity subscriptionEntity) {

        if (subscriptionEntity == null) {
            return null;
        }

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(subscriptionEntity.getId());
        subscriptionDomain.setClientId(subscriptionEntity.getClientId());
        subscriptionDomain.setSubscriptionDateStart(subscriptionEntity.getSubscriptionDateStart());
        subscriptionDomain.setType(subscriptionEntity.getType());
        subscriptionDomain.setOptionList(
                subscriptionEntity.getOptionsList().stream()
                        .map(SubscriptionEntityMapper::optionEntityToDomain)
                        .toList()
        );

        return subscriptionDomain;
    }

    public static SubscriptionEntity subscriptionDomainToEntity(Subscription subscriptionDomain) {

        if (subscriptionDomain == null) {
            return null;
        }

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setId(subscriptionDomain.getId());
        subscriptionEntity.setClientId(subscriptionDomain.getClientId());
        subscriptionEntity.setSubscriptionDateStart(subscriptionDomain.getSubscriptionDateStart());
        subscriptionEntity.setType(subscriptionDomain.getType());

        if (subscriptionDomain.getOptionsList() != null && !subscriptionDomain.getOptionsList().isEmpty()) {
            subscriptionEntity.setOptionsList(new ArrayList<>(
                    subscriptionDomain.getOptionsList().stream()
                            .map(SubscriptionEntityMapper::optionDomainToEntity)
                            .toList()
            ));
        }

        return subscriptionEntity;
    }

    public static Option optionEntityToDomain(OptionEntity optionEntity) {
        
        if (optionEntity == null) {
            return null;
        }

        Option optionDomain = new Option();
        optionDomain.setId(optionEntity.getId());
        optionDomain.setOptionType(optionEntity.getOptionType());
        optionDomain.setOptionSubDateStart(optionEntity.getOptionSubDateStart());

        return optionDomain;
    }

    public static OptionEntity optionDomainToEntity(Option optionDomain) {

        if (optionDomain == null) {
            return null;
        }

        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setId(optionDomain.getId());
        optionEntity.setOptionType(optionDomain.getOptionType());
        optionEntity.setOptionSubDateStart(optionDomain.getOptionSubDateStart());

        return optionEntity;
    }
}
