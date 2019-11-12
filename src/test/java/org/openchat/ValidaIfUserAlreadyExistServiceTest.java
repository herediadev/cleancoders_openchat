package org.openchat;

import org.junit.jupiter.api.Test;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.CreateNewUserRequest;
import org.openchat.usercases.CreateNewUserService;
import org.openchat.usercases.UserAlreadyExistException;
import org.openchat.usercases.ValidaIfUserAlreadyExistService;

public class ValidaIfUserAlreadyExistServiceTest {
    @Test
    void given_an_user_id_it_will_check_the_user_exist() {
        //arrange
        String username = "username";
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService = new ValidaIfUserAlreadyExistService(inMemoryUserRepository);
        CreateNewUserService createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        CreateNewUserRequest createNewUserRequest = new CreateNewUserRequest("username", "password", "about");

        //act
        createNewUserService.execute(createNewUserRequest);

        //assert
        org.junit.jupiter.api.Assertions.assertThrows(UserAlreadyExistException.class, () -> {
            validaIfUserAlreadyExistService.execute(username);
        });
    }
}
