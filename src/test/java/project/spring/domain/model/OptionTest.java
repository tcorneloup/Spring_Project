package project.spring.domain.model;

import project.spring.infra.entity.OptionEntity;
import project.spring.infra.mapper.SubscriptionEntityMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {

    @Test
    void optionEntityToDomain_ShouldReturnNullIfOptionEntityIsNull() {
        assertNull(SubscriptionEntityMapper.optionEntityToDomain(null));
    }

    @Test
    void optionEntityToDomain_ShouldReturnOptionDomainWhenOptionEntityIsNotNull() {
        LocalDateTime newDate = LocalDateTime.of(2026, 3, 10, 12, 30);

        OptionEntity optionEntity = new OptionEntity();
        optionEntity.setId(1L);
        optionEntity.setOptionType(OptionType.NETFLIX);
        optionEntity.setOptionSubDateStart(newDate);

        Option optionDomain = SubscriptionEntityMapper.optionEntityToDomain(optionEntity);

        assertNotNull(optionDomain);
        assertEquals(1L, optionDomain.getId());
        assertEquals(OptionType.NETFLIX, optionDomain.getOptionType());
        assertEquals(newDate, optionDomain.getOptionSubDateStart());
    }

    @Test
    void optionDomainToEntity_ShouldReturnNullIfOptionDomainIsNull() {
        assertNull(SubscriptionEntityMapper.optionDomainToEntity(null));
    }

    @Test
    void optionDomainToEntity_ShouldReturnOptionEntityWhenOptionDomainIsNotNull() {
        LocalDateTime newDate = LocalDateTime.of(2026, 3, 10, 12, 30);

        Option optionDomain = new Option();
        optionDomain.setId(1L);
        optionDomain.setOptionType(OptionType.NETFLIX);
        optionDomain.setOptionSubDateStart(newDate);

        OptionEntity optionEntity = SubscriptionEntityMapper.optionDomainToEntity(optionDomain);

        assertNotNull(optionEntity);
        assertEquals(1L, optionEntity.getId());
        assertEquals(OptionType.NETFLIX, optionEntity.getOptionType());
        assertEquals(newDate, optionEntity.getOptionSubDateStart());
    }
}
