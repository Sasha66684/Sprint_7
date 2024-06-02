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

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class CourierAuthorizationTest {
    private CourierSteps courierSteps;
    private DataCourier dataCourier;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
        dataCourier = CourierRandomGen.getRandom();
        courierSteps.createCourier(dataCourier);
        courier = Courier.from(dataCourier);
        ValidatableResponse response = courierSteps.loginCourier(Courier.from(dataCourier));
        courierId = response.extract().path("id");

    }
    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Позитивная проверка курьера добавление в системе")
    public void loginCourierTest() {
        ValidatableResponse response = courierSteps.loginCourier(courier);
        response.assertThat()
                .statusCode(SC_OK)
                .body("id", greaterThan(0))
                .extract()
                .path("id");
        courierId = response.extract().path("id");
    }
    @Test
    @DisplayName("Тест на ошибку  пустого логина")
    @Description("Негативная проверка курьера с пустым полем login")
    public void loginWithEmptyTest() {
        courier.setLogin(null);
        ValidatableResponse response = courierSteps.loginCourier(courier);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Курьер с пустым полем password")
    @Description("Негативная проверка курьера с пустым полем password")
    public void loginWithEmptyPasswordTest() {
        courier.setPassword("");
        ValidatableResponse response = courierSteps.loginCourier(courier);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Курьер c несуществующими данными")
    @Description("Негативная проверка курьера с несуществующими данными")
    public void courierNonExistDataTest() {
        courier.setLogin("dghnytrd");
        courier.setPassword("45gg55eg");
        ValidatableResponse response = courierSteps.loginCourier(courier);
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @After
    public void deleteCourier() {
        courierSteps.delete(String.valueOf(courierId));
    }
}
