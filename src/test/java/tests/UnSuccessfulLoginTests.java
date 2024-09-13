package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UnSuccessfulLoginTests extends TestBase {
    @Test
    void unSuccessfulLogin400Test() {
        String authData = "{\"email\": \"sydney@fife\"}";

        given()
                .body(authData)
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