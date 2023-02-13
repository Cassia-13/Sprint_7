package order;

import http.OrderApi;
import models.order.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final List<String> color;
    private final OrderApi orderApi = new OrderApi();

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Create an order with color: {0}")
    public static Object[][] getData() {
        return new List[][]{
                {List.of("BLACK", "GREY")},
                {List.of("GREY")},
                {List.of("BLACK")},
                {List.of()},
        };
    }


    @Test
    public void withoutColorTest() {
        Order order = new Order();
        order.setColor(color);
        orderApi.createOrder(order)
                .then()
                .assertThat()
                .statusCode(201);
    }
}
