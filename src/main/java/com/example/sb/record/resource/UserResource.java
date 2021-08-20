package com.example.sb.record.resource;

import com.example.sb.record.model.User;
import com.example.sb.record.model.UserRequest;
import com.example.sb.record.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/user")
public record UserResource(UserService userService) {

    @GetMapping
    public Flux<User> list() {
        return userService.list();
    }

    @GetMapping(path = "/{id}")
    public Mono<User> findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PostMapping
    public Mono<User> save(@RequestBody UserRequest user) {
        return userService.save(user);
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable("id") String id, @RequestBody UserRequest user) {
        return userService.update(id, user);
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return userService.delete(id);
    }
}
