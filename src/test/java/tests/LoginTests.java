package tests;

import models.RegistrationRequestModel;
import models.SuccessfulRegistrationResponseModel;
import models.UnsuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpecs.*;

@Tag("API")
@DisplayName("/register tests")
public class LoginTests extends TestBase {
    @Test
    void successfulRegistrationTest() {
        RegistrationRequestModel regData = new RegistrationRequestModel();
        regData.setEmail("eve.holt@reqres.in");
        regData.setPassword("pistol");
        SuccessfulRegistrationResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(successResponseSpec)
                        .extract().as(SuccessfulRegistrationResponseModel.class));
        step("Check registered user id", () ->
                assertEquals("4", response.getId()));
        step("Check registered user token", () ->
                assertThat(response.getToken())
                        .isNotNull()
                        .hasSizeGreaterThan(10)
                        .isAlphanumeric());
    }

    @Test
    void unSuccessfulRegisterBadRequestTest() {
        RegistrationRequestModel regData = new RegistrationRequestModel();
        regData.setEmail("eve.holt");
        regData.setPassword("pistol");
        UnsuccessfulRegistrationResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(errorResponseSpec)
                        .extract().as(UnsuccessfulRegistrationResponseModel.class));
        step("Check 'missing password' error", () ->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }

    @Test
    void registrationWithNoPasswordTest() {
        RegistrationRequestModel regData = new RegistrationRequestModel();
        regData.setEmail("eve.holt@reqres.in");
        UnsuccessfulRegistrationResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(errorResponseSpec)
                        .extract().as(UnsuccessfulRegistrationResponseModel.class));
        step("Check 'missing password' error", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    void unSuccessfulRegisterNoUserTest() {
        RegistrationRequestModel regData = new RegistrationRequestModel();
        regData.setPassword("pistol");
        UnsuccessfulRegistrationResponseModel response = step("Make request", () ->
                given(requestSpec)
                        .body(regData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(errorResponseSpec)
                        .extract().as(UnsuccessfulRegistrationResponseModel.class));
        step("Check 'missing login' error", () ->
                assertEquals("Missing email or username", response.getError()));
    }
}