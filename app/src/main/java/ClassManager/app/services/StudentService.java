package ClassManager.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ClassManager.app.dtos.PaymentRequestDTO;
import ClassManager.app.dtos.StudentRequestDTO;
import ClassManager.app.dtos.StudentResponseDTO;
import ClassManager.app.entities.Student;
import ClassManager.app.entities.User;
import ClassManager.app.exceptions.ResourceNotFoundException;
import ClassManager.app.mapper.StudentMapper;
import ClassManager.app.repositories.StudentRepository;
import ClassManager.app.repositories.UserRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Student student = new Student();
        student.setName(request.name());
        student.setDefaultPrice(request.defaultPrice());
        student.setUser(user);
        student.setTicket(0);
        // student.setStatus depends on how the codebase updated it, defaulting to true
        // or ACTIVE.

        student = studentRepository.save(student);
        return StudentMapper.toResponseDTO(student);
    }

    public List<StudentResponseDTO> listAll() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        // Cannot update ticket here, only the basic fields
        student.setName(request.name());
        student.setDefaultPrice(request.defaultPrice());

        student = studentRepository.save(student);
        return StudentMapper.toResponseDTO(student);
    }

    @Transactional
    public StudentResponseDTO registerPayment(Long id, PaymentRequestDTO request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        // Incrementa o ticket baseado nas aulas pagas
        student.setTicket(student.getTicket() + request.classesPaid());

        student = studentRepository.save(student);
        return StudentMapper.toResponseDTO(student);
    }
}
