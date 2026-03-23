package recruitment.itsf.web.dto;

import recruitment.itsf.domain.model.SubscriptionType;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SubscriptionRequest(
        @NotNull Long clientId,
        @NotNull SubscriptionType subscriptionType,
        List<OptionRequest> options) {
}
