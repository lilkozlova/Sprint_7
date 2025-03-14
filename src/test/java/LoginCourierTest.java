import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;

    @Test
    public void shouldReturnId() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps
                .createCourier(login, password, firstName);

        courierSteps
                .loginCourier(login, password)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void mandatoryFieldsShouldBeFilled() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps
                .createCourier(login, password, firstName);

        courierSteps
                .loginCourier("", password)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    public void wrongPasswordNotFound() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps
                .createCourier(login, password, firstName);

        courierSteps
                .loginCourier(login, "1234")
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        Integer id = courierSteps.loginCourier(login, password)
                .extract().body().path("id");
        if (id != null) {
            courierSteps.deleteCourier(id);
        }
    }


}

