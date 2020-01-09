package org.openchat.usercases;

import org.openchat.entities.Post;
import org.openchat.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GetUserWallService implements Function<String, List<Post>> {

    private final GetTimelineFromUserIdService getTimelineFromUserIdService;
    private final FindUserByIdService findUserByIdService;
    private GetAllFollowingForUserService getAllFollowingForUserService;

    public GetUserWallService(GetTimelineFromUserIdService getTimelineFromUserIdService, GetAllFollowingForUserService getAllFollowingForUserService, FindUserByIdService findUserByIdService) {
        this.findUserByIdService = findUserByIdService;
        this.getTimelineFromUserIdService = getTimelineFromUserIdService;
        this.getAllFollowingForUserService = getAllFollowingForUserService;
    }

    @Override
    public List<Post> apply(String userId) {
        List<User> userList = findUserByIdService
                .andThen(this::getAllFollowingForUser)
                .apply(userId);

        return userList
                .stream()
                .map(user -> this.getTimelineFromUserIdService.apply(user.getId()))
                .flatMap(Collection::stream)
                .sorted((post1, post2) -> post2.getDateTime().compareTo(post1.getDateTime()))
                .collect(Collectors.toList());
    }

    private List<User> getAllFollowingForUser(User user) {
        return getAllFollowingForUserService
                .andThen(users -> addUserToFollowingList(user, users))
                .apply(user.getUsername());
    }

    private List<User> addUserToFollowingList(User user, List<User> users) {
        users.add(user);
        return users;
    }
}
