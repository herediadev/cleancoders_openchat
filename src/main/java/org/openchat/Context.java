package org.openchat;

import org.openchat.api.FormatDateService;
import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.repository.InMemoryPostRepository;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.*;

class Context {

    //data sources
    private static InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private static InMemoryPostRepository postRepository = new InMemoryPostRepository();
    private static InMemoryFollowingsRepository followingsRepository = new InMemoryFollowingsRepository();

    //services
    static CreateNewUserService createNewUserService = new CreateNewUserService(userRepository);
    static CreateNewPostService createNewPostService = new CreateNewPostService(postRepository);
    static CreateNewFollowingsService createNewFollowingsService = new CreateNewFollowingsService(followingsRepository);

    static FindAllUserService findAllUserService = new FindAllUserService(userRepository);
    static FindUserByIdService findUserByIdService = new FindUserByIdService(userRepository);

    static ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService = new ValidaIfUserAlreadyExistService(userRepository);
    static ValidateFollowingExistService validateFollowingExistService = new ValidateFollowingExistService(followingsRepository);

    static GetAllFollowingForUserService getAllFollowingForUserService = new GetAllFollowingForUserService(userRepository, followingsRepository);
    static GetTimelineFromUserIdService getTimelineFromUserIdService = new GetTimelineFromUserIdService(postRepository);
    static GetUserWallService getUserWallService = new GetUserWallService(getTimelineFromUserIdService, getAllFollowingForUserService, findUserByIdService);

    static FormatDateService formatDateService = new FormatDateService();
    static LoginUserService loginUserService = new LoginUserService(userRepository);

    private Context() {
    }
}
