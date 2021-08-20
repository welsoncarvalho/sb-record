package com.example.sb.record.model;

import com.example.sb.record.persistence.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserRequest(String firstName, String lastName, LocalDate birth, String email) {

    public UserEntity toNewEntity() {
        return UserEntity.builder()
                .id(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .birth(birth)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public UserEntity toUpdateEntity(String id) {
        return UserEntity.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .birth(birth)
                .email(email)
                .build();
    }
}
