package ClassManager.app.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ClassManager.app.entities.ClassStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassSessionDTO {
    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private ClassStatus status;
    private Long userId;
    private List<Long> studentIds;
}
