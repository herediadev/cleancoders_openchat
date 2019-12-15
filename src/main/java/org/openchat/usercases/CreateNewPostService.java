package org.openchat.usercases;

import org.openchat.entities.Post;
import org.openchat.repository.InMemoryPostRepository;
import org.openchat.usercases.exceptions.InappropriateLanguageException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public class CreateNewPostService implements Function<CreatePostRequest, Post> {

    private final InMemoryPostRepository inMemoryPostRepository;

    public CreateNewPostService(InMemoryPostRepository inMemoryPostRepository) {
        this.inMemoryPostRepository = inMemoryPostRepository;
    }

    public Post apply(CreatePostRequest createPostRequest) {
        validateInappropriateWords(createPostRequest.getText());

        LocalDateTime now = LocalDateTime.now();

        Post post = new Post(UUID.randomUUID().toString(), createPostRequest.getUserId(), createPostRequest.getText(), now);

        inMemoryPostRepository.save(post);

        return post;
    }

    private void validateInappropriateWords(String text) {
        List<String> inappropriateWords = Arrays.asList("orange", "ice cream", "elephant");

        inappropriateWords
                .stream()
                .filter(word -> text.toLowerCase().contains(word))
                .findFirst()
                .ifPresent(this::throwInappropriateLanguageException);
    }

    private void throwInappropriateLanguageException(String word) {
        throw new InappropriateLanguageException();
    }
}
