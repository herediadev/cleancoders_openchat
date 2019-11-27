package org.openchat.usercases;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.repository.InMemoryUserRepository;

import java.util.List;

class GetAllFollowingForUserServiceTest {

    @Test
    void given_the_user_name_it_will_return_all_followings() {
        //arrange
        User user = new User("test_user", "test_user_name", "test_user_password", "test_user_about");
        User following1 = new User("test_id_1", "test_user_name_1", "test_password_1", "test_about_1");
        User following2 = new User("test_id_2", "test_user_name_2", "test_password_2", "test_about_2");
        User notFollowing = new User("test_id_3", "test_user_name_3", "test_password_3", "test_about_3");
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        InMemoryFollowingsRepository inMemoryFollowingsRepository = new InMemoryFollowingsRepository();
        GetAllFollowingForUserService getAllFollowingForUserService = new GetAllFollowingForUserService(inMemoryUserRepository, inMemoryFollowingsRepository);

        //act
        inMemoryUserRepository.save(user);
        inMemoryUserRepository.save(following1);
        inMemoryUserRepository.save(following2);
        inMemoryUserRepository.save(notFollowing);
        inMemoryFollowingsRepository.addNewFollowing(new FollowingRequest(following1.getId(), user.getId()));
        inMemoryFollowingsRepository.addNewFollowing(new FollowingRequest(following2.getId(), user.getId()));
        List<User> followings = getAllFollowingForUserService.execute(user.getUsername());

        //assert
        Assertions.assertThat(followings).containsExactly(following1, following2);
    }
}