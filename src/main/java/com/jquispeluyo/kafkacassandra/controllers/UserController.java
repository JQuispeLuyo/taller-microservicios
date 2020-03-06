package com.jquispeluyo.kafkacassandra.controllers;

import com.datastax.driver.core.utils.UUIDs;

import com.jquispeluyo.kafkacassandra.models.User;
import com.jquispeluyo.kafkacassandra.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        user.setUser_id(UUIDs.timeBased());
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") UUID id, @RequestBody User user) {
        return userRepository.findById(id).map((x) -> {
            user.setUser_id(id);
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        }).orElseGet(()->{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>("User has been deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<String> deleteAllUsers() {
        userRepository.deleteAll();
        return new ResponseEntity<>("All Users have been deleted!", HttpStatus.OK);
    }
}
