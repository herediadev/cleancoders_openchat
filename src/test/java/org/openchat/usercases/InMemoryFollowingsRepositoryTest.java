package org.openchat.usercases;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class InMemoryFollowingsRepositoryTest {

    @Test
    void given_the_following_request_it_will_add_the_new_relationship() {
        //arrange
        FollowingRequest followingRequest = new FollowingRequest("test_followeeId", "test_followingId");
        InMemoryFollowingsRepository inMemoryFollowingsRepository = new InMemoryFollowingsRepository();

        //act
        inMemoryFollowingsRepository.addNewFollowing(followingRequest);
        Optional<String> ifExistOptional = inMemoryFollowingsRepository.checkIfExist(followingRequest);

        //assert
        Assertions.assertThat(ifExistOptional.isPresent()).isTrue();
    }

    @Test
    void given_the_following_request_when_adding_two_it_will_get_the_following_list_for_the_following_user_id() {
        //arrange
        InMemoryFollowingsRepository inMemoryFollowingsRepository = new InMemoryFollowingsRepository();

        //act
        inMemoryFollowingsRepository.addNewFollowing(new FollowingRequest("test_followeeId1", "test_followingId"));
        inMemoryFollowingsRepository.addNewFollowing(new FollowingRequest("test_followeeId2", "test_followingId"));
        List<String> followings = inMemoryFollowingsRepository.getFollowing("test_followingId");

        //assert
        Assertions.assertThat(followings).containsExactly("test_followeeId1", "test_followeeId2");
    }
}