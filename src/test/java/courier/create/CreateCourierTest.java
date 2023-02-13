package courier.create;

import com.github.javafaker.Faker;
import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateCourierTest {
    private final CourierApi courierApi = new CourierApi();
    private final Faker faker = new Faker();

    private final Courier courier = new Courier(String.valueOf(faker.number().numberBetween(100000, 9999999)), String.valueOf(faker.number().numberBetween(1000, 9999)), faker.name().firstName());;

    @Test
    @DisplayName("Check the status code when creating a courier")
    @Description("A positive scenario for creating a courier")
    public void statusCodeTest() {
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @DisplayName("Check the body when creating a courier")
    @Description("A positive scenario for creating a courier")
    public void bodyTest() {
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Can't create a courier duplicate")
    @Description("Can't create two users with same the login and the password. Returns the error 'Этот логин уже используется'")
    public void duplicateCourierTest() {
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(201);
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется")); //Падает, потому что приходит неверное сообшение об ошибке
    }

    @Test
    @DisplayName("Can't create a courier duplicate by login")
    @Description("Can't create two users with same the login. Returns the error 'Этот логин уже используется'")
    public void duplicateLoginTest() {
        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(201);

        String correctPassword = courier.getPassword();
        courier.setPassword(RandomStringUtils.randomNumeric(4));

        courierApi.createCourier(courier)
                .then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется")); //Падает, потому что приходит неверное сообшение об ошибке

        courier.setPassword(correctPassword);
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