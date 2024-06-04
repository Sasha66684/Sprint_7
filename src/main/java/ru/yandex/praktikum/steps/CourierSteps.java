package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.Courier;
import ru.yandex.praktikum.model.DataCourier;
import static io.restassured.RestAssured.given;


public class CourierSteps extends  BaseUrl {

    private static final String COURIER_CREATE = "/api/v1/courier";
    private static final String COURIER_LOGIN = "/api/v1/courier/login";
    private static final String COURIER_DELETE = "/api/v1/courier/";
    @Step("Создание курьера в системе")
    public ValidatableResponse createCourier(DataCourier dataCourier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(dataCourier)
                .when()
                .post(COURIER_CREATE)
                .then();
    }
    @Step("Логин курьера в систему")
    public ValidatableResponse loginCourier(Courier courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }
    @Step("Удаления курьера из БЗ")
    public ValidatableResponse delete(String id) {
        return given()
                .spec(requestSpecification())
                .delete(COURIER_DELETE + id)
                .then();
    }
}
