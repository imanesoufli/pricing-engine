public class PricingEngine {

    DiscountService discountService = new DiscountService();
    TaxService taxService = new TaxService();

    public double calculate(Order order, String type, String code) {

        double subtotal = order.getSubtotal();

        double afterDiscount = discountService.apply(subtotal, code, type);

        return taxService.apply(afterDiscount);
    }
}
