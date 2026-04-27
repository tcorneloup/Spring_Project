package project.spring.web;

import project.spring.domain.model.Option;
import project.spring.domain.model.OptionType;
import project.spring.domain.model.Subscription;
import project.spring.domain.model.SubscriptionType;
import project.spring.web.dto.OptionRequest;
import project.spring.web.dto.OptionResponse;
import project.spring.web.dto.SubscriptionRequest;
import project.spring.web.dto.SubscriptionResponse;
import project.spring.web.mapper.SubscriptionDtoMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDtoMapperTest {

    private static final LocalDateTime DATE = LocalDateTime.of(2026, 3, 10, 12, 30);

    @Test
    void subscriptionRequestToDomain_shouldReturnNullWhenSubscriptionRequestIsNull() {
        assertNull(SubscriptionDtoMapper.subscriptionRequestToDomain(null));
    }

    @Test
    void subscriptionRequestToDomain_shouldMapFieldsCorrectly() {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(42L, SubscriptionType.MOBILE, null);

        Subscription subscriptionDomain = SubscriptionDtoMapper.subscriptionRequestToDomain(subscriptionRequest);

        assertNotNull(subscriptionDomain);
        assertEquals(42L, subscriptionDomain.getClientId());
        assertEquals(SubscriptionType.MOBILE, subscriptionDomain.getType());
        assertNull(subscriptionDomain.getId());
        assertNull(subscriptionDomain.getSubscriptionDateStart());
        assertTrue(subscriptionDomain.getOptionsList().isEmpty());
    }

    @Test
    void subscriptionRequestToDomain_shouldMapOptionsRequestCorrectly() {
        OptionRequest optionRequest = new OptionRequest(OptionType.NETFLIX, DATE);
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(42L, SubscriptionType.FIBER, List.of(optionRequest));

        Subscription subscriptionDomain = SubscriptionDtoMapper.subscriptionRequestToDomain(subscriptionRequest);

        assertEquals(1, subscriptionDomain.getOptionsList().size());
        Option optionDomain = subscriptionDomain.getOptionsList().getFirst();
        assertEquals(OptionType.NETFLIX, optionDomain.getOptionType());
        assertEquals(DATE, optionDomain.getOptionSubDateStart());
    }

    @Test
    void subscriptionRequestToDomain_shouldHandleEmptyOptionsList() {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(42L, SubscriptionType.FIX, Collections.emptyList());

        Subscription subscriptionDomain = SubscriptionDtoMapper.subscriptionRequestToDomain(subscriptionRequest);

        assertNotNull(subscriptionDomain);
        assertTrue(subscriptionDomain.getOptionsList().isEmpty());
    }

    @Test
    void optionRequestToDomain_shouldReturnNullWhenOptionRequestIsNull() {
        assertNull(SubscriptionDtoMapper.optionRequestToDomain(null));
    }

    @Test
    void optionRequestToDomain_shouldMapFieldsCorrectly() {
        OptionRequest optionRequest = new OptionRequest(OptionType.ROAMING, DATE);

        Option optionDomain = SubscriptionDtoMapper.optionRequestToDomain(optionRequest);

        assertNotNull(optionDomain);
        assertEquals(OptionType.ROAMING, optionDomain.getOptionType());
        assertEquals(DATE, optionDomain.getOptionSubDateStart());
        assertNull(optionDomain.getId());
    }

    @Test
    void subscriptionDomainToResponse_shouldReturnNullWhenSubscriptionDomainIsNull() {
        assertNull(SubscriptionDtoMapper.subscriptionDomainToResponse(null));
    }

    @Test
    void subscriptionDomainToResponse_shouldMapAllFieldsCorrectly() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);
        subscriptionDomain.setSubscriptionDateStart(DATE);

        SubscriptionResponse subscriptionResponse = SubscriptionDtoMapper.subscriptionDomainToResponse(subscriptionDomain);

        assertNotNull(subscriptionResponse);
        assertEquals(1L, subscriptionResponse.id());
        assertEquals(42L, subscriptionResponse.clientId());
        assertEquals(SubscriptionType.MOBILE, subscriptionResponse.type());
        assertEquals(DATE, subscriptionResponse.subscriptionDateStart());
        assertTrue(subscriptionResponse.options().isEmpty());
    }

    @Test
    void subscriptionDomainToResponse_shouldMapOptionsDomainCorrectly() {
        Option optionDomain = new Option();
        optionDomain.setId(10L);
        optionDomain.setOptionType(OptionType.HD);
        optionDomain.setOptionSubDateStart(DATE);

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.FIBER);
        subscriptionDomain.setSubscriptionDateStart(DATE);
        subscriptionDomain.setOptionList(List.of(optionDomain));

        SubscriptionResponse subscriptionResponse = SubscriptionDtoMapper.subscriptionDomainToResponse(subscriptionDomain);

        assertEquals(1, subscriptionResponse.options().size());
        OptionResponse optionResponse = subscriptionResponse.options().getFirst();
        assertEquals(10L, optionResponse.id());
        assertEquals(OptionType.HD, optionResponse.optionType());
        assertEquals(DATE, optionResponse.optionSubDateStart());
    }

    @Test
    void subscriptionDomainToResponse_shouldHandleNullOptionsList() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.FIX);
        subscriptionDomain.setOptionList(null);

        SubscriptionResponse subscriptionResponse = SubscriptionDtoMapper.subscriptionDomainToResponse(subscriptionDomain);

        assertNotNull(subscriptionResponse);
        assertTrue(subscriptionResponse.options().isEmpty());
    }

    @Test
    void optionDomainToResponse_shouldReturnNullWhenOptionIsNull() {
        assertNull(SubscriptionDtoMapper.optionDomainToResponse(null));
    }

    @Test
    void optionDomainToResponse_shouldMapAllFieldsCorrectly() {
        Option optionDomain = new Option();
        optionDomain.setId(5L);
        optionDomain.setOptionType(OptionType.MUSIC);
        optionDomain.setOptionSubDateStart(DATE);

        OptionResponse optionResponse = SubscriptionDtoMapper.optionDomainToResponse(optionDomain);

        assertNotNull(optionResponse);
        assertEquals(5L, optionResponse.id());
        assertEquals(OptionType.MUSIC, optionResponse.optionType());
        assertEquals(DATE, optionResponse.optionSubDateStart());
    }
}

