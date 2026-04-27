package project.spring.domain.model;

import project.spring.infra.entity.OptionEntity;
import project.spring.infra.entity.SubscriptionEntity;
import project.spring.infra.mapper.SubscriptionEntityMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionTest {

    @Test
    void subscriptionEntityToDomain_ShouldReturnNullIfSubscriptionEntityIsNull() {
        assertNull(SubscriptionEntityMapper.subscriptionEntityToDomain(null));
    }

    @Test
    void subscriptionEntityToDomain_ShouldReturnSubscriptionDomainWhenSubscriptionEntityAndOptionEntityAreNotNull() {
        LocalDateTime newDate = LocalDateTime.of(2026, 3, 10, 12, 30);

        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setOptionType(OptionType.NETFLIX);
        optionEntity.setOptionSubDateStart(newDate);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setId(1L);
        subscriptionEntity.setClientId(2L);
        subscriptionEntity.setType(SubscriptionType.FIBER);
        subscriptionEntity.setSubscriptionDateStart(newDate);
        subscriptionEntity.setOptionsList(List.of(optionEntity));

        Subscription subscriptionDomain = SubscriptionEntityMapper.subscriptionEntityToDomain(subscriptionEntity);

        assertNotNull(subscriptionDomain);
        assertEquals(1L, subscriptionDomain.getId());
        assertEquals(2L, subscriptionDomain.getClientId());
        assertEquals(SubscriptionType.FIBER, subscriptionDomain.getType());
        assertEquals(newDate, subscriptionDomain.getSubscriptionDateStart());
        assertEquals(1, subscriptionDomain.getOptionsList().size());
        assertEquals(OptionType.NETFLIX, subscriptionDomain.getOptionsList().getFirst().getOptionType());
    }

    @Test
    void subscriptionDomainToEntity_ShouldReturnNullIfSubscriptionDomainIsNull() {
        assertNull(SubscriptionEntityMapper.subscriptionDomainToEntity(null));
    }

    @Test
    void subscriptionDomainToEntity_ShouldReturnSubscriptionEntityWhenSubscriptionDomainIsNotNull() {
        LocalDateTime newDate = LocalDateTime.of(2026, 3, 10, 12, 30);

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(2L);
        subscriptionDomain.setType(SubscriptionType.FIBER);
        subscriptionDomain.setSubscriptionDateStart(newDate);

        SubscriptionEntity subscriptionEntity = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomain);

        assertNotNull(subscriptionEntity);
        assertEquals(1L, subscriptionEntity.getId());
        assertEquals(2L, subscriptionEntity.getClientId());
        assertEquals(SubscriptionType.FIBER, subscriptionEntity.getType());
        assertEquals(newDate, subscriptionEntity.getSubscriptionDateStart());
    }

    @Test
    void subscriptionDomainToEntity_ShouldReturnEntityWithOptionsWhenSubscriptionDomainAndOptionDomainAreNotNull() {
        LocalDateTime newDate = LocalDateTime.of(2026, 3, 10, 12, 30);

        Option optionDomain = new Option();
        optionDomain.setId(1L);
        optionDomain.setOptionType(OptionType.NETFLIX);
        optionDomain.setOptionSubDateStart(newDate);

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setType(SubscriptionType.FIBER);
        subscriptionDomain.setSubscriptionDateStart(newDate);
        subscriptionDomain.setOptionList(List.of(optionDomain));

        SubscriptionEntity subscriptionEntity = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomain);

        assertNotNull(subscriptionEntity);
        assertEquals(1, subscriptionEntity.getOptionsList().size());
        assertEquals(OptionType.NETFLIX, subscriptionEntity.getOptionsList().getFirst().getOptionType());
    }

    @Test
    void subscriptionDomainToEntity_ShouldNotSetOptionsWhenOptionsListIsNull() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);
        subscriptionDomain.setOptionList(null);

        SubscriptionEntity subscriptionEntity = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomain);

        assertNotNull(subscriptionEntity);
        assertTrue(subscriptionEntity.getOptionsList().isEmpty());
    }
}
