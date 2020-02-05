package org.openchat;

import org.openchat.api.GetAllUserApi;
import org.openchat.api.LoginUserApi;
import org.openchat.api.createNewFollowingApi.CreateNewFollowingApi;
import org.openchat.api.createNewPostApi.CreateNewPostApi;
import org.openchat.api.createNewUserApi.CreateNewUserApi;
import org.openchat.api.getAllFollowingForUserApi.GetAllFollowingForUserApi;
import org.openchat.api.getTimelineForUserApi.GetTimelineForUserApi;
import org.openchat.api.getUserWallApi.GetUserWallApi;
import spark.Route;
import static spark.Spark.*;

class Routes {

    void create() {
        swaggerRoutes();
        openChatRoutes();
    }

    private void openChatRoutes() {
        Route createNewUserApi = new CreateNewUserApi(Context.createNewUserService, Context.validaIfUserAlreadyExistService, Context.createNewUserRequestService, Context.createNewUserResponsePresenter);
        Route loginUserApi = new LoginUserApi(Context.loginUserService, Context.createNewUserResponsePresenter);
        Route getAllUserApi = new GetAllUserApi(Context.findAllUserService, Context.createGetAllUserPresenter);
        Route createNewFollowingApi = new CreateNewFollowingApi(Context.createNewFollowingsService, Context.validateFollowingExistService, Context.createNewFollowingRequestService);
        Route getAllFollowingForUserApi = new GetAllFollowingForUserApi(Context.getAllFollowingForUserService, Context.findUserByIdService, Context.getAllFollowingForUserResponsePresenter);
        Route createNewPostApi = new CreateNewPostApi(Context.createNewPostService, Context.createNewPostRequestService, Context.createNewPostResponsePresenter);
        Route getTimelineFromUserApi = new GetTimelineForUserApi(Context.getTimelineFromUserIdService, Context.getTimelineForUserResponsePresenter);
        Route getUserWallApi = new GetUserWallApi(Context.getUserWallService, Context.getUserWallResponsePresenter);

        createGetRoute("status", (req, res) -> "OpenChat: OK!");
        createPostRoute("v2/users", createNewUserApi);
        createGetRoute("v2/users", getAllUserApi);
        createPostRoute("v2/login", loginUserApi);
        createPostRoute("v2/followings", createNewFollowingApi);
        createGetRoute("v2/followings/:followerId/followees", getAllFollowingForUserApi);
        createPostRoute("v2/users/:userId/timeline", createNewPostApi);
        createGetRoute("v2/users/:userId/timeline", getTimelineFromUserApi);
        createGetRoute("v2/users/:userId/wall", getUserWallApi);
    }

    void createPostRoute(String path, Route route) {
        post(path, route);
    }

    void createGetRoute(String path, Route route) {
        get(path, route);
    }

    private void swaggerRoutes() {
        options("v2/users", (req, res) -> "OK");
        options("v2/login", (req, res) -> "OK");
        options("v2/users/:userId/timeline", (req, res) -> "OK");
        options("v2/followings", (req, res) -> "OK");
        options("v2/followings/:userId/followees", (req, res) -> "OK");
        options("v2/users/:userId/wall", (req, res) -> "OK");
    }
}
