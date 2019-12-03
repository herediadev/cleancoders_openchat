package org.openchat.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openchat.entities.Post;
import org.openchat.repository.InMemoryPostRepository;

import java.time.LocalDateTime;
import java.util.Optional;

class InMemoryPostRepositoryTest {

    @Test
    void given_a_post_when_it_saves_the_post_it_will_save_the_post_in_the_repository() {
        //arrange
        String postId = "test_post_id";
        InMemoryPostRepository inMemoryPostRepository = new InMemoryPostRepository();

        //act
        inMemoryPostRepository.save(new Post(postId, "test_user_id", "test text", LocalDateTime.now()));
        Optional<Post> postByIdFound = inMemoryPostRepository.findPostById(postId);

        //assert
        Assertions.assertThat(postByIdFound.isPresent()).isTrue();
    }
}