package http;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.order.Order;

import static io.restassured.RestAssured.given;

public class OrderApi extends ScooterRestClient {

    @Step("Create an order. POST '/api/v1/orders'")
    public Response createOrder(Order order) {
        return given()
                .spec(baseSpec())
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Get a list of orders. GET '/api/v1/orders'")
    public Response getOrderList() {
        return given()
                .spec(baseSpec())
                .when()
                .get("/api/v1/orders");
    }

    @Step("Take an order. PUT '/api/v1/orders/accept'")
    public Response acceptOrder(String courierId, int id) {
        return given()
                .spec(baseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put("/api/v1/orders/accept/" + id);
    }

    @Step("Take an order without order id. PUT '/api/v1/orders/accept'")
    public Response acceptOrder(String courierId) {
        return given()
                .spec(baseSpec())
                .queryParam("courierId", courierId)
                .when()
                .put("/api/v1/orders/accept/");
    }

    @Step("Get an order by number. GET '/api/v1/orders/track'")
    public Response getOrderByNumber(int track) {
        return given()
                .spec(baseSpec())
                .queryParam("t", track)
                .when()
                .get("/api/v1/orders/track");
    }

    @Step("Get an order by number without track. GET '/api/v1/orders/track'")
    public Response getOrderByNumber() {
        return given()
                .spec(baseSpec())
                .queryParam("t")
                .when()
                .get("/api/v1/orders/track");
    }
}
