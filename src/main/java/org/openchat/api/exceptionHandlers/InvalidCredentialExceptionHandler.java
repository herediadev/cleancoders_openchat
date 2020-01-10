package org.openchat.api.exceptionHandlers;

import org.openchat.usercases.exceptions.InvalidCredentialException;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

import static spark.Spark.exception;

public class InvalidCredentialExceptionHandler implements ExceptionHandler {

    public InvalidCredentialExceptionHandler() {
        exception(InvalidCredentialException.class, this);
    }

    @Override
    public void handle(Exception exception, Request request, Response response) {
        response.status(404);
        response.body(exception.getMessage());
    }
}
