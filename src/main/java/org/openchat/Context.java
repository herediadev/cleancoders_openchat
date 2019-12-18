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

    //services
    private static ValidateInappropriateWordService validateInappropriateWordService = new ValidateInappropriateWordService();
    static CreateNewUserService createNewUserService = new CreateNewUserService(userRepository);
    static CreateNewPostService createNewPostService = new CreateNewPostService(postRepository, validateInappropriateWordService);
    static CreateNewFollowingsService createNewFollowingsService = new CreateNewFollowingsService(followingsRepository);

    static FindAllUserService findAllUserService = new FindAllUserService(userRepository);
    static FindUserByIdService findUserByIdService = new FindUserByIdService(userRepository);

    static CreateNewUserRequestService createNewUserRequestService = new CreateNewUserRequestService();
    static CreateNewUserResponseService createNewUserResponseService = new CreateNewUserResponseService();
    static CreateNewFollowingRequestService createNewFollowingRequestService = new CreateNewFollowingRequestService();
    static CreateFollowingForUserResponseService createFollowingForUserResponseService = new CreateFollowingForUserResponseService();
    static CreatePostRequestService createPostRequestService = new CreatePostRequestService();


    static ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService = new ValidaIfUserAlreadyExistService(userRepository);
    static ValidateFollowingExistService validateFollowingExistService = new ValidateFollowingExistService(followingsRepository);

    static GetAllFollowingForUserService getAllFollowingForUserService = new GetAllFollowingForUserService(userRepository, followingsRepository);
    static GetTimelineFromUserIdService getTimelineFromUserIdService = new GetTimelineFromUserIdService(postRepository);
    static GetUserWallService getUserWallService = new GetUserWallService(getTimelineFromUserIdService, getAllFollowingForUserService, findUserByIdService);

    static FormatDateService formatDateService = new FormatDateService();
    static LoginUserService loginUserService = new LoginUserService(userRepository);

    static CreateNewPostResponseService createNewPostResponseService = new CreateNewPostResponseService(formatDateService);
    static CreateTimelineForUserResponseService createTimelineForUserResponseService = new CreateTimelineForUserResponseService(formatDateService);
    static CreateUserWallResponseService createUserWallResponseService = new CreateUserWallResponseService(formatDateService);

    private Context() {
    }
}
