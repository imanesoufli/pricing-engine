public class Order {
    double[] prices;
    int[] qty;

    public Order(double[] prices, int[] qty) {
        this.prices = prices;
        this.qty = qty;
    }

    public double getSubtotal() {
        double total = 0;
        for (int i = 0; i < prices.length; i++) {
            total += prices[i] * qty[i];
        }
        return total;
    }
}
