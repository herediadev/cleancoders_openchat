package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;

import java.util.List;
import java.util.function.Function;

public class CreateGetAllUserPresenter implements Function<List<User>, JsonArray> {

    private final Function<User, JsonObject> createNewUserResponsePresenter;

    public CreateGetAllUserPresenter(Function<User, JsonObject> createNewUserResponsePresenter) {
        this.createNewUserResponsePresenter = createNewUserResponsePresenter;
    }

    @Override
    public JsonArray apply(List<User> users) {
        return users
                .stream()
                .map(createNewUserResponsePresenter)
                .collect(JsonArray::new, JsonArray::add, (jsonValues, jsonValues2) -> jsonValues2.forEach(jsonValues::add));
    }
}
