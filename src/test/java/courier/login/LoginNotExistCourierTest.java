package courier.login;

import com.github.javafaker.Faker;
import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginNotExistCourierTest {

    private final CourierApi courierApi = new CourierApi();
    private final Faker faker = new Faker();

    private final Courier courier = new Courier(String.valueOf(faker.number().numberBetween(100000, 9999999)), String.valueOf(faker.number().numberBetween(1000, 9999)), faker.name().firstName());

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
