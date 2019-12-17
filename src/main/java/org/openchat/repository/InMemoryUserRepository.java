package org.openchat.repository;

import org.openchat.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class InMemoryUserRepository {
    private final List<User> userList = new ArrayList<>();

    public Optional<User> findUserByUsername(String username) {
        return getUserBy(user -> username.equals(user.getUsername()));
    }

    public void save(User user) {
        userList.add(user);
    }

    public List<User> getUserList() {
        return Collections.unmodifiableList(userList);
    }

    public Optional<User> findUserById(String userId) {
        return getUserBy(user -> userId.equals(user.getId()));
    }

    private Optional<User> getUserBy(Predicate<User> userPredicate) {
        return getUserList().stream().filter(userPredicate).findFirst();
    }
}
