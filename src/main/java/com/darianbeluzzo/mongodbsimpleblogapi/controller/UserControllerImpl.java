package com.darianbeluzzo.mongodbsimpleblogapi.controller;

import com.darianbeluzzo.mongodbsimpleblogapi.exception.ResourceNotFoundException;
import com.darianbeluzzo.mongodbsimpleblogapi.model.User;
import com.darianbeluzzo.mongodbsimpleblogapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserControllerImpl implements UserController {

    private final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        var users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        logger.info("Username -> {} ", username);
        var user = userService.findUserByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity.accepted().body(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid User user) {
        var savedUser = userService.save(user);
        return ResponseEntity.accepted().body(savedUser);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) throws ResourceNotFoundException {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user) throws ResourceNotFoundException {
        var savedUser = userService.update(user);
        return ResponseEntity.accepted().body(savedUser);
    }
}
