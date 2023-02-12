package courier.create;

import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ErrorCreateCourierTest {
    private final CourierApi courierApi = new CourierApi();

    private final Courier courier = new Courier(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomNumeric(4), RandomStringUtils.randomAlphabetic(5));

    @Test
    @DisplayName("Can't create a courier without a login")
    @Description("Can't create a courier without the required field: 'login'. Returns an error 'Недостаточно данных для создания учетной записи'")
    public void withoutLoginTest() {
        courier.setLogin(null);
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Can't create a courier without a password")
    @Description("Can't create a courier without the required field: 'password'. Returns an error 'Недостаточно данных для создания учетной записи'")
    public void withoutPasswordTest() {
        courier.setPassword(null);
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Can't create a courier without the required fields")
    @Description("Can't create a courier without the required field: 'password' and 'login'. Returns an error 'Недостаточно данных для создания учетной записи'")
    public void withoutPasswordAndLoginTest() {
        courier.setPassword(null);
        courier.setLogin(null);
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
