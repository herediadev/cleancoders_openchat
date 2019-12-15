package org.openchat.usercases;

import org.junit.jupiter.api.Test;
import org.openchat.entities.Post;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.repository.InMemoryPostRepository;
import org.openchat.repository.InMemoryUserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetUserWallServiceTest {

    @Test
    void given_user_id_it_will_get_the_user_wall() {
        //arrange
        InMemoryPostRepository inMemoryPostRepository = new InMemoryPostRepository();
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        InMemoryFollowingsRepository inMemoryFollowingsRepository = new InMemoryFollowingsRepository();
        GetTimelineFromUserIdService getTimelineFromUserIdService = new GetTimelineFromUserIdService(inMemoryPostRepository);
        GetAllFollowingForUserService getAllFollowingForUserService = new GetAllFollowingForUserService(inMemoryUserRepository, inMemoryFollowingsRepository);
        FindUserByIdService findUserByIdService = new FindUserByIdService(inMemoryUserRepository);
        GetUserWallService getUserWallService = new GetUserWallService(getTimelineFromUserIdService, getAllFollowingForUserService, findUserByIdService);
        CreateNewUserService createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        CreateNewFollowingsService createNewFollowingsService = new CreateNewFollowingsService(inMemoryFollowingsRepository);
        CreateNewPostService createNewPostService = new CreateNewPostService(inMemoryPostRepository);


        //act
        User userCreated = createNewUserService.execute(new CreateNewUserRequest("test_user_name", "test_password", "test_about"));
        User userCreated2 = createNewUserService.execute(new CreateNewUserRequest("test_user_name2", "test_password2", "test_about2"));
        createNewFollowingsService.accept(new FollowingRequest(userCreated2.getId(), userCreated.getId()));
        Post post1 = createNewPostService.apply(new CreatePostRequest(userCreated.getId(), "text"));
        Post post2 = createNewPostService.apply(new CreatePostRequest(userCreated.getId(), "text"));
        Post post3 = createNewPostService.apply(new CreatePostRequest(userCreated2.getId(), "text"));

        List<Post> userWall = getUserWallService.execute(userCreated.getId());


        //assert
        assertThat(userWall.size()).isGreaterThan(0);
        assertThat(userWall).containsExactly(post3, post2, post1);
    }
}
