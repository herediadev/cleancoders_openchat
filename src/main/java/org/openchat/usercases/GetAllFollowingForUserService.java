package org.openchat.usercases;

import org.openchat.entities.User;
import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.repository.InMemoryUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetAllFollowingForUserService implements Function<String, List<User>> {

    private final InMemoryUserRepository inMemoryUserRepository;
    private final InMemoryFollowingsRepository inMemoryFollowingsRepository;

    public GetAllFollowingForUserService(InMemoryUserRepository inMemoryUserRepository, InMemoryFollowingsRepository inMemoryFollowingsRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
        this.inMemoryFollowingsRepository = inMemoryFollowingsRepository;
    }

    @Override
    public List<User> apply(String username) {
        return inMemoryUserRepository
                .findUserByUsername(username)
                .map(user -> inMemoryFollowingsRepository.getAll(user.getId())
                        .stream()
                        .map(userId -> inMemoryUserRepository.findUserById(userId).orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

    }
}
