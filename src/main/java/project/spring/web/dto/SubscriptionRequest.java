package project.spring.web.dto;

import project.spring.domain.model.SubscriptionType;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SubscriptionRequest(
        @NotNull Long clientId,
        @NotNull SubscriptionType subscriptionType,
        List<OptionRequest> options) {
}
