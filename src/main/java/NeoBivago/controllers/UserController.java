package NeoBivago.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.server.ResponseStatusException;

import NeoBivago.dto.user.UserDTO;
import NeoBivago.models.User;
import NeoBivago.repositories.UserRepository;
import NeoBivago.services.MappingService;
import NeoBivago.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserRepository userR;

    @Autowired
    UserService userS;

    @Autowired
    MappingService mappingS;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO data) throws Exception {        
        
        this.userS.create(new User( data.name(), data.email(), data.password(), data.cpf(), data.birthday(), mappingS.getRole(data.role().getName()) ));
        return new ResponseEntity<>("Created User", HttpStatus.CREATED);              

    }

    @Operation(summary = "Find All Users in NeoBivago", description = "Return a list of all users registered in NeoBivago.")
    @GetMapping
    public ResponseEntity<List<User>> readAllUsers() {

        List<User> userList = this.userR.findAll();

        return new ResponseEntity<>(userList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable UUID id) {

        Optional<User> user = this.userR.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        try {
            this.userS.update(id, fields);
            return new ResponseEntity<>("Updated User", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {

        try {
            this.userS.delete(id);
            return new ResponseEntity<>("Deleted User", HttpStatus.OK);
        }
        
        catch (ResponseStatusException e) {
            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
        }
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
