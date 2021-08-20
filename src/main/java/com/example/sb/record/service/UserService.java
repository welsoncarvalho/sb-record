package com.example.sb.record.service;

import com.example.sb.record.model.User;
import com.example.sb.record.model.UserRequest;
import com.example.sb.record.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public record UserService(UserRepository userRepository) {

    public Mono<User> save(UserRequest user) {
        return Mono.just(user)
                .map(UserRequest::toNewEntity)
                .flatMap(userRepository::save)
                .map(User::fromEntity);
    }

    public Mono<Void> update(String id, UserRequest user) {
        return userRepository.existsById(id)
                .flatMap(exists -> exists
                        ? Mono.just(id)
                        : Mono.error(() -> new IllegalArgumentException("Invalid argument " + id)))
                .map(user::toUpdateEntity)
                .flatMap(userRepository::update);
    }

    public Flux<User> list() {
        return userRepository.findAll()
                .map(User::fromEntity);
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id)
                .map(User::fromEntity);
    }

    public Mono<Void> delete(String id) {
        return userRepository.deleteById(id);
    }
}
