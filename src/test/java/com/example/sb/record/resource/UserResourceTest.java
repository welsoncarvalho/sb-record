package com.example.sb.record.resource;

import com.example.sb.record.handler.ErrorHandler;
import com.example.sb.record.model.User;
import com.example.sb.record.model.UserRequest;
import com.example.sb.record.persistence.entity.UserEntity;
import com.example.sb.record.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@SpringBootTest
public class UserResourceTest {

    @Autowired
    private ApplicationContext applicationContext;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    public void testSave() {
        UserRequest userRequest = new UserRequest("save", "test", LocalDate.now(), "save.test@test.com");

        webTestClient.post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> {
                    Assertions.assertNotNull(user.id());
                    Assertions.assertNotNull(user.createdAt());
                });
    }

    @Test
    public void testList() {
        String id = UUID.randomUUID().toString();
        saveUserTest(() -> new UserEntity(id, "list", "test", LocalDate.now(), "list.test@test.com", LocalDateTime.now()));

        webTestClient.get()
                .uri("/user")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> {
                    Assertions.assertTrue(users.size() > 0);
                    Assertions.assertTrue(users.stream().anyMatch(u -> u.id().equals(id)));
                });
    }

    @Test
    public void testFindById() {
        String id = UUID.randomUUID().toString();
        saveUserTest(() -> new UserEntity(id, "find", "test", LocalDate.now(), "find.test@test.com", LocalDateTime.now()));

        webTestClient.get()
                .uri("/user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> {
                    Assertions.assertNotNull(user);
                    Assertions.assertEquals(id, user.id());
                    Assertions.assertEquals("find", user.firstName());
                    Assertions.assertEquals("test", user.lastName());
                });
    }

    @Test
    public void testUpdate() {
        String id = UUID.randomUUID().toString();
        saveUserTest(() -> new UserEntity(id, "update", "test", LocalDate.now(), "update.test@test.com", LocalDateTime.now()));

        UserRequest userRequest = new UserRequest("update2", "test", LocalDate.now(), "update2.test@test.com");

        webTestClient.put()
                .uri("/user/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> {
                    Assertions.assertNotNull(user);
                    Assertions.assertEquals(id, user.id());
                    Assertions.assertEquals("update2", user.firstName());
                    Assertions.assertEquals("update2.test@test.com", user.email());
                });
    }

    @Test
    public void testUpdateIllegalId() {
        UserRequest userRequest = new UserRequest("update2", "test", LocalDate.now(), "update2.test@test.com");

        record Error(String message){}

        webTestClient.put()
                .uri("/user/wrongId")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userRequest), UserRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Error.class)
                .value(error -> Assertions.assertEquals("Invalid argument wrongId", error.message));
    }

    @Test
    public void testDelete() {
        String id = UUID.randomUUID().toString();
        saveUserTest(() -> new UserEntity(id, "delete", "test", LocalDate.now(), "delete.test@test.com", LocalDateTime.now()));

        webTestClient.delete()
                .uri("/user/" + id)
                .exchange()
                .expectStatus().isNoContent();

        webTestClient.get()
                .uri("/user/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(Assertions::assertNull);
    }

    private void saveUserTest(Supplier<UserEntity> function) {
        applicationContext.getBean(UserRepository.class)
                .save(function.get())
                .subscribe();
    }
}
