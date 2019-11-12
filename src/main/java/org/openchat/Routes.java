package org.openchat;

import org.openchat.api.CreateNewUserApi;
import org.openchat.api.FollowingsApi;
import org.openchat.api.GetAllUserApi;
import org.openchat.api.LoginUserApi;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.*;
import spark.Route;

import static spark.Spark.*;

class Routes {

    void create() {
        swaggerRoutes();
        openChatRoutes();
    }

    private void openChatRoutes() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        InMemoryFollowingsRepository inMemoryFollowingsRepository = new InMemoryFollowingsRepository();

        CreateNewUserService createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService = new ValidaIfUserAlreadyExistService(inMemoryUserRepository);
        FindAllUserService findAllUserService = new FindAllUserService(inMemoryUserRepository);
        LoginUserService loginUserService = new LoginUserService(inMemoryUserRepository);
        ValidateFollowingExistService validateFollowingExistService = new ValidateFollowingExistService(inMemoryFollowingsRepository);

        CreateNewUserApi createNewUserApi = new CreateNewUserApi(createNewUserService, validaIfUserAlreadyExistService);
        LoginUserApi loginUserApi = new LoginUserApi(loginUserService);
        GetAllUserApi getAllUserApi = new GetAllUserApi(findAllUserService);
        FollowingsApi followingsApi = new FollowingsApi(new CreateNewFollowingsService(inMemoryFollowingsRepository), validateFollowingExistService);

        createGetRoute("status", (req, res) -> "OpenChat: OK!");
        createPostRoute("v2/users", createNewUserApi);
        createGetRoute("v2/users", getAllUserApi);
        createPostRoute("v2/login", loginUserApi);
        createPostRoute("v2/followings", followingsApi);
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
