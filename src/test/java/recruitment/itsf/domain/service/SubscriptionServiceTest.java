package recruitment.itsf.domain.service;

import recruitment.itsf.domain.exception.DomainException;
import recruitment.itsf.domain.repository.SubscriptionRepositoryDomain;
import recruitment.itsf.domain.model.Option;
import recruitment.itsf.domain.model.OptionType;
import recruitment.itsf.domain.model.Subscription;
import recruitment.itsf.domain.model.SubscriptionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepositoryDomain subscriptionRepositoryDomain;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @Test
    void getSubscriptionById_shouldThrowDomainExceptionWhenNotFound() {
        when(subscriptionRepositoryDomain.findById(any())).thenReturn(Optional.empty());

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.getSubscriptionById(999L));

        assertEquals("subscription.isNull", domainException.getMessage());
    }

    @Test
    void findAllSubscriptionsWithOptions_shouldReturnSubscriptionsDomainListIfThereAreSome() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.FIBER);
        subscriptionDomain.setSubscriptionDateStart(LocalDateTime.now());

        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.NETFLIX);
        subscriptionDomain.setOptionList(new java.util.ArrayList<>(List.of(optionDomain)));

        when(subscriptionRepositoryDomain.findAllSubscriptionsWithOptions())
                .thenReturn(List.of(subscriptionDomain));

        List<Subscription> subscriptionsDomainList = subscriptionService.findAllSubscriptionsWithOptions();

        assertEquals(1, subscriptionsDomainList.size());
        assertNotNull(subscriptionsDomainList.getFirst());
        verify(subscriptionRepositoryDomain).findAllSubscriptionsWithOptions();
    }

    @Test
    void findAllSubscriptionsWithOptions_shouldReturnEmptyListIfThereIsNoSubscriptionsDomain() {
        when(subscriptionRepositoryDomain.findAllSubscriptionsWithOptions())
                .thenReturn(Collections.emptyList());

        List<Subscription> subscriptionsDomainList = subscriptionService.findAllSubscriptionsWithOptions();

        assertNotNull(subscriptionsDomainList);
        assertTrue(subscriptionsDomainList.isEmpty());
        verify(subscriptionRepositoryDomain).findAllSubscriptionsWithOptions();
    }

    @Test
    void addNewSubscription_shouldThrowDomainExceptionWhenClientIdIsNull() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(null);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("subscription.clientId.required", domainException.getErrorCode());
    }

    @Test
    void addNewSubscription_shouldThrowDomainExceptionWhenSubTypeIsNull() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(null);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("subscription.type.required", domainException.getErrorCode());
    }

    @Test
    void addNewSubscription_shouldReturnSubscriptionDomainWhenValid() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        Subscription savedSubscriptionDomain = mock(Subscription.class);
        when(subscriptionRepositoryDomain.save(any(Subscription.class))).thenReturn(savedSubscriptionDomain);

        Subscription newSubscriptionDomain = subscriptionService.addNewSubscription(subscriptionDomain);

        assertSame(savedSubscriptionDomain, newSubscriptionDomain);
        verify(subscriptionRepositoryDomain).save(any(Subscription.class));
    }

    @Test
    void addOptionToExistingSubscription_shouldThrowDomainExceptionWhenOptionsTypeIsNull() {

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addOptionsToExistingSubscription(subscriptionDomain, null));

        assertEquals("option.isNull", domainException.getErrorCode());
    }

    @Test
    void addOptionToExistingSubscription_shouldReturnSubscriptionDomainWhenOptionsTypeIsValid() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.FIBER);

        Subscription savedSubscriptionDomain = mock(Subscription.class);
        when(subscriptionRepositoryDomain.save(any(Subscription.class))).thenReturn(savedSubscriptionDomain);

        Subscription newSubscriptionDomain = subscriptionService.addOptionsToExistingSubscription(subscriptionDomain, OptionType.NETFLIX);

        assertSame(savedSubscriptionDomain, newSubscriptionDomain);
        verify(subscriptionRepositoryDomain).save(any(Subscription.class));
    }

    @Test
    void manageOptions_shouldThrowDomainExceptionWhenOptionTypeIsNull() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        Option optionDomain = new Option();
        optionDomain.setOptionType(null);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("option.isNull", domainException.getErrorCode());
    }

    @Test
    void manageOptions_shouldThrowDomainExceptionWhenOptionAlreadyExists() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        Option optionDomain1 = new Option();
        optionDomain1.setOptionType(OptionType.ROAMING);

        Option optionDomain2 = new Option();
        optionDomain2.setOptionType(OptionType.ROAMING);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain1);
        subscriptionDomain.getOptionsList().add(optionDomain2);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("option.already.exists", domainException.getErrorCode());
    }

    @Test
    void manageOptions_shouldThrowDomainExceptionWhenRoamingWithoutMobile() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.FIX);

        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.ROAMING);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("option.roaming.not.allowed", domainException.getErrorCode());
    }

    @Test
    void manageOptions_shouldThrowDomainExceptionWhenNetflixWithoutFiber() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.NETFLIX);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("option.netflix.not.allowed", domainException.getErrorCode());
    }

    @Test
    void manageOptions_shouldThrowDomainExceptionWhenHDWithoutNetflix() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.ROAMING);

        Option optionDomain2 = new Option();
        optionDomain2.setOptionType(OptionType.HD);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain);
        subscriptionDomain.getOptionsList().add(optionDomain2);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("option.hd.requires.netflix", domainException.getErrorCode());
    }

    @Test
    void manageOptions_shouldThrowDomainExceptionWhenMusicWithFix() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.FIX);

        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.MUSIC);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain);

        DomainException domainException = assertThrows(DomainException.class,
                () -> subscriptionService.addNewSubscription(subscriptionDomain));

        assertEquals("option.music.not.allowed", domainException.getErrorCode());
    }

    @Test
    void manageOptions_shouldSaveWhenRoamingAndMusicWithMobile() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.MOBILE);

        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.MUSIC);

        Option optionDomain2 = new Option();
        optionDomain2.setOptionType(OptionType.ROAMING);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain);
        subscriptionDomain.getOptionsList().add(optionDomain2);

        Subscription savedSubcriptionDomain = mock(Subscription.class);
        when(subscriptionRepositoryDomain.save(any(Subscription.class))).thenReturn(savedSubcriptionDomain);

        Subscription newSubscriptionDomain = subscriptionService.addNewSubscription(subscriptionDomain);

        assertSame(savedSubcriptionDomain, newSubscriptionDomain);
        verify(subscriptionRepositoryDomain).save(any(Subscription.class));
    }

    @Test
    void manageOptions_shouldSaveWhenNetflixAndMusicAndHDWithFiber() {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.FIBER);

        Option optionDomain = new Option();
        optionDomain.setOptionType(OptionType.NETFLIX);

        Option optionDomain2 = new Option();
        optionDomain2.setOptionType(OptionType.MUSIC);

        Option optionDomain3 = new Option();
        optionDomain3.setOptionType(OptionType.HD);

        subscriptionDomain.setOptionList(new java.util.ArrayList<>());
        subscriptionDomain.getOptionsList().add(optionDomain);
        subscriptionDomain.getOptionsList().add(optionDomain2);
        subscriptionDomain.getOptionsList().add(optionDomain3);

        Subscription savedSubscriptionDomain = mock(Subscription.class);
        when(subscriptionRepositoryDomain.save(any(Subscription.class))).thenReturn(savedSubscriptionDomain);

        Subscription newSubscriptionDomain = subscriptionService.addNewSubscription(subscriptionDomain);

        assertSame(savedSubscriptionDomain, newSubscriptionDomain);
        verify(subscriptionRepositoryDomain).save(any(Subscription.class));
    }
}
