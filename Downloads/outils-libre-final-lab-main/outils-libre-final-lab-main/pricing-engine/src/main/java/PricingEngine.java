import java.util.List;

// BAD DESIGN - Everything in one class, no separation of concerns
public class PricingEngine {

    public double calc(List<Double> prices, List<Integer> quantities,
                       String customerType, String discountCode) {

        // Step 1: Calculate subtotal
        double subtotal = 0;
        for (int i = 0; i < prices.size(); i++) {
            subtotal = subtotal + prices.get(i) * quantities.get(i);
        }

        // Step 2: Apply discount based on customer type
        double discountAmount = 0;
        if (customerType.equals("VIP")) {
            discountAmount = subtotal * 0.10;
        } else if (customerType.equals("REGULAR")) {
            discountAmount = 0;
        }

        // Step 3: Apply discount code on top
        if (discountCode.equals("SAVE10")) {
            discountAmount = discountAmount + subtotal * 0.10;
        } else if (discountCode.equals("SAVE20")) {
            discountAmount = discountAmount + subtotal * 0.20;
        } else if (discountCode.equals("SAVE5")) {
            discountAmount = discountAmount + subtotal * 0.05;
        }

        // Step 4: Make sure discount doesn't exceed subtotal
        if (discountAmount > subtotal) {
            discountAmount = subtotal;
        }

        // Step 5: Calculate tax (19% TVA)
        double afterDiscount = subtotal - discountAmount;
        double tax = afterDiscount * 0.19;

        // Step 6: Final price
        double finalPrice = afterDiscount + tax;

        System.out.println("Subtotal: " + subtotal);
        System.out.println("Discount: " + discountAmount);
        System.out.println("Tax: " + tax);
        System.out.println("Final: " + finalPrice);

        return finalPrice;
    }

    public static void main(String[] args) {
        PricingEngine engine = new PricingEngine();
        List<Double> prices = List.of(100.0, 50.0, 200.0);
        List<Integer> quantities = List.of(2, 3, 1);
        double result = engine.calc(prices, quantities, "VIP", "SAVE10");
        System.out.println("Result: " + result);
    }
}