package recruitment.itsf.domain.service;

import recruitment.itsf.domain.exception.DomainException;
import recruitment.itsf.domain.repository.SubscriptionRepositoryDomain;
import recruitment.itsf.domain.model.Option;
import recruitment.itsf.domain.model.OptionType;
import recruitment.itsf.domain.model.Subscription;
import recruitment.itsf.domain.model.SubscriptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SubscriptionService {
    
    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

    private final SubscriptionRepositoryDomain subscriptionRepositoryDomain;

    public SubscriptionService(SubscriptionRepositoryDomain subscriptionRepositoryDomain) {
        this.subscriptionRepositoryDomain = subscriptionRepositoryDomain;
    }

    @Transactional
    public Subscription addNewSubscription(Subscription newSubscriptionDomain) {

        if (newSubscriptionDomain.getClientId() == null) {
            log.warn(ErrorCodes.SUBSCRIPTION_CLIENT_ID_REQUIRED);
            throw new DomainException(ErrorCodes.SUBSCRIPTION_CLIENT_ID_REQUIRED);
        }

        if (newSubscriptionDomain.getType() == null) {
            log.warn(ErrorCodes.SUBSCRIPTION_TYPE_REQUIRED);
            throw new DomainException(ErrorCodes.SUBSCRIPTION_TYPE_REQUIRED);
        }
        newSubscriptionDomain.setSubscriptionDateStart(LocalDateTime.now());
        
        List<Option> optionsDomainListToAdd = newSubscriptionDomain.getOptionsList();
        for (Option optionsDomain : optionsDomainListToAdd) {
            manageOptions(newSubscriptionDomain, optionsDomain);
        }

        return subscriptionRepositoryDomain.save(newSubscriptionDomain);
    }

    @Transactional
    public Subscription addOptionsToExistingSubscription(Subscription subscriptionDomainToAddNewOption, OptionType optionTypeToAdd) {

        Option newOptionDomain = new Option();
        newOptionDomain.setOptionType(optionTypeToAdd);
        newOptionDomain.setOptionSubDateStart(LocalDateTime.now());
        
        manageOptions(subscriptionDomainToAddNewOption, newOptionDomain);

        subscriptionDomainToAddNewOption.getOptionsList().add(newOptionDomain);
        
        return subscriptionRepositoryDomain.save(subscriptionDomainToAddNewOption);
    }
    
    @Transactional(readOnly = true)
    public Subscription getSubscriptionById(Long pId) {
        return subscriptionRepositoryDomain.findById(pId)
                .orElseThrow(() -> new DomainException(ErrorCodes.SUBSCRIPTION_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<Subscription> findAllSubscriptionsWithOptions() {
        return subscriptionRepositoryDomain.findAllSubscriptionsWithOptions();
    }
    
    private void manageOptions(Subscription subscriptionDomainToManage, Option optionDomainToManage) {
        OptionType getOptionType = optionDomainToManage.getOptionType();

        if (optionDomainToManage.getOptionSubDateStart() == null) {
            optionDomainToManage.setOptionSubDateStart(LocalDateTime.now());
        }

        if (getOptionType == null) {
            log.warn(ErrorCodes.OPTION_TYPE_NULL);
            throw new DomainException(ErrorCodes.OPTION_TYPE_NULL);
        }

        boolean optionAlreadyExistsInThisSubscription = subscriptionDomainToManage.getOptionsList().stream()
                .filter(option -> option != optionDomainToManage)
                .anyMatch(option -> option.getOptionType() == getOptionType);

        if (optionAlreadyExistsInThisSubscription) {
            log.warn("Option {} is already added for this subscription {}", getOptionType, subscriptionDomainToManage.getId());
            throw new DomainException(ErrorCodes.OPTION_ALREADY_EXISTS);
        }

        checkOptionsToAddInTheSubscription(subscriptionDomainToManage, getOptionType);
    }

    private void checkOptionsToAddInTheSubscription(Subscription subscriptionDomainToManage, OptionType optionTypeToAdd) {
        switch (optionTypeToAdd) {
            case ROAMING -> {
                if (subscriptionDomainToManage.getType() != (SubscriptionType.MOBILE)) {
                    log.warn(ErrorCodes.OPTION_ROAMING_NOT_ALLOWED);
                    throw new DomainException(ErrorCodes.OPTION_ROAMING_NOT_ALLOWED);
                }
            }
            case NETFLIX -> {
                if (subscriptionDomainToManage.getType() != (SubscriptionType.FIBER)) {
                    log.warn(ErrorCodes.OPTION_NETFLIX_NOT_ALLOWED);
                    throw new DomainException(ErrorCodes.OPTION_NETFLIX_NOT_ALLOWED);
                }
            }
            case HD -> {
                boolean isNetflixAdded = subscriptionDomainToManage.getOptionsList().stream()
                        .anyMatch(option -> option.getOptionType() == OptionType.NETFLIX);

                if (!isNetflixAdded) {
                    log.warn(ErrorCodes.OPTION_HD_REQUIRES_NETFLIX);
                    throw new DomainException(ErrorCodes.OPTION_HD_REQUIRES_NETFLIX);
                }
            }
            case MUSIC -> {
                if (subscriptionDomainToManage.getType() == SubscriptionType.FIX) {
                    log.warn(ErrorCodes.OPTION_MUSIC_NOT_ALLOWED);
                    throw new DomainException(ErrorCodes.OPTION_MUSIC_NOT_ALLOWED);
                }
            }
        }
    }

}