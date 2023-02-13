package order;

import http.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.order.Order;
import org.hamcrest.Matchers;
import org.junit.Test;

public class CreateOrderBodyTest {
    private final OrderApi orderApi = new OrderApi();

    @Test
    @DisplayName("Check the body when create an order")
    @Description("A positive scenario for create an order")
    public void bodyTest() {
        Order order = new Order();
        orderApi.createOrder(order)
                .then()
                .assertThat()
                .body("track", Matchers.anything());
    }
}
