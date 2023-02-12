package courier.login;

import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginTest {
    private final CourierApi courierApi = new CourierApi();

    private final Courier courier = new Courier(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomNumeric(4), RandomStringUtils.randomAlphabetic(5));

    @Before
    public void createCourier() {
        courierApi.createCourier(courier);
    }

    @Test
    @DisplayName("Check the status code when a courier login")
    @Description("A positive scenario for logging a courier")
    public void statusCodeTest() {
        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check the body when a courier login")
    @Description("A positive scenario for logging a courier")
    public void bodyTest() {
        courier.setId(courierApi.loginCourier(courier)
                .path("id").toString());

        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .body("id", equalTo(Integer.parseInt(courier.getId())));
    }

    @Test
    @DisplayName("Can't login with an invalid password")
    @Description("Can login only with the correct courier's password. Returns an error 'Учетная запись не найдена'")
    public void invalidPasswordTest() {
        String correctPassword = courier.getPassword();
        courier.setPassword(RandomStringUtils.randomNumeric(4));

        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        courier.setPassword(correctPassword);
    }

    @Test
    @DisplayName("Can't login with an invalid login")
    @Description("Can login only with the correct courier's login. Returns an error 'Учетная запись не найдена'")
    public void invalidLoginTest() {
        String correctLogin = courier.getLogin();
        courier.setLogin(RandomStringUtils.randomAlphabetic(5));

        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        courier.setLogin(correctLogin);
    }

    @Test
    @DisplayName("Can't login without a login")
    @Description("Can't login without the courier's login. Returns an error 'Недостаточно данных для входа'")
    public void withoutLoginTest() {
        String correctLogin = courier.getLogin();
        courier.setLogin(null);

        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        courier.setLogin(correctLogin);
    }

    @Test
    @DisplayName("Can't login without a password")
    @Description("Can't login without the courier's password. Returns an error 'Недостаточно данных для входа'")
    public void withoutPasswordTest() {
        String correctPassword = courier.getPassword();
        courier.setPassword(null);
        courier.setFirstName(null);

        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа")); // 504

        courier.setPassword(correctPassword);
    }

    @Test
    @DisplayName("Can't login without a login and a password")
    @Description("Can't login without the courier's login and a password. Returns an error 'Недостаточно данных для входа'")
    public void withoutPasswordAndLoginTest() {
        String correctPassword = courier.getPassword();
        String correctLogin = courier.getLogin();
        courier.setLogin(null);
        courierApi.loginCourier(courier)
                .then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        courier.setPassword(correctPassword);
        courier.setLogin(correctLogin);
    }

    @After
    public void deleteCourier() {
        courier.setId(courierApi.loginCourier(courier).path("id").toString());
        courierApi.removeCourier(courier)
                .then()
                .assertThat()
                .statusCode(200)
                .body("ok", equalTo(true));
    }
}
