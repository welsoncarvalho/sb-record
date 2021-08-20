package com.example.sb.record.model;

import com.example.sb.record.persistence.entity.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record User(
        String id,
        String firstName,
        String lastName,
        LocalDate birth,
        String email,
        LocalDateTime createdAt) {

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.id(),
                entity.firstName(),
                entity.lastName(),
                entity.birth(),
                entity.email(),
                entity.createdAt()
        );
    }
}
