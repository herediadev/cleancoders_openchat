package org.openchat;

import org.openchat.api.*;
import org.openchat.repository.InMemoryFollowingsRepository;
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
        CreateNewFollowingsService createNewFollowingsService = new CreateNewFollowingsService(inMemoryFollowingsRepository);
        GetAllFollowingForUserService getAllFollowingForUserService = new GetAllFollowingForUserService(inMemoryUserRepository, inMemoryFollowingsRepository);
        FindUserByIdService findUserByIdService = new FindUserByIdService(inMemoryUserRepository);

        CreateNewUserApi createNewUserApi = new CreateNewUserApi(createNewUserService, validaIfUserAlreadyExistService);
        LoginUserApi loginUserApi = new LoginUserApi(loginUserService);
        GetAllUserApi getAllUserApi = new GetAllUserApi(findAllUserService);
        CreateNewFollowingApi createNewFollowingApi = new CreateNewFollowingApi(createNewFollowingsService, validateFollowingExistService);
        GetAllFollowingForUserApi route = new GetAllFollowingForUserApi(getAllFollowingForUserService, findUserByIdService);

        createGetRoute("status", (req, res) -> "OpenChat: OK!");
        createPostRoute("v2/users", createNewUserApi);
        createGetRoute("v2/users", getAllUserApi);
        createPostRoute("v2/login", loginUserApi);
        createPostRoute("v2/followings", createNewFollowingApi);
        createGetRoute("v2/followings/:followerId/followees", route);
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
