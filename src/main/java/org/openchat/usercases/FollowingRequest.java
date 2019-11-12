package org.openchat.usercases;

public class FollowingRequest {
    private final String followeeId;
    private final String followingId;

    public FollowingRequest(String followeeId, String followingId) {
        this.followeeId = followeeId;
        this.followingId = followingId;
    }

    public String getFolloweeId() {
        return followeeId;
    }

    public String getFollowingId() {
        return followingId;
    }
}
