import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.yandex.praktikum.orders.CreatingReceivingListOfOrders;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class GettingListOrdersTest {
    private CreatingReceivingListOfOrders creatingReceivingListOfOrders;

    @Test
    @DisplayName("Запрос на получение списка заказов")
    @Description("Позитивная проверка получение списка заказов")
    public void listOrder() {
        creatingReceivingListOfOrders = new CreatingReceivingListOfOrders();
        ValidatableResponse responce = creatingReceivingListOfOrders.listOrder();
        responce.assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
