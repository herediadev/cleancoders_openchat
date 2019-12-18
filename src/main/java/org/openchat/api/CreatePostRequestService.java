package org.openchat.api;

import com.eclipsesource.json.Json;
import org.openchat.usercases.CreatePostRequest;
import spark.Request;

import java.util.function.Function;

public class CreatePostRequestService implements Function<Request, CreatePostRequest> {

    public CreatePostRequestService() {
    }

    public CreatePostRequest apply(Request request) {
        String userId = request.params("userId");
        String text = getTextFromBody(request.body());
        return new CreatePostRequest(userId, text);
    }

    private String getTextFromBody(String body) {
        return Json.parse(body).asObject().getString("text", "");
    }
}
