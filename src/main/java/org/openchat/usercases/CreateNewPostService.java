package org.openchat.usercases;

import org.openchat.entities.Post;
import org.openchat.repository.InMemoryPostRepository;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Function;

public class CreateNewPostService implements Function<CreatePostRequest, Post> {

    private final InMemoryPostRepository inMemoryPostRepository;
    private final ValidateInappropriateWordService validateInappropriateWordService;

    public CreateNewPostService(InMemoryPostRepository inMemoryPostRepository, ValidateInappropriateWordService validateInappropriateWordService) {
        this.inMemoryPostRepository = inMemoryPostRepository;
        this.validateInappropriateWordService = validateInappropriateWordService;
    }

    public Post apply(CreatePostRequest createPostRequest) {
        return Function.<Post>identity()
                .compose(this::createPost)
                .compose(this::validateInappropriateWords)
                .andThen(this::savePostToRepository)
                .apply(createPostRequest);

    }

    private CreatePostRequest validateInappropriateWords(CreatePostRequest createPostRequestParam) {
        validateInappropriateWordService.validate(createPostRequestParam.getText());
        return createPostRequestParam;
    }

    private Post createPost(CreatePostRequest createPostRequest) {
        return new Post(UUID.randomUUID().toString(), createPostRequest.getUserId(), createPostRequest.getText(), LocalDateTime.now());
    }

    private Post savePostToRepository(Post post) {
        inMemoryPostRepository.save(post);
        return post;
    }
}
