package org.openchat.usercases;

public class CreatePostRequest {
    private final String userId;
    private final String text;

    public CreatePostRequest(String userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }
}
