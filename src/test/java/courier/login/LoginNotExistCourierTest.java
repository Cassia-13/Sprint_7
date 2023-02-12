package courier.login;

import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginNotExistCourierTest {

    private final CourierApi courierApi = new CourierApi();

    private final Courier courier = new Courier(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomNumeric(4), RandomStringUtils.randomAlphabetic(5));

    @Test
    @DisplayName("Login of a non-existent courier")
    @Description("An unregistered courier can't login. Returns an error 'Учетная запись не найдена'")
    public void loginNonexistentCourierTest() {
        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
