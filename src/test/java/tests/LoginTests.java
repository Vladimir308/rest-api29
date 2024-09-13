package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;


public class LoginTests extends TestBase {
    @Test
    void successfulRegisterTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        String token = given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .extract().path("token");
        assertThat(token)
                .isNotNull()
                .hasSizeGreaterThan(10)
                .matches(t -> t.chars().allMatch(Character::isLetterOrDigit));

    }

    @Test
    void unSuccessfulRegisterBadRequestTest() {
        String authData = "{\"email\": \"eve.holt\", \"password\": \"pistol\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void unSuccessfulRegisterNotPasswordTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unSuccessfulRegisterNoUserTest() {
        String authData = "{}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }
}