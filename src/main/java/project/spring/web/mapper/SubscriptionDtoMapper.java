package project.spring.web.mapper;
import project.spring.domain.model.Option;
import project.spring.domain.model.Subscription;
import project.spring.web.dto.OptionRequest;
import project.spring.web.dto.OptionResponse;
import project.spring.web.dto.SubscriptionRequest;
import project.spring.web.dto.SubscriptionResponse;
import java.util.Collections;
import java.util.List;
public final class SubscriptionDtoMapper {

    private SubscriptionDtoMapper() {
    }

    public static Subscription subscriptionRequestToDomain(SubscriptionRequest subscriptionRequest) {
        if (subscriptionRequest == null) {
            return null;
        }
        Subscription subscription = new Subscription();
        subscription.setClientId(subscriptionRequest.clientId());
        subscription.setType(subscriptionRequest.subscriptionType());

        List<Option> options = subscriptionRequest.options() != null
                ? subscriptionRequest.options().stream().map(SubscriptionDtoMapper::optionRequestToDomain).toList()
                : Collections.emptyList();

        subscription.setOptionList(options);
        return subscription;
    }

    public static Option optionRequestToDomain(OptionRequest optionRequest) {
        if (optionRequest == null) {
            return null;
        }
        Option option = new Option();
        option.setOptionType(optionRequest.name());
        option.setOptionSubDateStart(optionRequest.optionSubDateStart());
        return option;
    }

    public static SubscriptionResponse subscriptionDomainToResponse(Subscription subscriptionDomain) {

        if (subscriptionDomain == null) {
            return null;
        }

        List<OptionResponse> optionResponses = subscriptionDomain.getOptionsList() != null
                ? subscriptionDomain.getOptionsList().stream().map(SubscriptionDtoMapper::optionDomainToResponse).toList()
                : Collections.emptyList();

        return new SubscriptionResponse(
                subscriptionDomain.getId(),
                subscriptionDomain.getClientId(),
                subscriptionDomain.getSubscriptionDateStart(),
                subscriptionDomain.getType(),
                optionResponses
        );
    }

    public static OptionResponse optionDomainToResponse(Option optionDomain) {

        if (optionDomain == null) {
            return null;
        }

        return new OptionResponse(
                optionDomain.getId(),
                optionDomain.getOptionType(),
                optionDomain.getOptionSubDateStart()
        );
    }
}