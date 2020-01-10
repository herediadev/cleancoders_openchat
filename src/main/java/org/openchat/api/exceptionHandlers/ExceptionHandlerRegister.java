package org.openchat.api.exceptionHandlers;

public class ExceptionHandlerRegister {

    public static void init() {
        new UserAlreadyExistExceptionHandler();
        new InappropriateLanguageExceptionHandler();
        new InvalidCredentialExceptionHandler();
        new FollowingAlreadyExistExceptionHandler();
    }
}
