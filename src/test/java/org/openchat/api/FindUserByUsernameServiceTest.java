package org.openchat.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FindUserByUsernameServiceTest {
    @Test
    void given_an_user_id_it_will_return_the_user() {
        //arrange
        String username = "username";
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        FindUserByUsernameService findUserByUsernameService = new FindUserByUsernameService(inMemoryUserRepository);
        CreateNewUserService createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        CreateNewUserRequest createNewUserRequest = new CreateNewUserRequest("username", "password", "about");

        //act
        createNewUserService.execute(createNewUserRequest);
        User user = findUserByUsernameService.execute(username);

        //assert
        Assertions.assertThat(user.getUsername()).isEqualTo(username);
    }
}
