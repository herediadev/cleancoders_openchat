package org.openchat.api;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.UUID;

public class CreateNewUserApi implements Route {

    public String handle(Request request, Response response) {
        JsonObject requestBody = Json.parse(request.body()).asObject();
        String username = requestBody.getString("username", "");
        String password = requestBody.getString("password", "");
        String about = requestBody.getString("about", "");

        response.status(201);
        response.type("application/json");

        return new JsonObject()
                .add("about", about)
                .add("id", UUID.randomUUID().toString())
                .add("username", username).toString();
    }
}
