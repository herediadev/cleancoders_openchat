package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.usercases.CreateNewFollowingsService;
import org.openchat.usercases.FollowingAlreadyExistException;
import org.openchat.usercases.FollowingRequest;
import org.openchat.usercases.ValidateFollowingExistService;
import spark.Request;
import spark.Response;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FollowingsApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Mock
    private CreateNewFollowingsService createNewFollowingsService;

    @Mock
    private ValidateFollowingExistService validateFollowingExistService;

    @Test
    void given_the_request_when_add_new_following_it_will_response_following_created() {
        //arrange
        FollowingsApi followingsApi = new FollowingsApi(createNewFollowingsService, validateFollowingExistService);
        given(request.body()).willReturn(JsonContaining());

        //act
        String result = followingsApi.handle(request, response);

        //assert
        verify(response).status(201);
        verify(createNewFollowingsService).execute(any(FollowingRequest.class));
        Assertions.assertThat(result).isEqualTo("Following created.");
    }

    private String JsonContaining() {
        return new JsonObject()
                .add("followeeId", "test_followeeId")
                .add("followerId", "test_followingId")
                .toString();
    }

    @Test
    void given_the_request_when_adding_new_following_that_already_exist_it_will_response_an_400_error() {
        //arrange
        FollowingsApi followingsApi = new FollowingsApi(createNewFollowingsService, validateFollowingExistService);
        doThrow(FollowingAlreadyExistException.class).when(validateFollowingExistService).execute(any(FollowingRequest.class));
        given(request.body()).willReturn(JsonContaining());

        //act
        String result = followingsApi.handle(request, response);

        //assert
        verify(response).status(400);
        verify(validateFollowingExistService).execute(any(FollowingRequest.class));
        Assertions.assertThat(result).isEqualTo("Following already exist.");
    }
}