package org.openchat;

import org.openchat.api.LoginUserApi;
import org.openchat.api.createNewFollowingApi.CreateNewFollowingApi;
import org.openchat.api.createNewPostApi.CreateNewPostApi;
import org.openchat.api.createNewUserApi.CreateNewUserApi;
import org.openchat.usercases.exceptions.FollowingAlreadyExistException;
import org.openchat.usercases.exceptions.InappropriateLanguageException;
import org.openchat.usercases.exceptions.InvalidCredentialException;
import org.openchat.usercases.exceptions.UserAlreadyExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Route;
import spark.Spark;

import static org.eclipse.jetty.http.HttpStatus.NOT_IMPLEMENTED_501;
import static spark.Spark.*;

public class OpenChat {

    private static final Logger logger = LoggerFactory.getLogger(OpenChat.class);

    private static final String API_NOT_IMPLEMENTED = "API not implemented.";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error.";

    private final Routes routes = new Routes();

    public void start() {
        port(4321);
        enableCORS();
        setLog();
        routes.create();
        configureInternalServerError();
        configureNotImplemented();
        registerExceptionHandlers();
    }

    public void stop() {
        Spark.stop();
    }

    public void awaitInitialization() {
        Spark.awaitInitialization();
    }

    private void configureInternalServerError() {
        internalServerError(getDefaultNotImplementedRoute(INTERNAL_SERVER_ERROR));
    }

    private void configureNotImplemented() {
        notFound(getDefaultNotImplementedRoute(API_NOT_IMPLEMENTED));
    }

    private void enableCORS() {
        // Enable Cross Origin Resource Sharing.
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Headers", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, OPTIONS");
        });
    }

    private Route getDefaultNotImplementedRoute(String internalServerError) {
        return (req, res) -> {
            res.status(NOT_IMPLEMENTED_501);
            logger.error(internalServerError + ": " + req.pathInfo());
            return internalServerError;
        };
    }

    private void registerExceptionHandlers() {
        exception(UserAlreadyExistException.class, CreateNewUserApi::registerExceptionHandler);

        exception(InappropriateLanguageException.class, CreateNewPostApi::registerExceptionHandler);

        exception(InvalidCredentialException.class, LoginUserApi::registerExceptionHandler);

        exception(FollowingAlreadyExistException.class, CreateNewFollowingApi::registerExceptionHandler);
    }

    private void setLog() {
        before((request, response) -> logger.info("URL request: " + request.requestMethod() + " " + request.uri() + " - headers: " + request.headers()));
    }

}
