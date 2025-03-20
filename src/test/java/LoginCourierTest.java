import dto.request.CourierBase;
import dto.request.CreateCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;


    @Before
    public void setUp() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);
        CreateCourier request = new CreateCourier(login, password, firstName);

        courierSteps
                .createCourier(request);
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    @Description("Проверяем может ли курьер авторизоваться в существующий аккаунт")
    public void shouldReturnId() {
        CourierBase request = new CourierBase(login, password);

        courierSteps
                .loginCourier(request)
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Попытка авторизоваться в существующий аккаунт курьера без логина")
    public void mandatoryFieldsShouldBeFilled() {
        CourierBase request = new CourierBase("", password);

        courierSteps
                .loginCourier(request)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация c неверным паролем")
    @Description("Попытка авторизоваться в существующий аккаунт используя неверный пароль")
    public void wrongPasswordNotFound() {
        CourierBase request = new CourierBase(login, "1234");

        courierSteps
                .loginCourier(request)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        CourierBase request = new CourierBase(login, "1234");

        Integer id = courierSteps.loginCourier(request)
                .extract().body().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }


}

