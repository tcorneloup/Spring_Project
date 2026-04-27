package project.spring.web.dto;

import project.spring.domain.model.SubscriptionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record SubscriptionResponse(
        @NotNull Long id,
        @NotNull Long clientId,
        @NotNull LocalDateTime subscriptionDateStart,
        @NotNull SubscriptionType type,
        List<OptionResponse> options){
}
