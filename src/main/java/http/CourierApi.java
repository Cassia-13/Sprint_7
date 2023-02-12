package http;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Courier;

import static io.restassured.RestAssured.given;

public class CourierApi extends ScooterRestClient {

    @Step("Create a courier. POST '/api/v1/courier'")
    public Response createCourier(Courier courier) {
        return given()
                .spec(baseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Log in to a courier system or get a courier id. POST '/api/v1/courier/login'")
    public Response loginCourier(Courier courier) {
        return given()
                .spec(baseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Remove a courier. DELETE '/api/v1/courier'")
    public Response removeCourier(Courier courier) {
        return given()
                .spec(baseSpec())
                .when()
                .delete("/api/v1/courier/" + courier.getId());
    }

    @Step("Remove a courier without a courier id. DELETE '/api/v1/courier'")
    public Response removeCourier() {
        return given()
                .spec(baseSpec())
                .when()
                .delete("/api/v1/courier/");
    }
}
