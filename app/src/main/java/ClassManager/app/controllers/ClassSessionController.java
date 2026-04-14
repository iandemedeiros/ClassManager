package ClassManager.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import ClassManager.app.dtos.ClassSessionRequestDTO;
import ClassManager.app.dtos.ClassSessionResponseDTO;
import ClassManager.app.entities.ClassStatus;
import ClassManager.app.services.ClassSessionService;

@RestController
@RequestMapping("/classes")
public class ClassSessionController {

    @Autowired
    private ClassSessionService classSessionService;

    @PostMapping
    public ResponseEntity<ClassSessionResponseDTO> createSession(
            @Valid @RequestBody ClassSessionRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classSessionService.createSession(request));
    }

    @GetMapping
    public ResponseEntity<List<ClassSessionResponseDTO>> listAll() {
        return ResponseEntity.ok(classSessionService.listAll());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ClassSessionResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam ClassStatus status) {
        return ResponseEntity.ok(classSessionService.updateStatus(id, status));
    }
}
