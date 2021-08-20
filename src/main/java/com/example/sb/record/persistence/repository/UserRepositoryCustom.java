package com.example.sb.record.persistence.repository;

import com.example.sb.record.persistence.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserRepositoryCustom {

    Mono<Void> update(UserEntity entity);
}
