import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.model.CourierOrder;

import ru.yandex.praktikum.orders.CreatingReceivingListOfOrders;
import ru.yandex.praktikum.steps.CourierSteps;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
public class OrderTest {
    private CreatingReceivingListOfOrders creatingReceivingListOfOrders; //апи
    private CourierOrder courierOrder;
    private final List<String> color;

    public OrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Color Scooter - {0}")
    public static Object[][] data() {
        return new Object[][]{
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }
    @Before
    public void setUp() {
        creatingReceivingListOfOrders = new CreatingReceivingListOfOrders();

    }
    @Test
    @DisplayName("Создание заказа с указанием параметров цвета")
    public void orderColorParamTest() {
        courierOrder = new CourierOrder(color);
        ValidatableResponse response = creatingReceivingListOfOrders.createOrder(courierOrder);
        response.assertThat()
                .statusCode(SC_CREATED)
                .body("track", greaterThan(0))
                .extract()
                .path("track");
    }

}
