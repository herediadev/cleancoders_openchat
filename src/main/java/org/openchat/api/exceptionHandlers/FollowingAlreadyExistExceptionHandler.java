package org.openchat.api.exceptionHandlers;

import org.openchat.usercases.exceptions.FollowingAlreadyExistException;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

import static org.eclipse.jetty.http.HttpStatus.BAD_REQUEST_400;
import static spark.Spark.exception;

public class FollowingAlreadyExistExceptionHandler implements ExceptionHandler {

    public FollowingAlreadyExistExceptionHandler() {
        exception(FollowingAlreadyExistException.class, this);
    }

    @Override
    public void handle(Exception exception, Request request, Response response) {
        response.status(BAD_REQUEST_400);
        response.body(exception.getMessage());
    }
}
