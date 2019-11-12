package org.openchat.usercases;

import java.util.*;

public class InMemoryFollowingsRepository {

    private final Map<String, List<String>> followings;

    public InMemoryFollowingsRepository() {
        followings = new HashMap<>();
    }

    public Optional<String> checkIfExist(FollowingRequest followingRequest) {
        List<String> followingList = getFollowing(followingRequest.getFollowingId());

        if (followingList.isEmpty())
            return Optional.empty();

        return followingList
                .stream()
                .filter(followeeId -> followeeId.equals(followingRequest.getFolloweeId()))
                .findFirst();
    }

    public List<String> getFollowing(String followingId) {
        return followings.getOrDefault(followingId, new ArrayList<>());
    }

    public void addNewFollowing(FollowingRequest followingRequest) {
        if (checkIfExist(followingRequest).isEmpty())
            createNewFollowingRelationship(followingRequest);
    }

    private void createNewFollowingRelationship(FollowingRequest followingRequest) {
        String followingId = followingRequest.getFollowingId();
        List<String> following = getFollowing(followingId);
        following.add(followingRequest.getFolloweeId());
        followings.put(followingId, following);
    }
}
