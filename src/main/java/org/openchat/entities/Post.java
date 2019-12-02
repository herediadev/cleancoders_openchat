package org.openchat.entities;

public class Post {
    private final String postId;
    private final String userId;
    private final String text;
    private final String dateTime;

    public Post(String postId, String userId, String text, String dateTime) {
        this.postId = postId;
        this.userId = userId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public String getDateTime() {
        return dateTime;
    }
}
