package org.example.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    public ResponseEntity<UserModel> create(@RequestBody UserRequest user) {
        // send a log message to logging message in async
        UserModel userResponse = service.createUser(user);
        return ResponseEntity.ok(userResponse);
    }
    @Operation(summary = " Login a user",
            description = "Login user with email and password",
            responses = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "403", description = "User not found or password is incorrect")
    })
    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestBody UserRequest user) {
        UserModel userResponse = service.login(user);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Retrieve user information.")
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Update an existing user")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Long id, @RequestBody UserModel user) {
        UserModel userResponse = service.updateUser(id, user);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "Change the password of an existing user")
    @PutMapping("/{id}/change-password")
    public ResponseEntity<UserModel> changePassword(@PathVariable Long id, @RequestBody ChangePwdRequest changePwdRequest) {
        UserModel userResponse = service.changePassword(id, changePwdRequest);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(summary = "request to get a dynamic code")
    @GetMapping("/{user-id}/request-code")
    public ResponseEntity<Void> requestCode(@PathVariable("user-id") Long userId) {
        service.requestCode(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Change the password with a dynamic code")
    @PutMapping("/{id}/change-password-with-code")
    public ResponseEntity<UserModel> changePasswordWithCode(@PathVariable Long id, @RequestBody ChangePwdRequest changePwdRequest) {
        try{
            System.out.println("Code: " + changePwdRequest.getCode());
            System.out.println("new password: " + changePwdRequest.getNewPassword());
            UserModel userResponse = service.changePasswordWithCode(id, changePwdRequest);
            return ResponseEntity.ok(userResponse);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    @Operation(summary = "Delete an existing user")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
