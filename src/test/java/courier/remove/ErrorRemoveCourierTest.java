package courier.remove;

import com.github.javafaker.Faker;
import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ErrorRemoveCourierTest {

    private final CourierApi courierApi = new CourierApi();
    private final Faker faker = new Faker();

    private final Courier courier = new Courier(String.valueOf(faker.number().numberBetween(100000, 9999999)), String.valueOf(faker.number().numberBetween(1000, 9999)), faker.name().firstName());

    @Test
    @DisplayName("Can't remove a courier without a courier's id")
    @Description("Can't remove a courier without the required field: 'id'. Returns an error 'Недостаточно данных для удаления курьера'")
    public void removeWithoutId() {
        courierApi.removeCourier()
                .then()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для удаления курьера")); //Падает, потому что приходит неверное сообшение об ошибке
    }

    @Test
    @DisplayName("Can't remove a courier with invalid courier's id")
    @Description("Can't remove a courier with invalid courier's id. Returns an error 'Недостаточно данных для удаления курьера'")
    public void removeWithNotCorrectId() {
        courier.setId(String.valueOf(faker.number().numberBetween(1, 1000)));
        courierApi.removeCourier(courier)
                .then()
                .assertThat()
                .body("message", equalTo("Курьера с таким id нет")); //Падает, потому что приходит неверное сообшение об ошибке. Лишняя точка, которой нет в требованиях
    }

}
