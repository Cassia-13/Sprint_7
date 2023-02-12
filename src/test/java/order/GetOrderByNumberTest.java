package order;

import http.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.order.Order;
import models.order.OrderListResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class GetOrderByNumberTest {

    private final OrderApi orderApi = new OrderApi();
    private Order order = new Order();

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
        order.setTrack(Integer.parseInt(RandomStringUtils.randomNumeric(7)));
        orderApi.getOrderByNumber(order.getTrack())
                .then()
                .assertThat()
                .body("message", equalTo("Заказ не найден"));
    }
}
