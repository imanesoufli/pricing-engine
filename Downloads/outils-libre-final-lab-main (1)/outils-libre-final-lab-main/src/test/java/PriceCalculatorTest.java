import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PriceCalculatorTest {

    @Test
    void testPrice() {

        Order order = new Order(
                new double[]{100, 50},
                new int[]{1, 2}
        );

        PricingEngine engine = new PricingEngine();

        double result = engine.calculate(order, "REGULAR", "SAVE10");

        assertTrue(result > 0);
    }
    @Test
    void testInvalidCode() {

        Order order = new Order(
                new double[]{100},
                new int[]{1}
        );

        PricingEngine engine = new PricingEngine();

        double result = engine.calculate(order, "REGULAR", "WRONG");

        assertTrue(result > 0);
    }}