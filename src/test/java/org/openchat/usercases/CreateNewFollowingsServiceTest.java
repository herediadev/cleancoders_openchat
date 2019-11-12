package org.openchat.usercases;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateNewFollowingsServiceTest {

    @Mock
    private InMemoryFollowingsRepository inMemoryFollowingsRepository;

    @Test
    void given_the_create_following_request_when_is_adding_a_new_following_it_will_add_the_following_and_followee_relationship() {
        //arrange
        FollowingRequest followingRequest = new FollowingRequest("test_followeeId", "test_followingId");
        CreateNewFollowingsService createNewFollowingsService = new CreateNewFollowingsService(inMemoryFollowingsRepository);

        //act
        createNewFollowingsService.execute(followingRequest);

        //assert
        verify(inMemoryFollowingsRepository).addNewFollowing(eq(followingRequest));
    }
}