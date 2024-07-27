package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            return new ResponseEntity<>(userService.getUserByEmail(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("something went wrong", HttpStatus.EXPECTATION_FAILED);
        }

    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestParam String username, @RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.updateUser(username, user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.EXPECTATION_FAILED);
        }
    
}
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        try {
            return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("something went wrong", HttpStatus.EXPECTATION_FAILED);

        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginData) {
        User user = userService.checkLogin(loginData.getUsername(), loginData.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);  // Status 200
        } else {
            return ResponseEntity.status(404).body("User not found");  // Status 404
        }
    }
}
