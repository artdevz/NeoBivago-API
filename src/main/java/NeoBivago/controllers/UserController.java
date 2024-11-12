package NeoBivago.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NeoBivago.dto.UserRequestDTO;
import NeoBivago.dto.UserResponseDTO;
import NeoBivago.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userS;    

    public UserController(UserService userS) {        
        this.userS = userS;
    }

    @Operation(summary = "Create an User in NeoBivago")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User successfully created."),
        @ApiResponse(responseCode = "400", description = "Invalid email or CPF."),
        @ApiResponse(responseCode = "403", description = "User is underage."),
        @ApiResponse(responseCode = "409", description = "Email or CPF already in use."),
        @ApiResponse(responseCode = "422", description = "Username or password length is invalid.")
    })
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserRequestDTO data) {        
        
        this.userS.create(data);
        return new ResponseEntity<>("Created User", HttpStatus.CREATED);              

    }

    @Operation(summary = "Get a list of all users in NeoBivago")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of all users returned successfully.")
    })
    @GetMapping    
    public ResponseEntity<List<UserResponseDTO>> readAllUsers() {

        return new ResponseEntity<>(this.userS.readAll(), HttpStatus.OK);

    }

    @Operation(summary = "Find a user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found and returned successfully."),
        @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable UUID id) {

        UserResponseDTO user = userS.readById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    
    @Operation(summary = "Update fields of an existing user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully."),
        @ApiResponse(responseCode = "400", description = "Invalid field value provided."),
        @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {
        
        this.userS.update(id, fields);
        return new ResponseEntity<>("Updated User", HttpStatus.OK);
        
    }

    @Operation(summary = "Delete a user by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User deleted successfully."),
        @ApiResponse(responseCode = "404", description = "User not found.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        
        this.userS.delete(id);
        return new ResponseEntity<>("Deleted User", HttpStatus.OK);
           
    }

}
