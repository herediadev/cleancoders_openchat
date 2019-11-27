package org.openchat.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

class InMemoryUserRepositoryTest {

    @Test
    void init() {
        //arrange
        User user = new User("id", "username","password", "about");
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();

        //act
        inMemoryUserRepository.execute(users -> users.add(user));

        //assert
        inMemoryUserRepository.execute(users -> users.stream()
                .filter(user1 -> "id".equals(user1.getId()))
                .findFirst()
                .ifPresent(userFound -> {
                    Assertions.assertThat(userFound.getId()).isEqualTo("id");
                    Assertions.assertThat(userFound.getUsername()).isEqualTo("username");
                    Assertions.assertThat(userFound.getPassword()).isEqualTo("password");
                    Assertions.assertThat(userFound.getAbout()).isEqualTo("about");
                }));
    }
}