package org.openchat.usercases;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openchat.entities.Post;
import org.openchat.repository.InMemoryPostRepository;

import java.util.List;

public class GetTimelineFromUserIdServiceTest {

    @Test
    void given_the_user_id_it_will_return_all_post_created_by_the_user_in_reverse_order() {
        //arrange
        String userId = "test_user_id";
        InMemoryPostRepository inMemoryPostRepository = new InMemoryPostRepository();
        CreateNewPostService createNewPostService = new CreateNewPostService(inMemoryPostRepository);
        GetTimelineFromUserIdService getTimelineFromUserIdService = new GetTimelineFromUserIdService(inMemoryPostRepository);

        //act
        Post post1 = createNewPostService.apply(new CreatePostRequest(userId, "test_post_1"));
        Post post2 = createNewPostService.apply(new CreatePostRequest(userId, "test_post_2"));
        List<Post> allPostFromUser = getTimelineFromUserIdService.execute(userId);

        //assert
        Assertions.assertThat(allPostFromUser).containsExactly(post2, post1);
    }
}
