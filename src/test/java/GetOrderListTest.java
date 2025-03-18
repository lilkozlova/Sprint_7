import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetOrderListTest {
    private GetOrderListSteps getOrderListSteps = new GetOrderListSteps();

    @Test
    public void getOrderList() {

        getOrderListSteps
                .getOrderList(1, "", 3, 1)
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
