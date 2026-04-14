package ClassManager.app.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ClassManager.app.entities.ClassStatus;

public record ClassSessionResponseDTO(
        Long id,
        LocalDate date,
        LocalTime startTime,
        ClassStatus status,
        List<StudentResponseDTO> students) {
}
