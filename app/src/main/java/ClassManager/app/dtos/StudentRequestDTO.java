package ClassManager.app.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentRequestDTO(
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Default price is required")
        @Min(value = 0, message = "Default price must be a non-negative value") Double defaultPrice) {
}
