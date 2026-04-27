package project.spring.infra;

import project.spring.domain.model.Option;
import project.spring.domain.model.OptionType;
import project.spring.domain.model.Subscription;
import project.spring.domain.model.SubscriptionType;
import project.spring.infra.entity.OptionEntity;
import project.spring.infra.entity.SubscriptionEntity;
import project.spring.infra.mapper.SubscriptionEntityMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionEntityMapperTest {

    private static final LocalDateTime DATE = LocalDateTime.of(2026, 3, 10, 12, 30);

    @Test
    void subscriptionEntityToDomain_shouldReturnNullWhenSubscriptionEntityIsNull() {
        assertNull(SubscriptionEntityMapper.subscriptionEntityToDomain(null));
    }

    @Test
    void subscriptionEntityToDomain_shouldMapAllFieldsCorrectly() {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setId(1L);
        subscriptionEntity.setClientId(2L);
        subscriptionEntity.setType(SubscriptionType.FIBER);
        subscriptionEntity.setSubscriptionDateStart(DATE);

        Subscription subscriptionDomain = SubscriptionEntityMapper.subscriptionEntityToDomain(subscriptionEntity);

        assertNotNull(subscriptionDomain);
        assertEquals(1L, subscriptionDomain.getId());
        assertEquals(2L, subscriptionDomain.getClientId());
        assertEquals(SubscriptionType.FIBER, subscriptionDomain.getType());
        assertEquals(DATE, subscriptionDomain.getSubscriptionDateStart());
        assertTrue(subscriptionDomain.getOptionsList().isEmpty());
    }

    @Test
    void subscriptionEntityToDomain_shouldMapOptionsEntityCorrectly() {
        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setId(10L);
        optionEntity.setOptionType(OptionType.NETFLIX);
        optionEntity.setOptionSubDateStart(DATE);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setId(1L);
        subscriptionEntity.setClientId(2L);
        subscriptionEntity.setType(SubscriptionType.FIBER);
        subscriptionEntity.setSubscriptionDateStart(DATE);
        subscriptionEntity.setOptionsList(List.of(optionEntity));

        Subscription subscriptionDomain = SubscriptionEntityMapper.subscriptionEntityToDomain(subscriptionEntity);

        assertEquals(1, subscriptionDomain.getOptionsList().size());
        Option optionDomain = subscriptionDomain.getOptionsList().getFirst();
        assertEquals(10L, optionDomain.getId());
        assertEquals(OptionType.NETFLIX, optionDomain.getOptionType());
        assertEquals(DATE, optionDomain.getOptionSubDateStart());
    }

    @Test
    void subscriptionDomainToEntity_shouldReturnNullWhenSubscriptionDomainIsNull() {
        assertNull(SubscriptionEntityMapper.subscriptionDomainToEntity(null));
    }

    @Test
    void subscriptionDomainToEntity_shouldMapAllFieldsCorrectly() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(2L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);
        subscriptionDomain.setSubscriptionDateStart(DATE);

        SubscriptionEntity subscriptionEntity = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomain);

        assertNotNull(subscriptionEntity);
        assertEquals(1L, subscriptionEntity.getId());
        assertEquals(2L, subscriptionEntity.getClientId());
        assertEquals(SubscriptionType.MOBILE, subscriptionEntity.getType());
        assertEquals(DATE, subscriptionEntity.getSubscriptionDateStart());
        assertTrue(subscriptionEntity.getOptionsList().isEmpty());
    }

    @Test
    void subscriptionDomainToEntity_shouldMapOptionsDomainCorrectly() {
        Option optionDomain = new Option();
        optionDomain.setId(10L);
        optionDomain.setOptionType(OptionType.ROAMING);
        optionDomain.setOptionSubDateStart(DATE);

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(2L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);
        subscriptionDomain.setSubscriptionDateStart(DATE);
        subscriptionDomain.setOptionList(List.of(optionDomain));

        SubscriptionEntity subscriptionEntity = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomain);

        assertEquals(1, subscriptionEntity.getOptionsList().size());
        OptionEntity optionEntity = subscriptionEntity.getOptionsList().getFirst();
        assertEquals(10L, optionEntity.getId());
        assertEquals(OptionType.ROAMING, optionEntity.getOptionType());
        assertEquals(DATE, optionEntity.getOptionSubDateStart());
    }

    @Test
    void subscriptionDomainToEntity_shouldHandleNullOptionsDomainList() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.FIX);
        subscriptionDomain.setOptionList(null);

        SubscriptionEntity subscriptionEntity = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomain);

        assertNotNull(subscriptionEntity);
        assertTrue(subscriptionEntity.getOptionsList().isEmpty());
    }

    @Test
    void subscriptionDomainToEntity_shouldHandleEmptyOptionsDomainList() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.FIX);
        subscriptionDomain.setOptionList(new ArrayList<>());

        SubscriptionEntity subscriptionEntity = SubscriptionEntityMapper.subscriptionDomainToEntity(subscriptionDomain);

        assertNotNull(subscriptionEntity);
        assertTrue(subscriptionEntity.getOptionsList().isEmpty());
    }

    @Test
    void optionEntityToDomain_shouldReturnNullWhenOptionEntityIsNull() {
        assertNull(SubscriptionEntityMapper.optionEntityToDomain(null));
    }

    @Test
    void optionEntityToDomain_shouldMapAllFieldsCorrectly() {
        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setId(5L);
        optionEntity.setOptionType(OptionType.MUSIC);
        optionEntity.setOptionSubDateStart(DATE);

        Option optionDomain = SubscriptionEntityMapper.optionEntityToDomain(optionEntity);

        assertNotNull(optionDomain);
        assertEquals(5L, optionDomain.getId());
        assertEquals(OptionType.MUSIC, optionDomain.getOptionType());
        assertEquals(DATE, optionDomain.getOptionSubDateStart());
    }

    @Test
    void optionDomainToEntity_shouldReturnNullWhenOptionDomainIsNull() {
        assertNull(SubscriptionEntityMapper.optionDomainToEntity(null));
    }

    @Test
    void optionDomainToEntity_shouldMapAllFieldsCorrectly() {
        Option optionDomain = new Option();
        optionDomain.setId(5L);
        optionDomain.setOptionType(OptionType.HD);
        optionDomain.setOptionSubDateStart(DATE);

        OptionEntity optionEntity = SubscriptionEntityMapper.optionDomainToEntity(optionDomain);

        assertNotNull(optionEntity);
        assertEquals(5L, optionEntity.getId());
        assertEquals(OptionType.HD, optionEntity.getOptionType());
        assertEquals(DATE, optionEntity.getOptionSubDateStart());
    }
}

