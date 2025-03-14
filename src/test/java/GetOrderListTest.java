import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetOrderListTest {
    private GetOrderListSteps getOrderListSteps = new GetOrderListSteps();

    @Test
    public void getOrderList() {

        getOrderListSteps
                .getOrderList(1, "", 3, 1)
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
