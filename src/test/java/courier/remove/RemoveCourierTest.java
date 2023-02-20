package courier.remove;

import com.github.javafaker.Faker;
import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class RemoveCourierTest {

    private final CourierApi courierApi = new CourierApi();
    private final Faker faker = new Faker();

    private final Courier courier = new Courier(String.valueOf(faker.number().numberBetween(100000, 9999999)), String.valueOf(faker.number().numberBetween(1000, 9999)), faker.name().firstName());

    @Before
    public void createCourier() {
        courierApi.createCourier(courier);
    }

    @Test
    @DisplayName("Check the status code when delete the courier")
    @Description("A positive scenario for delete the courier")
    public void removeCourierTest() {
        courier.setId(courierApi.loginCourier(courier).path("id").toString());

        courierApi.removeCourier(courier)
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }
}
