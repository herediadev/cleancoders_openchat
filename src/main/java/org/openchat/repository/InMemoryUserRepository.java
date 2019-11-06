package org.openchat.repository;

import org.openchat.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InMemoryUserRepository {
    private final List<User> userList = new ArrayList<>();

    public void execute(Consumer<List<User>> consumer) {
        consumer.accept(userList);
    }
}
