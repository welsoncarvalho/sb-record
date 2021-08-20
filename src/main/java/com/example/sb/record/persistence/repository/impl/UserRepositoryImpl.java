package com.example.sb.record.persistence.repository.impl;

import com.example.sb.record.persistence.entity.UserEntity;
import com.example.sb.record.persistence.repository.UserRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

@Slf4j
public record UserRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate)
    implements UserRepositoryCustom {

    @Override
    public Mono<Void> update(UserEntity entity) {
        return reactiveMongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(entity.id())),
                Update.update("firstName", entity.firstName())
                        .set("lastName", entity.lastName())
                        .set("birth", entity.birth())
                        .set("email", entity.email()),
                UserEntity.class)
                .doOnNext(result -> log.debug("{} items updated", result.getModifiedCount()))
                .then();
    }
}
