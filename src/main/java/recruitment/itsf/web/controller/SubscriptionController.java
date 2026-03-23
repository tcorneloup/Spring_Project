package recruitment.itsf.web.controller;

import recruitment.itsf.domain.model.OptionType;
import recruitment.itsf.domain.model.Subscription;
import recruitment.itsf.domain.service.SubscriptionService;
import recruitment.itsf.web.dto.SubscriptionRequest;
import recruitment.itsf.web.dto.SubscriptionResponse;
import recruitment.itsf.web.mapper.SubscriptionDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/itsf/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> findAllSubscriptions() {
        List<Subscription> subscriptionsListDomain = subscriptionService.findAllSubscriptionsWithOptions();

        List<SubscriptionResponse> subscriptionsListResponse = subscriptionsListDomain.stream()
                .map(SubscriptionDtoMapper::subscriptionDomainToResponse)
                .toList();

        return ResponseEntity.ok(subscriptionsListResponse);
    }

    @PostMapping()
    public ResponseEntity<SubscriptionResponse> newSubscription(@Valid @RequestBody SubscriptionRequest request) {
        Subscription subscriptionDomainToSave = SubscriptionDtoMapper.subscriptionRequestToDomain(request);

        Subscription savedSubscriptionDomain = subscriptionService.addNewSubscription(subscriptionDomainToSave);

        SubscriptionResponse SubscriptionResponse = SubscriptionDtoMapper.subscriptionDomainToResponse(savedSubscriptionDomain);
        return ResponseEntity.ok(SubscriptionResponse);
    }

    @PostMapping("/{id}/options/{option}")
    public ResponseEntity<SubscriptionResponse> addOptionToExistingSubscription(
            @PathVariable("id") Long id,
            @PathVariable("option") OptionType optionType) {

        Subscription subscriptionDomainToUpdate = subscriptionService.getSubscriptionById(id);

        Subscription updatedSubscriptionDomain = subscriptionService.addOptionsToExistingSubscription(subscriptionDomainToUpdate, optionType);

        SubscriptionResponse subscriptionResponse = SubscriptionDtoMapper.subscriptionDomainToResponse(updatedSubscriptionDomain);
        return ResponseEntity.ok(subscriptionResponse);
    }
}
