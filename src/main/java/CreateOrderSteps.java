import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CreateOrderSteps {
    private static final String HOST = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDERS = "/api/v1/orders";


    public ValidatableResponse createOrder(String firstName, String lastName, String address, String metroStation,
                                           String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        return given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .baseUri(HOST)
                .body("{\n" +
                        "    \"firstName\": \"" + firstName + "\",\n" +
                        "    \"lastName\": \"" + lastName + "\",\n" +
                        "    \"address\": \"" + address + "\",\n" +
                        "    \"metroStation\": \"" + metroStation + "\",\n" +
                        "    \"phone\": \"" + phone + "\",\n" +
                        "    \"rentTime\": \"" + rentTime + "\",\n" +
                        "    \"deliveryDate\": \"" + deliveryDate + "\",\n" +
                        "    \"comment\": \"" + comment + "\",\n" +
                        "    \"color\": [\"" + String.join(",", color) + "\"]\n" +
                        "}")
                .when()
                .post(ORDERS)
                .then();

    }

}
