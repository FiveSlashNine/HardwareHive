package com.example.HardwareHive_Backend.service;

import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.model.User;
import com.example.HardwareHive_Backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> createUser(User user) {
        userRepository.save(user).doOnError(e -> System.err.println("Error: " + e.getMessage()))
                .subscribe();
        return Mono.just(user);
    }

    public Mono<User> updateUser(User user) {
        userRepository.save(user).doOnError(e -> System.err.println("Error: " + e.getMessage()))
                .subscribe();
        return Mono.just(user);
    }

    public Mono<User> deleteUserById(Long userId) {
        return userRepository.deleteUserById(userId);
    }
}
