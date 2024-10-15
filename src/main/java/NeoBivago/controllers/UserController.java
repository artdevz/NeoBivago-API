package NeoBivago.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import NeoBivago.dto.auth.RegisterDTO;
import NeoBivago.models.UserModel;
import NeoBivago.repositories.UserRepository;
import NeoBivago.services.UserService;
import jakarta.validation.Valid;

@RequestMapping("/api/user")
@RestController
public class UserController {

    @Autowired
    UserRepository ur;

    @Autowired
    UserService us;

    // CRUD:

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid RegisterDTO data) {
                
        UserModel newUser = new UserModel(data.name(), data.email(), data.password(), data.cpf(), data.birthday() );

        try {
            this.us.create(newUser);
            return new ResponseEntity<>("Created User", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }        

    }

    @GetMapping
    public ResponseEntity<List<UserModel>> readAllUsers() {

        List<UserModel> userList = this.ur.findAll();

        return new ResponseEntity<>(userList, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserModel>> findUserById(@PathVariable UUID id) {

        Optional<UserModel> user = this.ur.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
    /*
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserModel user, @PathVariable UUID id) {

        try {
            this.us.update(id, user);
            return new ResponseEntity<>("Updated User", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {

        try {
            this.us.delete(id);
            return new ResponseEntity<>("Deleted User", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}
