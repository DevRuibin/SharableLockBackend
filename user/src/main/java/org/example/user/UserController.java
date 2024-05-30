package org.example.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<UserModel> create(UserRequest user) {
        UserModel userResponse = service.createUser(user);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Find user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Long id, UserModel user) {
        UserModel userResponse = service.updateUser(id, user);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Delete an existing user")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
