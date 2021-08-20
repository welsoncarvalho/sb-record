package com.example.sb.record.persistence.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document("user")
public record UserEntity(
        @Id
        String id,
        String firstName,
        String lastName,
        LocalDate birth,
        String email,
        LocalDateTime createdAt) {

    @Builder
    public UserEntity{}
}
