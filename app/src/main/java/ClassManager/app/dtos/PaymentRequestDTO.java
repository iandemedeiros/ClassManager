package ClassManager.app.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDTO(
        @NotNull(message = "Classes paid amount is required") @Min(value = 1, message = "Payment must be for at least 1 class") Integer classesPaid) {
}
