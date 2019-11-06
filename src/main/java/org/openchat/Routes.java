package org.openchat;

import org.openchat.api.*;
import org.openchat.repository.InMemoryUserRepository;
import org.openchat.usercases.CreateNewUserService;
import org.openchat.usercases.FindAllUserService;
import org.openchat.usercases.FindUserByUsernameService;
import org.openchat.usercases.LoginUserService;

import static spark.Spark.*;

public class Routes {

    public void create() {
        swaggerRoutes();
        openchatRoutes();
    }

    private void openchatRoutes() {
        InMemoryUserRepository inMemoryUserRepository = new InMemoryUserRepository();
        CreateNewUserService createNewUserService = new CreateNewUserService(inMemoryUserRepository);
        FindUserByUsernameService findUserByUsernameService = new FindUserByUsernameService(inMemoryUserRepository);
        FindAllUserService findAllUserService = new FindAllUserService(inMemoryUserRepository);
        LoginUserService loginUserService = new LoginUserService(findUserByUsernameService);

        CreateNewUserApi createNewUserApi = new CreateNewUserApi(createNewUserService, findUserByUsernameService);
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
