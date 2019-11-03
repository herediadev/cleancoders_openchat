package org.openchat.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryUserRepositoryTest {

    @Test
    void init() {
        //arrange
        User user = new User("id", "username", "about");
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
                    Assertions.assertThat(userFound.getAbout()).isEqualTo("about");
                }));
    }
}