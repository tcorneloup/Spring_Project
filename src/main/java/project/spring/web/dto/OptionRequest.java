package project.spring.web.dto;

import project.spring.domain.model.OptionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@NotNull
public record OptionRequest(
        @NotNull OptionType name,
        @NotNull LocalDateTime optionSubDateStart) {
}
