package org.openchat;

import org.openchat.api.*;
import org.openchat.repository.InMemoryFollowingsRepository;
import org.openchat.repository.InMemoryPostRepository;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.*;

class Context {

    //data sources
    private static InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private static InMemoryPostRepository postRepository = new InMemoryPostRepository();
    private static InMemoryFollowingsRepository followingsRepository = new InMemoryFollowingsRepository();

    static FindUserByIdService findUserByIdService = new FindUserByIdService(userRepository);
    private static ValidateInappropriateWordService validateInappropriateWordService = new ValidateInappropriateWordService();
    static GetAllFollowingForUserService getAllFollowingForUserService = new GetAllFollowingForUserService(userRepository, followingsRepository);
    static GetTimelineFromUserIdService getTimelineFromUserIdService = new GetTimelineFromUserIdService(postRepository);

    //services
    private static FormatDateService formatDateService = new FormatDateService();

    static CreateNewUserService createNewUserService = new CreateNewUserService(userRepository);
    static CreateNewPostService createNewPostService = new CreateNewPostService(postRepository, validateInappropriateWordService);
    static CreateNewFollowingsService createNewFollowingsService = new CreateNewFollowingsService(followingsRepository);
    static FindAllUserService findAllUserService = new FindAllUserService(userRepository);

    static CreateNewUserRequestService createNewUserRequestService = new CreateNewUserRequestService();
    static CreateNewUserResponsePresenter createNewUserResponsePresenter = new CreateNewUserResponsePresenter();
    static CreateNewFollowingRequestService createNewFollowingRequestService = new CreateNewFollowingRequestService();
    static CreateFollowingForUserResponsePresenter createFollowingForUserResponsePresenter = new CreateFollowingForUserResponsePresenter();
    static CreatePostRequestService createPostRequestService = new CreatePostRequestService();

    static ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService = new ValidaIfUserAlreadyExistService(userRepository);
    static ValidateFollowingExistService validateFollowingExistService = new ValidateFollowingExistService(followingsRepository);

    static GetUserWallService getUserWallService = new GetUserWallService(getTimelineFromUserIdService, getAllFollowingForUserService, findUserByIdService);
    static LoginUserService loginUserService = new LoginUserService(userRepository);

    static CreateNewPostResponsePresenter createNewPostResponsePresenter = new CreateNewPostResponsePresenter(formatDateService);
    static CreateTimelineForUserResponsePresenter createTimelineForUserResponsePresenter = new CreateTimelineForUserResponsePresenter(formatDateService);
    static CreateUserWallResponsePresenter createUserWallResponsePresenter = new CreateUserWallResponsePresenter(formatDateService);

    private Context() {
    }
}
