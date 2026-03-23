package recruitment.itsf.infra;

import recruitment.itsf.infra.entity.OptionEntity;
import recruitment.itsf.infra.entity.SubscriptionEntity;
import recruitment.itsf.domain.model.OptionType;
import recruitment.itsf.domain.model.SubscriptionType;
import recruitment.itsf.infra.repository.SubscriptionRepositoryInfra;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SubscriptionRepositoryInfraDomainTest {

    @Autowired
    private SubscriptionRepositoryInfra subscriptionRepositoryInfra;

    @Test
    void findAllSubscriptions_shouldReturnEmptyListWhenNoSubscriptionsExist() {
        List<SubscriptionEntity> subscriptionsEntityList = subscriptionRepositoryInfra.findAllSubscriptions();

        assertNotNull(subscriptionsEntityList);
        assertTrue(subscriptionsEntityList.isEmpty());
    }

    @Test
    void findAllSubscriptions_shouldReturnSubscriptionsWithoutOptions() {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientId(1L);
        subscriptionEntity.setType(SubscriptionType.MOBILE);
        subscriptionEntity.setSubscriptionDateStart(LocalDateTime.now());
        subscriptionRepositoryInfra.save(subscriptionEntity);

        List<SubscriptionEntity> subscriptionEntityList = subscriptionRepositoryInfra.findAllSubscriptions();

        assertEquals(1, subscriptionEntityList.size());
        assertEquals(SubscriptionType.MOBILE, subscriptionEntityList.getFirst().getType());
        assertTrue(subscriptionEntityList.getFirst().getOptionsList().isEmpty());
    }

    @Test
    void findAllSubscriptions_shouldReturnSubscriptionsWithOption() {
        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setOptionType(OptionType.NETFLIX);
        optionEntity.setOptionSubDateStart(LocalDateTime.now());

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientId(2L);
        subscriptionEntity.setType(SubscriptionType.FIBER);
        subscriptionEntity.setSubscriptionDateStart(LocalDateTime.now());
        subscriptionEntity.setOptionsList(new ArrayList<>(List.of(optionEntity)));
        subscriptionRepositoryInfra.save(subscriptionEntity);

        List<SubscriptionEntity> subscriptionEntityList = subscriptionRepositoryInfra.findAllSubscriptions();

        assertEquals(1, subscriptionEntityList.size());
        assertEquals(1, subscriptionEntityList.getFirst().getOptionsList().size());
        assertEquals(OptionType.NETFLIX, subscriptionEntityList.getFirst().getOptionsList().getFirst().getOptionType());
    }

    @Test
    void findAllSubscriptions_shouldNotReturnDuplicatesWhenSubscriptionHasMultipleOptions() {
        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setOptionType(OptionType.NETFLIX);
        optionEntity.setOptionSubDateStart(LocalDateTime.now());

        OptionEntity optionEntity1 = new OptionEntity();
        optionEntity1.setOptionType(OptionType.MUSIC);
        optionEntity1.setOptionSubDateStart(LocalDateTime.now());

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientId(3L);
        subscriptionEntity.setType(SubscriptionType.FIBER);
        subscriptionEntity.setSubscriptionDateStart(LocalDateTime.now());
        subscriptionEntity.setOptionsList(new ArrayList<>(List.of(optionEntity, optionEntity1)));
        subscriptionRepositoryInfra.save(subscriptionEntity);

        List<SubscriptionEntity> subscriptionEntityList = subscriptionRepositoryInfra.findAllSubscriptions();

        assertEquals(1, subscriptionEntityList.size());
        assertEquals(2, subscriptionEntityList.getFirst().getOptionsList().size());
    }

    @Test
    void saveSubscriptionEntity_shouldPersistAndGenerateId() {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientId(42L);
        subscriptionEntity.setType(SubscriptionType.FIX);
        subscriptionEntity.setSubscriptionDateStart(LocalDateTime.now());

        SubscriptionEntity savedSubscriptionEntity = subscriptionRepositoryInfra.save(subscriptionEntity);

        assertNotNull(savedSubscriptionEntity.getId());
        assertEquals(42L, savedSubscriptionEntity.getClientId());
    }

    @Test
    void findById_shouldReturnEmptySubscriptionEntityWhenIdDoesNotExist() {
        Optional<SubscriptionEntity> subscriptionEntity = subscriptionRepositoryInfra.findById(999L);

        assertTrue(subscriptionEntity.isEmpty());
    }

    @Test
    void findById_shouldReturnEntityWhenIdExists() {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity();
        subscriptionEntity.setClientId(10L);
        subscriptionEntity.setType(SubscriptionType.MOBILE);
        subscriptionEntity.setSubscriptionDateStart(LocalDateTime.now());
        SubscriptionEntity savedsubscriptionentity = subscriptionRepositoryInfra.save(subscriptionEntity);

        Optional<SubscriptionEntity> optionalSubscriptionEntity = subscriptionRepositoryInfra.findById(savedsubscriptionentity.getId());

        assertTrue(optionalSubscriptionEntity.isPresent());
        assertEquals(10L, optionalSubscriptionEntity.get().getClientId());
    }
}

