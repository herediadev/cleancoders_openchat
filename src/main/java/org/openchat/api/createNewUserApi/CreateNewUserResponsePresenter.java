package org.openchat.api.createNewUserApi;

import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;

import java.util.function.Function;

public class CreateNewUserResponsePresenter implements Function<User, JsonObject> {

    public CreateNewUserResponsePresenter() {
    }

    public JsonObject apply(User user) {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout()
                );
    }
}
