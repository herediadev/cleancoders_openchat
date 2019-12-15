package org.openchat.usercases;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

import java.util.List;

class FindAllUserServiceTest {

    private CreateNewUserService createNewUserService;
    private FindAllUserService findAllUserService;

    @BeforeEach
    void setUp() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        findAllUserService = new FindAllUserService(inMemoryUserRepository);
    }

    @Test
    void it_will_return_all_the_users() {
        //act
        User userAdded = createNewUserService.apply(new CreateNewUserRequest("username", "password", "about"));
        List<User> users = findAllUserService.execute();

        //assert
        Assertions.assertThat(users).containsExactly(userAdded);
    }
}