import dto.request.CreateOrder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CreateOrderSteps {
    private static final String HOST = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDERS = "/api/v1/orders";


    public ValidatableResponse createOrder(CreateOrder request) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body(request)
                .when()
                .post(ORDERS)
                .then();

    }

}
