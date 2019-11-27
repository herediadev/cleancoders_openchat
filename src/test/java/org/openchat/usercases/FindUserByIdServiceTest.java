package org.openchat.usercases;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

class FindUserByIdServiceTest {

    @Test
    void given_the_username_it_will_return_the_user() {
        //arrange
        User user = new User("test_user_id", "test_user_name", "test_password", "test_about");
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        FindUserByIdService findUserByIdService = new FindUserByIdService(inMemoryUserRepository);

        //act
        inMemoryUserRepository.save(user);
        User userFound = findUserByIdService.execute(user.getId());

        //assert
        Assertions.assertThat(userFound.getUsername()).isEqualTo("test_user_name");

    }
}