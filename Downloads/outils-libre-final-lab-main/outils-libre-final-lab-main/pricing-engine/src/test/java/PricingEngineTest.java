import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class PricingEngineTest {

    private PricingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PricingEngine();
    }

    @Test
    void testSubtotal_singleItem() {
        // 2 * 100 = 200, no discount, tax 19% → 238.0
        double result = engine.calc(
                List.of(100.0), List.of(2), "REGULAR", ""
        );
        assertEquals(238.0, result, 0.01);
    }

    @Test
    void testSubtotal_multipleItems() {
        // 100*2 + 50*3 = 350, no discount, tax 19% → 416.5
        double result = engine.calc(
                List.of(100.0, 50.0), List.of(2, 3), "REGULAR", ""
        );
        assertEquals(416.5, result, 0.01);
    }

    @Test
    void testVIPCustomer_gets10PercentDiscount() {
        // subtotal=100, discount=10, after=90, tax=17.1, final=107.1
        double result = engine.calc(
                List.of(100.0), List.of(1), "VIP", ""
        );
        assertEquals(107.1, result, 0.01);
    }

    @Test
    void testRegularCustomer_getsNoDiscount() {
        // subtotal=100, no discount, tax=19, final=119
        double result = engine.calc(
                List.of(100.0), List.of(1), "REGULAR", ""
        );
        assertEquals(119.0, result, 0.01);
    }

    @Test
    void testDiscountCode_SAVE10() {
        // subtotal=200, SAVE10=20, after=180, tax=34.2, final=214.2
        double result = engine.calc(
                List.of(100.0), List.of(2), "REGULAR", "SAVE10"
        );
        assertEquals(214.2, result, 0.01);
    }

    @Test
    void testDiscountCode_SAVE20() {
        // subtotal=200, SAVE20=40, after=160, tax=30.4, final=190.4
        double result = engine.calc(
                List.of(100.0), List.of(2), "REGULAR", "SAVE20"
        );
        assertEquals(190.4, result, 0.01);
    }

    @Test
    void testDiscountCode_SAVE5() {
        // subtotal=200, SAVE5=10, after=190, tax=36.1, final=226.1
        double result = engine.calc(
                List.of(100.0), List.of(2), "REGULAR", "SAVE5"
        );
        assertEquals(226.1, result, 0.01);
    }

    @Test
    void testVIP_with_SAVE10() {
        // subtotal=200, VIP=20 + SAVE10=20 → discount=40
        // after=160, tax=30.4, final=190.4
        double result = engine.calc(
                List.of(100.0), List.of(2), "VIP", "SAVE10"
        );
        assertEquals(190.4, result, 0.01);
    }

    @Test
    void testDiscount_doesNotExceedSubtotal() {
        double result = engine.calc(
                List.of(10.0), List.of(1), "VIP", "SAVE20"
        );
        assertTrue(result >= 0);
    }
}