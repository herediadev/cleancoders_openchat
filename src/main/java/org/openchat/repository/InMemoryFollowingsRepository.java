package org.openchat.repository;

import org.openchat.usercases.FollowingRequest;

import java.util.*;

public class InMemoryFollowingsRepository {

    private final Map<String, List<String>> followings;

    public InMemoryFollowingsRepository() {
        followings = new HashMap<>();
    }

    public Optional<String> checkIfExist(FollowingRequest followingRequest) {
        List<String> followingList = getAll(followingRequest.getFollowingId());

        if (followingList.isEmpty())
            return Optional.empty();

        return followingList
                .stream()
                .filter(followeeId -> followeeId.equals(followingRequest.getFolloweeId()))
                .findFirst();
    }

    public List<String> getAll(String followingId) {
        return Collections.unmodifiableList(followings.getOrDefault(followingId, Collections.emptyList()));
    }

    public void addNewFollowing(FollowingRequest followingRequest) {
        String followingId = followingRequest.getFollowingId();
        List<String> following = new ArrayList<>(getAll(followingId));
        following.add(followingRequest.getFolloweeId());
        followings.put(followingId, following);
    }

}
