package order;

import com.github.javafaker.Faker;
import http.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.order.Order;
import models.order.OrderListResponse;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderByNumberTest {

    private final OrderApi orderApi = new OrderApi();
    private Order order = new Order();
    private final Faker faker = new Faker();

    @Test
    @DisplayName("Get an order by number, returns the object with an order")
    @Description("A positive scenario for get an order by number")
    public void getOrderByNumberTest() {
        order = (orderApi.getOrderList().body().as(OrderListResponse.class).getOrders().get(0));
        int track = order.getTrack();

        order = orderApi.getOrderByNumber(track)
                .body()
                .as(Order.class);

        Assert.assertNotNull(order);
    }

    @Test
    @DisplayName("Can't get an order by number without the track")
    @Description("Can't get an order by number without a tracking parameter. Returns an error 'Недостаточно данных для поиска'")
    public void getOrderByNumberWithoutTrack() {
        orderApi.getOrderByNumber()
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Can't get an order by number with wrong track")
    @Description("Can't get an order by id without correct track number. Returns an error 'Заказ не найден'")
    public void getOrderByNumberWithInvalidTrack() {
        order.setTrack(faker.number().numberBetween(1000000, 10000000));
        orderApi.getOrderByNumber(order.getTrack())
                .then()
                .assertThat()
                .body("message", equalTo("Заказ не найден"));
    }
}
