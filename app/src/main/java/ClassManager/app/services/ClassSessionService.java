package ClassManager.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ClassManager.app.dtos.ClassSessionRequestDTO;
import ClassManager.app.dtos.ClassSessionResponseDTO;
import ClassManager.app.dtos.StudentResponseDTO;
import ClassManager.app.entities.ClassSession;
import ClassManager.app.entities.ClassStatus;
import ClassManager.app.entities.Student;
import ClassManager.app.entities.User;
import ClassManager.app.exceptions.BusinessException;
import ClassManager.app.exceptions.ResourceNotFoundException;
import ClassManager.app.mapper.StudentMapper;
import ClassManager.app.repositories.ClassSessionRepository;
import ClassManager.app.repositories.StudentRepository;
import ClassManager.app.repositories.UserRepository;

@Service
public class ClassSessionService {

    @Autowired
    private ClassSessionRepository classSessionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ClassSessionResponseDTO createSession(ClassSessionRequestDTO request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Student> students = studentRepository.findAllById(request.studentIds());
        if (students.isEmpty()) {
            throw new BusinessException("Session must have at least one valid student");
        }

        ClassSession session = new ClassSession();
        session.setDate(request.date());
        session.setStartTime(request.startTime());
        session.setStatus(ClassStatus.SCHEDULED);
        session.setUser(user);
        session.setStudents(students);

        session = classSessionRepository.save(session);
        return toResponseDTO(session);
    }

    public List<ClassSessionResponseDTO> listAll() {
        return classSessionRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ClassSessionResponseDTO updateStatus(Long sessionId, ClassStatus newStatus) {
        ClassSession session = classSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        // Se a classe já está concluída, não pode ser alterada.
        if (session.getStatus() == ClassStatus.COMPLETED) {
            throw new BusinessException("A completed class cannot be modified.");
        }

        // Se está sendo marcada como concluída, debita 1 ticket de cada aluno.
        if (newStatus == ClassStatus.COMPLETED) {
            for (Student student : session.getStudents()) {
                student.setTicket(student.getTicket() - 1);
            }
            studentRepository.saveAll(session.getStudents());
        }

        // Uma aula já tem status checado acima para COMPLETED.
        // Logos SCHEDULED e CANCELED podem pular aqui.

        session.setStatus(newStatus);
        session = classSessionRepository.save(session);

        return toResponseDTO(session);
    }

    private ClassSessionResponseDTO toResponseDTO(ClassSession session) {
        List<StudentResponseDTO> studentDTOs = session.getStudents().stream()
                .map(StudentMapper::toResponseDTO)
                .collect(Collectors.toList());
        return new ClassSessionResponseDTO(
                session.getId(),
                session.getDate(),
                session.getStartTime(),
                session.getStatus(),
                studentDTOs);
    }
}
