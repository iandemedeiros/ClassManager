package ClassManager.app.mapper;

import ClassManager.app.dtos.StudentResponseDTO;
import ClassManager.app.entities.Student;

public class StudentMapper {

    public static StudentResponseDTO toResponseDTO(Student student) {
        // Mapeia os dados da Entidade Student para o DTO de Resposta que é exposto na
        // API
        return new StudentResponseDTO(
                student.getName(),
                student.getTicket(),
                student.getDefaultPrice(),
                student.getStatus());
    }
}
