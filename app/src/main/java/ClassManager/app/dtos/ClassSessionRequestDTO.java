package ClassManager.app.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

public record ClassSessionRequestDTO(
        @NotNull(message = "Date is required") LocalDate date,
        @NotNull(message = "Start time is required") LocalTime startTime,
        @NotNull(message = "User ID is required") Long userId, // Represents the teacher
        @NotEmpty(message = "At least one student must be added to the session") List<Long> studentIds) {
}
