import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.CourierRandomGen;
import ru.yandex.praktikum.model.DataCourier;
import ru.yandex.praktikum.steps.CourierSteps;

import java.util.concurrent.ThreadLocalRandom;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierDeleteTest {
    private CourierSteps courierSteps;
    private DataCourier dataCourier;
    private int courierId;

    @Before

    public void setUp() {
        courierSteps = new CourierSteps();
        dataCourier = CourierRandomGen.getRandom();
        courierSteps.createCourier(dataCourier);
        ValidatableResponse response = courierSteps.loginCourier(Courier.from(dataCourier));
        courierId = response.extract().path("id");
    }

    @Test
    @DisplayName("Удаление курьера из системы")
    @Description("Позитивная проверка удаления курьера из системы")
    public void deleteCourierTest() {
        ValidatableResponse response = courierSteps.delete(String.valueOf(courierId));
        response.assertThat()
                .statusCode(SC_OK)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    @Description("Негативная проверка удаления курьера без передачи id")
    public void deleteNotIdTest() {
        ValidatableResponse response = courierSteps.delete("");
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Not Found."));

    }
    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    @Description("Негативная проверка удаления курьера с несуществующим id")
    public void deleteWithNonExistIdTest() {
        ValidatableResponse response = courierSteps.delete(String.valueOf(ThreadLocalRandom.current().nextInt(1, 9)));
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет."));
    }
    @After

    public void deleteCourier() {
        if (courierId != 0) {
            courierSteps.delete(String.valueOf(courierId));
        }
    }

}
