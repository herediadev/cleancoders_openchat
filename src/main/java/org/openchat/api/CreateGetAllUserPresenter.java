package org.openchat.api;

import org.openchat.entities.User;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreateGetAllUserPresenter implements Function<List<User>, List<Map<String, String>>> {

    private final Function<User, Map<String, String>> createNewUserResponsePresenter;

    public CreateGetAllUserPresenter(Function<User, Map<String, String>> createNewUserResponsePresenter) {
        this.createNewUserResponsePresenter = createNewUserResponsePresenter;
    }

    @Override
    public List<Map<String, String>> apply(List<User> users) {
        return users
                .stream()
                .map(createNewUserResponsePresenter)
                .collect(Collectors.toList());
    }
}
