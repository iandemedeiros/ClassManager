package ClassManager.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import ClassManager.app.dtos.PaymentRequestDTO;
import ClassManager.app.dtos.StudentRequestDTO;
import ClassManager.app.dtos.StudentResponseDTO;
import ClassManager.app.services.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @RequestParam Long userId,
            @Valid @RequestBody StudentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> listAll() {
        return ResponseEntity.ok(studentService.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<StudentResponseDTO> registerPayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequestDTO payment) {
        return ResponseEntity.ok(studentService.registerPayment(id, payment));
    }
}
