package org.openchat.api.exceptionHandlers;

import org.openchat.usercases.exceptions.InappropriateLanguageException;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

import static spark.Spark.exception;

public class InappropriateLanguageExceptionHandler implements ExceptionHandler {

    public InappropriateLanguageExceptionHandler() {
        exception(InappropriateLanguageException.class, this);
    }

    @Override
    public void handle(Exception exception, Request request, Response response) {
        response.status(400);
        response.body(exception.getMessage());
    }
}
