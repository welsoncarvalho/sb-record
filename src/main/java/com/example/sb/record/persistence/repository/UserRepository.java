package com.example.sb.record.persistence.repository;

import com.example.sb.record.persistence.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserEntity, String>, UserRepositoryCustom {

}
