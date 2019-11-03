package org.openchat;

import org.openchat.api.CreateNewUserApi;

import static spark.Spark.*;

public class Routes {

    public void create() {
        swaggerRoutes();
        openchatRoutes();
    }

    private void openchatRoutes() {
        CreateNewUserApi createNewUserApi = new CreateNewUserApi();
        get("status", (req, res) -> "OpenChat: OK!");
        post("v2/users", createNewUserApi);
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
