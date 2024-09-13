package tests;

import models.RegistrationRequestModel;
import models.UnsuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.ReqresSpecs.errorResponseSpec;
import static specs.ReqresSpecs.requestSpec;

@Tag("API")
@DisplayName("/register tests")

public class UnSuccessfulLoginTests extends TestBase {
    @Test
    void unSuccessfulLogin400Test() {
        RegistrationRequestModel regData = new RegistrationRequestModel();
        regData.setEmail("sydney@fife");
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
}