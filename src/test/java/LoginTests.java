import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTests extends TestBase {
    @Test
    void successfulRegisterTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";

        given()
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
                .body("token", notNullValue())
                .body("token", is("QpwL5tke4Pnpja7X4"));
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