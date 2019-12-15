package org.openchat.usercases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryUserRepository;

import static integration.APITestSuit.UUID_PATTERN;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateNewUserServiceTest {

    @Mock
    private InMemoryUserRepository userRepository;

    @Test
    void given_the_create_new_user_request_it_will_return_the_new_user() {
        //arrange
        CreateNewUserRequest createNewUserRequest = new CreateNewUserRequest("username", "password", "about");
        CreateNewUserService createNewUserService = new CreateNewUserService(userRepository);

        //act
        User user = createNewUserService.apply(createNewUserRequest);

        //assert
        assertThat(user.getId()).matches(UUID_PATTERN);
        assertThat(user.getUsername()).isEqualTo("username");
        assertThat(user.getAbout()).isEqualTo("about");
    }
}