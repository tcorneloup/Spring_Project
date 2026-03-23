package recruitment.itsf.web.dto;

import recruitment.itsf.domain.model.OptionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@NotNull
public record OptionRequest(
        @NotNull OptionType name,
        @NotNull LocalDateTime optionSubDateStart) {
}
