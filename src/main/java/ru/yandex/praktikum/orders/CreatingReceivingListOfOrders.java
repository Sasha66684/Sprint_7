package ru.yandex.praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.model.CourierOrder;
import ru.yandex.praktikum.steps.BaseUrl;
import static io.restassured.RestAssured.given;

public class CreatingReceivingListOfOrders extends BaseUrl {
    private static final String ORDERS_CREATE = "/api/v1/orders";
    private static final String ORDERS_LIST = "/api/v1/orders";
    @Step("Создание заказа")
    public ValidatableResponse createOrder(CourierOrder courierOrder) {
        return given()
                .spec(requestSpecification())
                .body(courierOrder)
                .when()
                .post(ORDERS_CREATE)
                .then();
    }
    @Step("Получение списка заказов")
    public ValidatableResponse listOrder() {
        return given()
                .spec(requestSpecification())
                .when()
                .when().get(ORDERS_LIST)
                .then();
    }
}
