package courier.remove;

import http.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ErrorRemoveCourierTest {

    private final CourierApi courierApi = new CourierApi();

    private final Courier courier = new Courier(RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomNumeric(4), RandomStringUtils.randomAlphabetic(5));

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
        courier.setId(RandomStringUtils.randomNumeric(1));
        courierApi.removeCourier(courier)
                .then()
                .assertThat()
                .body("message", equalTo("Курьера с таким id нет")); //Падает, потому что приходит неверное сообшение об ошибке. Лишняя точка, которой нет в требованиях
    }

}
