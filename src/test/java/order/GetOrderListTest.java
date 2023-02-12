package order;

import http.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.order.OrderListResponse;
import org.junit.Assert;
import org.junit.Test;

public class GetOrderListTest {
    private final OrderApi orderApi = new OrderApi();

    @Test
    @DisplayName("The list order returns in the body an order list")
    @Description("A positive scenario for get the list order returns in the body an order list")
    public void getListOrderBodyTest() {
        OrderListResponse orderListResponse = orderApi.getOrderList()
                .body().as(OrderListResponse.class);

        Assert.assertNotNull("Order list not exist", orderListResponse.getOrders());
    }
}
