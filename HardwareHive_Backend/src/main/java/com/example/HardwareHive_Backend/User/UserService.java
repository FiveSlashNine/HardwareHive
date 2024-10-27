package com.example.HardwareHive_Backend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String uuid) {
        return userRepository.findById(uuid);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
       userRepository.save(user);
    }

    public void deleteUser(String uuid) {
        userRepository.deleteById(uuid);
    }
}
