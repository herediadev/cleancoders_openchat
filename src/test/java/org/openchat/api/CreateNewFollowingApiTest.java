package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openchat.api.createNewFollowingApi.CreateNewFollowingApi;
import org.openchat.api.createNewFollowingApi.CreateNewFollowingRequestService;
import org.openchat.usercases.FollowingRequest;
import org.openchat.usercases.exceptions.FollowingAlreadyExistException;
import spark.Request;
import spark.Response;

import java.util.function.Consumer;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CreateNewFollowingApiTest {

    @Mock
    private Request request;

    @Mock
    private Response response;

    @Spy
    private Consumer<FollowingRequest> createNewFollowingsService;

    @Spy
    private Consumer<FollowingRequest> validateFollowingExistService;

    @Test
    void given_the_request_when_add_new_following_it_will_response_following_created() {
        //arrange
        CreateNewFollowingApi createNewFollowingApi = new CreateNewFollowingApi(createNewFollowingsService, validateFollowingExistService, new CreateNewFollowingRequestService());
        doReturn(JsonContaining()).when(request).body();

        //act
        String result = createNewFollowingApi.handle(request, response);

        //assert
        verify(response).status(201);
        verify(createNewFollowingsService).accept(any(FollowingRequest.class));
        verify(validateFollowingExistService).accept(any(FollowingRequest.class));
        Assertions.assertThat(result).isEqualTo("Following created.");
    }

    @Test
    void given_the_request_when_adding_new_following_that_already_exist_it_will_response_a_400_error() {
        //arrange
        CreateNewFollowingApi createNewFollowingApi = new CreateNewFollowingApi(createNewFollowingsService, validateFollowingExistService, new CreateNewFollowingRequestService());
        doThrow(FollowingAlreadyExistException.class).when(validateFollowingExistService).accept(any(FollowingRequest.class));
        doReturn(JsonContaining()).when(request).body();

        //act and assert
        org.junit.jupiter.api.Assertions.assertThrows(FollowingAlreadyExistException.class, () -> createNewFollowingApi.handle(request, response));
    }

    @Test
    void given_the_exception_when_it_is_thrown_it_will_get_the_exception_response() {
        //arrange
        FollowingAlreadyExistException followingAlreadyExistException = new FollowingAlreadyExistException();

        //act
        CreateNewFollowingApi.registerExceptionHandler(followingAlreadyExistException, request, response);

        //arrange
        verify(response).status(400);
        verify(response).body("Following already exist.");
    }

    private String JsonContaining() {
        return new JsonObject()
                .add("followeeId", "test_followeeId")
                .add("followerId", "test_followingId")
                .toString();
    }
}