package org.openchat.api.getAllFollowingForUserApi;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;

import java.util.List;
import java.util.function.Function;

public class GetAllFollowingForUserResponsePresenter implements Function<List<User>, JsonArray> {

    public GetAllFollowingForUserResponsePresenter() {
    }

    @Override
    public JsonArray apply(List<User> users) {
        return users
                .stream()
                .map(this::createUserJson)
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add));
    }

    private JsonObject createUserJson(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout());
    }
}
