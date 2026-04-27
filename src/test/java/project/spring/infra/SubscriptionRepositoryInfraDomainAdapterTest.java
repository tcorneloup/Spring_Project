package project.spring.infra;

import project.spring.domain.model.Option;
import project.spring.domain.model.OptionType;
import project.spring.domain.model.Subscription;
import project.spring.domain.model.SubscriptionType;
import project.spring.infra.adapter.SubscriptionRepositoryDomainAdapter;
import project.spring.infra.entity.OptionEntity;
import project.spring.infra.entity.SubscriptionEntity;
import project.spring.infra.repository.SubscriptionRepositoryInfra;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionRepositoryInfraDomainAdapterTest {

    @Mock
    private SubscriptionRepositoryInfra subscriptionRepositoryInfra;

    @InjectMocks
    private SubscriptionRepositoryDomainAdapter adapter;

    private static final LocalDateTime DATE = LocalDateTime.of(2026, 3, 10, 12, 30);

    @Test
    void save_shouldConvertDomainToEntitySaveAndConvertBack() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);
        subscriptionDomain.setSubscriptionDateStart(DATE);

        SubscriptionEntity savedSubscriptionEntity = new SubscriptionEntity();
        savedSubscriptionEntity.setId(1L);
        savedSubscriptionEntity.setClientId(42L);
        savedSubscriptionEntity.setType(SubscriptionType.MOBILE);
        savedSubscriptionEntity.setSubscriptionDateStart(DATE);

        when(subscriptionRepositoryInfra.save(any(SubscriptionEntity.class))).thenReturn(savedSubscriptionEntity);

        Subscription subscriptionDomain1 = adapter.save(subscriptionDomain);

        assertNotNull(subscriptionDomain1);
        assertEquals(1L, subscriptionDomain1.getId());
        assertEquals(42L, subscriptionDomain1.getClientId());
        assertEquals(SubscriptionType.MOBILE, subscriptionDomain1.getType());
        verify(subscriptionRepositoryInfra).save(any(SubscriptionEntity.class));
    }

    @Test
    void save_shouldConvertOptionsCorrectly() {
        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.NETFLIX);
        optionDomain.setOptionSubDateStart(DATE);

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.FIBER);
        subscriptionDomain.setSubscriptionDateStart(DATE);
        subscriptionDomain.setOptionList(new ArrayList<>(List.of(optionDomain)));

        OptionEntity savedOptionEntity = new OptionEntity();
        savedOptionEntity.setId(10L);
        savedOptionEntity.setOptionType(OptionType.NETFLIX);
        savedOptionEntity.setOptionSubDateStart(DATE);

        SubscriptionEntity savedSubscriptionEntity = new SubscriptionEntity();
        savedSubscriptionEntity.setId(1L);
        savedSubscriptionEntity.setClientId(42L);
        savedSubscriptionEntity.setType(SubscriptionType.FIBER);
        savedSubscriptionEntity.setSubscriptionDateStart(DATE);
        savedSubscriptionEntity.setOptionsList(List.of(savedOptionEntity));

        when(subscriptionRepositoryInfra.save(any(SubscriptionEntity.class))).thenReturn(savedSubscriptionEntity);

        Subscription subscriptionDomain1 = adapter.save(subscriptionDomain);

        assertEquals(1, subscriptionDomain1.getOptionsList().size());
        assertEquals(OptionType.NETFLIX, subscriptionDomain1.getOptionsList().getFirst().getOptionType());
        assertEquals(10L, subscriptionDomain1.getOptionsList().getFirst().getId());
    }

    @Test
    void findById_shouldReturnDomainSubscriptionWhenFound() {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setId(1L);
        subscriptionEntity.setClientId(10L);
        subscriptionEntity.setType(SubscriptionType.FIX);
        subscriptionEntity.setSubscriptionDateStart(DATE);

        when(subscriptionRepositoryInfra.findById(1L)).thenReturn(Optional.of(subscriptionEntity));

        Optional<Subscription> subscriptionDomain = adapter.findById(1L);

        assertTrue(subscriptionDomain.isPresent());
        assertEquals(1L, subscriptionDomain.get().getId());
        assertEquals(10L, subscriptionDomain.get().getClientId());
        assertEquals(SubscriptionType.FIX, subscriptionDomain.get().getType());
        verify(subscriptionRepositoryInfra).findById(1L);
    }

    @Test
    void findById_shouldReturnEmptyWhenNotFound() {
        when(subscriptionRepositoryInfra.findById(999L)).thenReturn(Optional.empty());

        Optional<Subscription> subscriptionDomain = adapter.findById(999L);

        assertTrue(subscriptionDomain.isEmpty());
        verify(subscriptionRepositoryInfra).findById(999L);
    }

    @Test
    void findAllSubscriptionsWithOptions_shouldReturnDomainList() {
        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setId(10L);
        optionEntity.setOptionType(OptionType.MUSIC);
        optionEntity.setOptionSubDateStart(DATE);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setId(1L);
        subscriptionEntity.setClientId(5L);
        subscriptionEntity.setType(SubscriptionType.FIBER);
        subscriptionEntity.setSubscriptionDateStart(DATE);
        subscriptionEntity.setOptionsList(List.of(optionEntity));

        when(subscriptionRepositoryInfra.findAllSubscriptions()).thenReturn(List.of(subscriptionEntity));

        List<Subscription> subscriptionsDomainList = adapter.findAllSubscriptionsWithOptions();

        assertEquals(1, subscriptionsDomainList.size());
        Subscription subscriptionDomain = subscriptionsDomainList.getFirst();
        assertEquals(1L, subscriptionDomain.getId());
        assertEquals(5L, subscriptionDomain.getClientId());
        assertEquals(1, subscriptionDomain.getOptionsList().size());
        assertEquals(OptionType.MUSIC, subscriptionDomain.getOptionsList().getFirst().getOptionType());
        verify(subscriptionRepositoryInfra).findAllSubscriptions();
    }

    @Test
    void findAllSubscriptionsWithOptions_shouldReturnEmptyListWhenNoData() {
        when(subscriptionRepositoryInfra.findAllSubscriptions()).thenReturn(List.of());

        List<Subscription> subscriptionsDomainList = adapter.findAllSubscriptionsWithOptions();

        assertNotNull(subscriptionsDomainList);
        assertTrue(subscriptionsDomainList.isEmpty());
        verify(subscriptionRepositoryInfra).findAllSubscriptions();
    }
}

