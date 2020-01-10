package org.openchat;

import org.openchat.api.CreateGetAllUserPresenter;
import org.openchat.api.FormatDateService;
import org.openchat.api.createNewFollowingApi.CreateNewFollowingRequestService;
import org.openchat.api.createNewPostApi.CreateNewPostRequestService;
import org.openchat.api.createNewPostApi.CreateNewPostResponsePresenter;
import org.openchat.api.createNewUserApi.CreateNewUserRequestService;
import org.openchat.api.createNewUserApi.CreateNewUserResponsePresenter;
import org.openchat.api.getAllFollowingForUserApi.GetAllFollowingForUserResponsePresenter;
import org.openchat.api.getTimelineForUserApi.GetTimelineForUserResponsePresenter;
import org.openchat.api.getUserWallApi.GetUserWallResponsePresenter;
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
    static FindUserByIdService findUserByIdService = new FindUserByIdService(userRepository);
    static GetAllFollowingForUserService getAllFollowingForUserService = new GetAllFollowingForUserService(userRepository, followingsRepository);
    static GetTimelineFromUserIdService getTimelineFromUserIdService = new GetTimelineFromUserIdService(postRepository);

    private static FormatDateService formatDateService = new FormatDateService();

    static CreateNewUserService createNewUserService = new CreateNewUserService(userRepository);
    static CreateNewPostService createNewPostService = new CreateNewPostService(postRepository, validateInappropriateWordService);
    static CreateNewFollowingsService createNewFollowingsService = new CreateNewFollowingsService(followingsRepository);
    static FindAllUserService findAllUserService = new FindAllUserService(userRepository);

    static CreateNewUserRequestService createNewUserRequestService = new CreateNewUserRequestService();
    static CreateNewUserResponsePresenter createNewUserResponsePresenter = new CreateNewUserResponsePresenter();
    static CreateNewFollowingRequestService createNewFollowingRequestService = new CreateNewFollowingRequestService();
    static GetAllFollowingForUserResponsePresenter getAllFollowingForUserResponsePresenter = new GetAllFollowingForUserResponsePresenter();
    static CreateNewPostRequestService createNewPostRequestService = new CreateNewPostRequestService();

    static ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService = new ValidaIfUserAlreadyExistService(userRepository);
    static ValidateFollowingExistService validateFollowingExistService = new ValidateFollowingExistService(followingsRepository);

    static GetUserWallService getUserWallService = new GetUserWallService(getTimelineFromUserIdService, getAllFollowingForUserService, findUserByIdService);
    static LoginUserService loginUserService = new LoginUserService(userRepository);

    //Presenters
    static CreateNewPostResponsePresenter createNewPostResponsePresenter = new CreateNewPostResponsePresenter(formatDateService);
    static GetTimelineForUserResponsePresenter getTimelineForUserResponsePresenter = new GetTimelineForUserResponsePresenter(formatDateService);
    static GetUserWallResponsePresenter getUserWallResponsePresenter = new GetUserWallResponsePresenter(formatDateService);
    static CreateGetAllUserPresenter createGetAllUserPresenter = new CreateGetAllUserPresenter(createNewUserResponsePresenter);

    private Context() {
    }
}
