import java.util.List;

// ════════════════════════════════════════════════════
//  REFACTORED VERSION — Separation of Concerns
// ════════════════════════════════════════════════════

enum CustomerType {
    REGULAR, VIP
}

class Order {
    private final List<Double> prices;
    private final List<Integer> quantities;
    private final CustomerType customerType;
    private final String discountCode;

    public Order(List<Double> prices, List<Integer> quantities,
                 CustomerType customerType, String discountCode) {
        this.prices = prices;
        this.quantities = quantities;
        this.customerType = customerType;
        this.discountCode = discountCode;
    }

    public List<Double> getPrices()       { return prices; }
    public List<Integer> getQuantities()  { return quantities; }
    public CustomerType getCustomerType() { return customerType; }
    public String getDiscountCode()       { return discountCode; }
}

class PriceResult {
    private final double subtotal;
    private final double discountAmount;
    private final double tax;
    private final double finalPrice;

    public PriceResult(double subtotal, double discountAmount,
                       double tax, double finalPrice) {
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.tax = tax;
        this.finalPrice = finalPrice;
    }

    public double getSubtotal()       { return subtotal; }
    public double getDiscountAmount() { return discountAmount; }
    public double getTax()            { return tax; }
    public double getFinalPrice()     { return finalPrice; }

    @Override
    public String toString() {
        return String.format(
                "Subtotal:  %.2f\nDiscount:  %.2f\nTax:       %.2f\nFinal:     %.2f",
                subtotal, discountAmount, tax, finalPrice
        );
    }
}

class SubtotalCalculator {
    public double calculate(List<Double> prices, List<Integer> quantities) {
        double subtotal = 0;
        for (int i = 0; i < prices.size(); i++) {
            subtotal += prices.get(i) * quantities.get(i);
        }
        return subtotal;
    }
}

class DiscountCalculator {
    private static final double VIP_RATE    = 0.10;
    private static final double SAVE5_RATE  = 0.05;
    private static final double SAVE10_RATE = 0.10;
    private static final double SAVE20_RATE = 0.20;

    public double calculate(double subtotal, CustomerType customerType,
                            String discountCode) {
        double discount = 0;

        if (customerType == CustomerType.VIP) {
            discount += subtotal * VIP_RATE;
        }

        switch (discountCode) {
            case "SAVE5"  -> discount += subtotal * SAVE5_RATE;
            case "SAVE10" -> discount += subtotal * SAVE10_RATE;
            case "SAVE20" -> discount += subtotal * SAVE20_RATE;
        }

        return Math.min(discount, subtotal);
    }
}

class TaxCalculator {
    private static final double TAX_RATE = 0.19;

    public double calculate(double amountAfterDiscount) {
        return amountAfterDiscount * TAX_RATE;
    }
}

class PricingEngineRefactored {
    private final SubtotalCalculator subtotalCalculator;
    private final DiscountCalculator discountCalculator;
    private final TaxCalculator taxCalculator;

    public PricingEngineRefactored() {
        this.subtotalCalculator = new SubtotalCalculator();
        this.discountCalculator = new DiscountCalculator();
        this.taxCalculator      = new TaxCalculator();
    }

    public PriceResult calculate(Order order) {
        double subtotal      = subtotalCalculator.calculate(
                order.getPrices(), order.getQuantities());
        double discount      = discountCalculator.calculate(
                subtotal, order.getCustomerType(), order.getDiscountCode());
        double afterDiscount = subtotal - discount;
        double tax           = taxCalculator.calculate(afterDiscount);
        double finalPrice    = afterDiscount + tax;

        return new PriceResult(subtotal, discount, tax, finalPrice);
    }

    public static void main(String[] args) {
        PricingEngineRefactored engine = new PricingEngineRefactored();
        Order order = new Order(
                List.of(100.0, 50.0, 200.0),
                List.of(2, 3, 1),
                CustomerType.VIP,
                "SAVE10"
        );
        System.out.println(engine.calculate(order));
    }
}