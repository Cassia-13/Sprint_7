package courier.remove;

import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class RemoveCourierTest {

    private final CourierApi courierApi = new CourierApi();

    private final Courier courier = new Courier(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomNumeric(4), RandomStringUtils.randomAlphabetic(5));

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
