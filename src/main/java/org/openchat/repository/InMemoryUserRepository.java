package org.openchat.repository;

import org.openchat.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class InMemoryUserRepository {
    private final List<User> userList = new ArrayList<>();

    public void execute(Consumer<List<User>> consumer) {
        consumer.accept(userList);
    }

    public Optional<User> findUserByUsername(String username) {
        return userList.stream().filter(user -> username.equals(user.getUsername())).findFirst();
    }

    public void save(User user) {
        userList.add(user);
    }

    public List<User> getUserList() {
        return Collections.unmodifiableList(userList);
    }
}
