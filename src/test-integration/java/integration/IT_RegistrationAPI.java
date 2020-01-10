package integration;

import com.eclipsesource.json.JsonObject;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static integration.APITestSuit.BASE_URL;
import static integration.APITestSuit.UUID_PATTERN;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class IT_RegistrationAPI {

    @Test
    public void
    register_a_new_user() {
        given()
                .body(withJsonContaining("Lucy", "alki324d", "About Lucy"))
                .when()
                .post(BASE_URL + "/v2/users")
                .then()
                .statusCode(201)
                .contentType(JSON)
                .body("id", matchesPattern(UUID_PATTERN))
                .body("username", is("Lucy"))
                .body("about", is("About Lucy"));
    }

    @Test
    public void
    return_400_error_user_already_exist() {
        ValidatableResponse validatableResponse = given()
                .body(withJsonContaining("Lucy", "alki324d", "About Lucy"))
                .when()
                .post(BASE_URL + "/v2/users")
                .then()
                .statusCode(400);

        Assertions.assertThat(validatableResponse.extract().body().asString()).isEqualTo("user Lucy already exist");
    }

    private String withJsonContaining(String username, String password, String about) {
        return new JsonObject()
                .add("username", username)
                .add("password", password)
                .add("about", about)
                .toString();
    }
}
