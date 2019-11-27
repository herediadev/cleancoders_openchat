package org.openchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openchat.api.*;

import static org.mockito.Mockito.*;

class RoutesTest {

    private Routes spyRoute;

    @BeforeEach
    void setUp() {
        spyRoute = spy(new Routes());
    }

    @Test
    void given_the_route_when_the_method_create_is_called_it_will_create_the_get_route_for_user_api() {
        //act
        spyRoute.create();

        //assert
        verify(spyRoute).createGetRoute(eq("v2/users"), isA(GetAllUserApi.class));
    }

    @Test
    void given_the_route_when_the_method_create_is_called_it_will_create_the_post_route_for_user_api() {
        //act
        spyRoute.create();

        //assert
        verify(spyRoute).createPostRoute(eq("v2/users"), isA(CreateNewUserApi.class));
    }

    @Test
    void given_the_route_when_the_method_create_is_called_it_will_create_the_post_route_for_login_api() {
        //act
        spyRoute.create();

        //assert
        verify(spyRoute).createPostRoute(eq("v2/login"), isA(LoginUserApi.class));
    }

    @Test
    void given_the_route_when_the_method_create_is_called_it_will_create_the_post_route_for_followings_api() {
        //act
        spyRoute.create();

        //assert
        verify(spyRoute).createPostRoute(eq("v2/followings"), isA(FollowingsApi.class));
    }

    @Test
    void given_the_route_when_the_method_create_is_called_it_will_create_the_get_route_for_followings_api() {
        //act
        spyRoute.create();

        //assert
        verify(spyRoute).createGetRoute(eq("v2/followings/:followerId/followees"), isA(GetAllFollowingForUserApi.class));
    }
}