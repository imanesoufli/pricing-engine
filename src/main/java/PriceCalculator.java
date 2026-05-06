public class PriceCalculator {

    public double calculateSubtotal(double[] prices, int[] qty) {
        double sum = 0;
        for (int i = 0; i < prices.length; i++) {
            sum += prices[i] * qty[i];
        }
        return sum;
    }

    public double calculateDiscount(double subtotal, String code, CustomerType type) {
        double discount = 0;

        if (code.equals("SAVE10")) discount = subtotal * 0.10;
        if (code.equals("SAVE20")) discount = subtotal * 0.20;

        if (type == CustomerType.VIP) {
            discount += subtotal * 0.05;
        }

        return discount;
    }

    public double calculateTax(double amount) {
        return amount * 0.19;
    }

    public double calc(double[] p, int[] q, String c, CustomerType type) {

        double subtotal = calculateSubtotal(p, q);
        double discount = calculateDiscount(subtotal, c, type);

        double after = subtotal - discount;

        double tax = calculateTax(after);

        return after + tax;
    }
}