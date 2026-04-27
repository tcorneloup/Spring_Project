package project.spring.web.dto;

import project.spring.domain.model.OptionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OptionResponse(
        @NotNull Long id,
        @NotNull OptionType optionType,
        @NotNull LocalDateTime optionSubDateStart) {
}
