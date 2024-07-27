package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        Optional<User> userExists = userRepository.findByUsername(user.getUsername());
        if (userExists.isPresent())
            return;
        userRepository.save(user);
    }

    public User getUserByEmail(String username) {
        boolean userExists = userRepository.existsByUsername(username);
        if (userExists) {
            return userRepository.findByUsername(username).get();
        }
        return new User();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    public User updateUser(String username, User user) {
        Optional<User> userExists = userRepository.findByUsername(username);
        if (userExists.isPresent()) {
            User existingUser = userExists.get();
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
        return new User();
    }

    public String deleteUser(String username) {
        boolean userExists = userRepository.existsByUsername(username);
        if (userExists) {
            userRepository.deleteByUsername(username);
            return "User deleted successfully";
        }
        return "given email id does not exists";

    }

    public User checkLogin(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        return user.orElse(null); // Returns the user if present, otherwise returns null
    }
}
