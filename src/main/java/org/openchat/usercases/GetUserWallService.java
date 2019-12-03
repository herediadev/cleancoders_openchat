package org.openchat.usercases;

import org.openchat.entities.Post;
import org.openchat.entities.User;
import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.repository.InMemoryPostRepository;
import org.openchat.repository.InMemoryUserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GetUserWallService {

    private final GetTimelineFromUserIdService getTimelineFromUserIdService;
    private final FindUserByIdService findUserByIdService;
    private GetAllFollowingForUserService getAllFollowingForUserService;

    public GetUserWallService(GetTimelineFromUserIdService getTimelineFromUserIdService, GetAllFollowingForUserService getAllFollowingForUserService, FindUserByIdService findUserByIdService) {
        this.findUserByIdService = findUserByIdService;
        this.getTimelineFromUserIdService = getTimelineFromUserIdService;
        this.getAllFollowingForUserService = getAllFollowingForUserService;

    }

    public List<Post> execute(String userId) {
        User userFound = findUserByIdService.execute(userId);
        List<User> userList = getAllFollowingForUserService.execute(userFound.getUsername());
        userList.add(userFound);

        return userList
                .stream()
                .map(user -> this.getTimelineFromUserIdService.execute(user.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
