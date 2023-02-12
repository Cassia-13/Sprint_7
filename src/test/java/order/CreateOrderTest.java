package order;

import http.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.order.Order;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

public class CreateOrderTest {

    private final OrderApi orderApi = new OrderApi();

    String firstName = RandomStringUtils.randomAlphabetic(5);
    String lastName = RandomStringUtils.randomAlphabetic(5);
    String address = RandomStringUtils.randomAlphabetic(15) + " " + RandomStringUtils.randomNumeric(3);
    String metroStation = RandomStringUtils.randomNumeric(1);
    String phone = "+79" + RandomStringUtils.randomNumeric(9);
    int rentTime = Integer.parseInt(RandomStringUtils.randomNumeric(1));
    String deliveryDate = "2023-02-20"; //TODO: надо подумать
    String comment = RandomStringUtils.randomAlphabetic(25);

    @Test
    @DisplayName("Check the body when create an order")
    @Description("A positive scenario for create an order")
    public void bodyTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        orderApi.createOrder(order)
                .then()
                .assertThat()
                .body("track", Matchers.anything());
    }

    @Test
    @DisplayName("Can create an order without a color")
    @Description("A positive scenario for create an order without a color")
    public void withoutColorTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);
        orderApi.createOrder(order)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @DisplayName("Can create an order with two colors")
    @Description("A positive scenario for create an order when select two colors")
    public void withTwoColorTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, List.of("BLACK", "GREY"));
        orderApi.createOrder(order)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @DisplayName("Can create an order with the black color")
    @Description("A positive scenario for create an order when select the black color")
    public void withBlackColorTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, List.of("BLACK"));
        orderApi.createOrder(order)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @DisplayName("Can create an order with the grey color")
    @Description("A positive scenario for create an order when select the grey color")
    public void withGreyColorTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, List.of("GREY"));
        orderApi.createOrder(order)
                .then()
                .assertThat()
                .statusCode(201);
    }
}
