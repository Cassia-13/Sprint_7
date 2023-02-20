package courier.create;

import com.github.javafaker.Faker;
import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ErrorCreateCourierTest {
    private final CourierApi courierApi = new CourierApi();
    private final Faker faker = new Faker();

    private final Courier courier = new Courier(String.valueOf(faker.number().numberBetween(100000, 9999999)), String.valueOf(faker.number().numberBetween(1000, 9999)), faker.name().firstName());

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
