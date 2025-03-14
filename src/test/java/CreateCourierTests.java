import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;


public class CreateCourierTests {
    private CourierSteps courierSteps = new CourierSteps();
    private String login;
    private String password;
    private String firstName;

    @Test
    public void shouldReturnOkTrue() {
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void mandatoryFieldsShouldBeFilled() {
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

        courierSteps
                .createCourier("", password, firstName)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void duplicateLoginNotAllowed() {
        login = "login";
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .createCourier(login, password, firstName);
        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));

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