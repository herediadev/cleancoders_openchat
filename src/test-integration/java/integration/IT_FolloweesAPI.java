package integration;

import com.eclipsesource.json.JsonObject;
import integration.dsl.UserDSL.ITUser;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import static integration.APITestSuit.BASE_URL;
import static integration.dsl.OpenChatTestDSL.*;
import static integration.dsl.UserDSL.ITUserBuilder.aUser;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;

public class IT_FolloweesAPI {

    private static ITUser VIVIANE = aUser().withUsername("Viviane").build();
    private static ITUser SAMUEL = aUser().withUsername("Samuel").build();
    private static ITUser OLIVIA = aUser().withUsername("Olivia").build();

    @BeforeClass
    public static void beforeClass() {
        VIVIANE = register(VIVIANE);
        SAMUEL = register(SAMUEL);
        OLIVIA = register(OLIVIA);
    }

    @Test
    public void
    return_all_followees_for_a_given_user() {
        givenVivianeFollows(SAMUEL, OLIVIA);

        Response response = when().get(BASE_URL + "/v2/followings/" + VIVIANE.id() + "/followees");

        assertAllUsersAreReturned(response, asList(SAMUEL, OLIVIA));
    }

    private static String withFollowingJsonContaining(ITUser follower, ITUser followee) {
        return new JsonObject()
                .add("followerId", follower.id())
                .add("followeeId", followee.id())
                .toString();
    }

    @Test
    public void
    following_already_exists() {
        ValidatableResponse validatableResponse = given()
                .body(withFollowingJsonContaining(VIVIANE, SAMUEL))
                .when()
                .post(BASE_URL + "/v2/followings")
                .then()
                .statusCode(400);

        Assertions.assertThat(validatableResponse.extract().body().asString()).isEqualTo("Following already exist.");
    }

    private void givenVivianeFollows(ITUser... followees) {
        asList(followees).forEach(followee -> createFollowing(VIVIANE, followee));
    }
}
