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

import NeoBivago.dto.user.UserDTO;
import NeoBivago.entities.User;
import NeoBivago.enums.ERole;
import NeoBivago.enums.ERoleRepository;
import NeoBivago.exceptions.UnauthorizedDateException;
import NeoBivago.exceptions.ExistingAttributeException;
import NeoBivago.exceptions.LenghtException;
import NeoBivago.repositories.UserRepository;
import NeoBivago.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserRepository ur;

    @Autowired
    UserService us;

    @Autowired
    ERoleRepository rr;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDTO data) {
        
        Optional<ERole> optionalRole = rr.findById(data.role());
        if (!optionalRole.isPresent()) return ResponseEntity.badRequest().body(null);        

        User newUser = new User(data.name(), data.email(), data.password(), data.cpf(), data.birthday(), optionalRole );

        try {
            this.us.create(newUser);
            return new ResponseEntity<>("Created User", HttpStatus.CREATED);
        }

        catch (ExistingAttributeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.CONFLICT);
        }
        
        catch (LenghtException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        catch (UnauthorizedDateException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.FORBIDDEN);
        } 

        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }        

    }

    @Operation(summary = "Find All Users in NeoBivago", description = "Return a list of all users registered in NeoBivago.")
    @GetMapping
    public ResponseEntity<List<User>> readAllUsers() {

        List<User> userList = this.ur.findAll();

        return new ResponseEntity<>(userList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable UUID id) {

        Optional<User> user = this.ur.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody Map<String, Object> fields, @PathVariable UUID id) {

        try {
            this.us.update(id, fields);
            return new ResponseEntity<>("Updated User", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {

        try {
            this.us.delete(id);
            return new ResponseEntity<>("Deleted User", HttpStatus.OK);
        } 
        
        catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
