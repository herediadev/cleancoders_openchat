package org.openchat.usercases;

import integration.APITestSuit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.api.FormatDateService;
import org.openchat.entities.Post;
import org.openchat.repository.InMemoryPostRepository;
import org.openchat.usercases.exceptions.InappropriateLanguageException;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateNewPostServiceTest {

    @Mock
    private InMemoryPostRepository inMemoryPostRepository;

    private CreateNewPostService createNewPostService;

    @BeforeEach
    void setUp() {
        createNewPostService = new CreateNewPostService(inMemoryPostRepository);
    }

    @Test
    void given_a_new_post_request_it_will_create_a_post() {
        //arrange
        FormatDateService formatDateService = new FormatDateService();
        CreatePostRequest createPostRequest = new CreatePostRequest(UUID.randomUUID().toString(), "test text");
        doNothing().when(inMemoryPostRepository).save(any(Post.class));

        //act
        Post newPostCreated = createNewPostService.apply(createPostRequest);

        //assert
        verify(inMemoryPostRepository).save(any(Post.class));
        Assertions.assertThat(newPostCreated.getPostId()).matches(APITestSuit.UUID_PATTERN);
        Assertions.assertThat(newPostCreated.getUserId()).matches(APITestSuit.UUID_PATTERN);
        Assertions.assertThat(newPostCreated.getText()).isEqualTo("test text");
        Assertions.assertThat(formatDateService.execute(newPostCreated.getDateTime())).matches(APITestSuit.DATE_PATTERN);
    }

    @Test
    void given_a_post_request_when_it_has_an_inappropriate_word_it_will_thrown_an_inappropriate_exception() {
        //arrange
        CreatePostRequest createPostRequest = new CreatePostRequest("test_user_id", "inappropriate word ice cream");

        //act and assert
        org.junit.jupiter.api.Assertions.assertThrows(InappropriateLanguageException.class, () -> createNewPostService.apply(createPostRequest));
    }
}