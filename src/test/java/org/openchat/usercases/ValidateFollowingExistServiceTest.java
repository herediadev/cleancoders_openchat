package org.openchat.usercases;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.usercases.exceptions.FollowingAlreadyExistException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ValidateFollowingExistServiceTest {

    @Mock
    private InMemoryFollowingsRepository inMemoryFollowingsRepository;

    @Test
    void given_a_following_request_when_adding_new_following_that_exist_it_will_thrown_an_exception() {
        //arrange
        FollowingRequest followingRequest = new FollowingRequest("test_followee_id", "test_following_id");
        ValidateFollowingExistService validateFollowingExistService = new ValidateFollowingExistService(inMemoryFollowingsRepository);
        doReturn(Optional.of("test_followeeId")).when(inMemoryFollowingsRepository).checkIfExist(eq(followingRequest));

        //act and assert
        Assertions.assertThrows(FollowingAlreadyExistException.class, () -> validateFollowingExistService.accept(followingRequest));
    }
}