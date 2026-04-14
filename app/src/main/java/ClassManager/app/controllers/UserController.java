package ClassManager.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ClassManager.app.dtos.UserDTO;
import ClassManager.app.entities.User;
import ClassManager.app.services.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Permite criar um professor novo no banco
    @PostMapping
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody User user) {
        User created = userService.createUser(user);
        UserDTO responseDTO = new UserDTO(created.getId(), created.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
