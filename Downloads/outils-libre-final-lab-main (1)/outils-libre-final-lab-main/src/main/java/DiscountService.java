public class DiscountService {

    public double apply(double total, String code, String type) {

        if (code.equals("SAVE10")) total *= 0.9;
        else if (code.equals("SAVE20")) total *= 0.8;

        if (type.equals("VIP")) total *= 0.95;

        return total;
    }
}
