import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.CourierRandomGen;
import ru.yandex.praktikum.model.DataCourier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CreatingCourierTest {
    private CourierSteps courierSteps;
    private DataCourier dataCourier;
    private int courierId;


    @Before

    public void setUp() {
        courierSteps = new CourierSteps();
        dataCourier = CourierRandomGen.getRandom();
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Позитивная проверка создания курьера")
    public void creatingCourierTest() {
        ValidatableResponse response = courierSteps.createCourier(dataCourier);
        response.assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierId = courierSteps.loginCourier(Courier.from(dataCourier))
                .extract()
                .jsonPath()
                .getInt("id");
    }
    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Негатавная проверка создания курьера с существующим логином")
    public void duplicateCourierTest() {
        courierSteps.createCourier(dataCourier);
        ValidatableResponse response = courierSteps.createCourier(dataCourier);
        response.assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierId = courierSteps.loginCourier(Courier.from(dataCourier))
                .extract()
                .jsonPath()
                .getInt("id");
    }
    @Test
    @DisplayName("Создание курьера с пустым полем login")
    @Description("Негативная проверка создания курьера с пустым полем login")
    public void createWithEmptyLoginTest() {
        dataCourier.setLogin(null);
        ValidatableResponse response = courierSteps.createCourier(dataCourier);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
    @Test
    @DisplayName("Создание курьера с пустым полем password")
    @Description("Негативная проверка создания курьера с пустым полем password")
    public void createEmptyPasswordTest() {
        dataCourier.setPassword(null);
        ValidatableResponse response = courierSteps.createCourier(dataCourier);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
    @After

    public void deleteCourier() {
        if (courierId != 0) {
            courierSteps.delete(String.valueOf(courierId));
        }
    }
}
