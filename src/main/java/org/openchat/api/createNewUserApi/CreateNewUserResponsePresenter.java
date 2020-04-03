package org.openchat.api.createNewUserApi;

import org.openchat.entities.User;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class CreateNewUserResponsePresenter implements Function<User, Map<String, String>> {

    public CreateNewUserResponsePresenter() {
    }

    @Override
    public Map<String, String> apply(User user) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("about", user.getAbout());
        return map;
    }
}
