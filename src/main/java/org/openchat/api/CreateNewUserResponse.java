package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.openchat.entities.User;

class CreateNewUserResponse {
    private final User user;

    CreateNewUserResponse(User user) {
        this.user = user;
    }

    JsonObject invoke() {
        return new JsonObject()
                .add("id", user.getId())
                .add("username", user.getUsername())
                .add("about", user.getAbout()
                );
    }
}
