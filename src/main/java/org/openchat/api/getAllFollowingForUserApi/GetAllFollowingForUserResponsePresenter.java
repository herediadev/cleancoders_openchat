package org.openchat.api.getAllFollowingForUserApi;

import org.openchat.entities.User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetAllFollowingForUserResponsePresenter implements Function<List<User>, List<Map<String, String>>> {

    public GetAllFollowingForUserResponsePresenter() {
    }

    @Override
    public List<Map<String, String>> apply(List<User> users) {
        return users
                .stream()
                .map(this::createUserJson)
                .collect(Collectors.toList());
    }

    private Map<String, String> createUserJson(User user) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("about", user.getAbout());
        return map;
    }
}
