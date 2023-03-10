package order;

import com.github.javafaker.Faker;
import http.CourierApi;
import http.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import models.order.Order;
import models.order.OrderListResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class AcceptOrderTest {

    private final OrderApi orderApi = new OrderApi();
    private final CourierApi courierApi = new CourierApi();
    private Order order = new Order();
    private final Faker faker = new Faker();

    private Courier courier;

    @Before
    public void prepare() {
        courier = new Courier(String.valueOf(faker.number().numberBetween(100000, 9999999)), String.valueOf(faker.number().numberBetween(1000, 9999)), faker.name().firstName());
        courierApi.createCourier(courier);
        courier.setId(courierApi.loginCourier(courier).path("id").toString());
        order = orderApi.getOrderList().body().as(OrderListResponse.class).getOrders().get(0);
    }


    @Test
    @DisplayName("Can accept an order")
    @Description("A positive scenario for accept an order")
    public void bodyTest() {
        orderApi.acceptOrder(courier.getId(), order.getId())
                .then()
                .assertThat()
                .body("ok", equalTo(true));

        courierApi.removeCourier(courier)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Can't accept an order without a id")
    @Description("Can't accept an order without the required field: 'id'. Returns an error 'Недостаточно данных для поиска'")
    public void withoutIdTest() {
        orderApi.acceptOrder(courier.getId())
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для поиска")); //Падает из-за не правильной ошибки.

        courierApi.removeCourier(courier)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Can't accept an order without a courier's id")
    @Description("Can't accept an order without the required field: 'courierId'. Returns an error 'Недостаточно данных для поиска'")
    public void withoutCourierIdTest() {
        courier.setId(null);

        orderApi.acceptOrder(courier.getId(), order.getId())
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Can't accept an order with invalid a id")
    @Description("Can't accept an order with the invalid field: 'id'. Returns an error 'Заказа с таким id не существует'")
    public void withInvalidIdTest() {
        order.setId(faker.number().numberBetween(1000000, 100000000));

        orderApi.acceptOrder(courier.getId(), order.getId())
                .then()
                .assertThat()
                .body("message", equalTo("Заказа с таким id не существует"));

        courierApi.removeCourier(courier)
                .then()
                .assertThat()
                .statusCode(200);

    }

    @Test
    @DisplayName("Can't accept an order with invalid a courier's id")
    @Description("Can't accept an order with the invalid field: 'courierId'. Returns an error 'Курьера с таким id не существует'")
    public void withInvalidCourierIdTest() {
        courier.setId(String.valueOf(faker.number().numberBetween(1, 9)));

        orderApi.acceptOrder(courier.getId(), order.getId())
                .then()
                .assertThat()
                .body("message", equalTo("Курьера с таким id не существует"));
    }
}
