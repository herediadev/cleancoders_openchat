package org.openchat.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openchat.usercases.FollowingRequest;

import java.util.List;
import java.util.Optional;

class InMemoryFollowingsRepositoryTest {

    private InMemoryFollowingsRepository inMemoryFollowingsRepository;

    @BeforeEach
    void setUp() {
        inMemoryFollowingsRepository = new InMemoryFollowingsRepository();
    }

    @Test
    void given_the_following_request_it_will_add_the_new_relationship() {
        //arrange
        FollowingRequest followingRequest = new FollowingRequest("test_followeeId", "test_followingId");

        //act
        inMemoryFollowingsRepository.addNewFollowing(followingRequest);
        Optional<String> ifExistOptional = inMemoryFollowingsRepository.checkIfExist(followingRequest);

        //assert
        Assertions.assertThat(ifExistOptional.isPresent()).isTrue();
    }

    @Test
    void given_the_following_request_with_a_relationship_that_does_not_exist_it_will_not_add_the_new_relationship() {
        //arrange
        FollowingRequest followingRequest = new FollowingRequest("test_followeeId", "test_followingId");

        //act
        Optional<String> ifExistOptional = inMemoryFollowingsRepository.checkIfExist(followingRequest);

        //assert
        Assertions.assertThat(ifExistOptional.isPresent()).isFalse();
    }

    @Test
    void given_the_following_request_when_adding_two_it_will_get_the_following_list_for_the_following_user_id() {
        //arrange
        FollowingRequest followingRequest = new FollowingRequest("test_followeeId1", "test_followingId");
        FollowingRequest followingRequest1 = new FollowingRequest("test_followeeId2", "test_followingId");

        //act
        inMemoryFollowingsRepository.addNewFollowing(followingRequest);
        inMemoryFollowingsRepository.addNewFollowing(followingRequest1);
        List<String> followings = inMemoryFollowingsRepository.getAll("test_followingId");

        //assert
        Assertions.assertThat(followings).containsExactly("test_followeeId1", "test_followeeId2");
    }
}