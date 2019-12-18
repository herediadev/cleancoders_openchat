package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;

import java.util.List;
import java.util.function.Function;

public class CreateFollowingForUserResponseService implements Function<List<User>, String> {

    public CreateFollowingForUserResponseService() {
    }

    public String apply(List<User> users) {
        return users
                .stream()
                .map(this::createUserJson)
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add))
                .toString();
    }

    private JsonObject createUserJson(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout());
    }
}
