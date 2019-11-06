package org.openchat;

import org.openchat.api.CreateNewUserApi;
import org.openchat.api.GetAllUserApi;
import org.openchat.api.LoginUserApi;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.CreateNewUserService;
import org.openchat.usercases.FindAllUserService;
import org.openchat.usercases.LoginUserService;
import org.openchat.usercases.ValidaIfUserAlreadyExistService;

import static spark.Spark.*;

class Routes {

    void create() {
        swaggerRoutes();
        openChatRoutes();
    }

    private void openChatRoutes() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        CreateNewUserService createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        ValidaIfUserAlreadyExistService validaIfUserAlreadyExistService = new ValidaIfUserAlreadyExistService(inMemoryUserRepository);
        FindAllUserService findAllUserService = new FindAllUserService(inMemoryUserRepository);
        LoginUserService loginUserService = new LoginUserService(inMemoryUserRepository);

        CreateNewUserApi createNewUserApi = new CreateNewUserApi(createNewUserService, validaIfUserAlreadyExistService);
        LoginUserApi loginUserApi = new LoginUserApi(loginUserService);
        GetAllUserApi getAllUserApi = new GetAllUserApi(findAllUserService);


        get("status", (req, res) -> "OpenChat: OK!");
        post("v2/users", createNewUserApi);
        post("v2/login", loginUserApi);
        get("v2/users", getAllUserApi);
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
