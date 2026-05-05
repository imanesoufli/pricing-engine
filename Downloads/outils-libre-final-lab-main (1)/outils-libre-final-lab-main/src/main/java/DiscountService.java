public class DiscountService {

    public double apply(double total, String code, String type) {

        // تحقق من null
        if (code == null) return total;

        switch (code) {
            case "SAVE10":
                total *= 0.9;
                break;

            case "SAVE20":
                total *= 0.8;
                break;

            case "STUDENT10":   // ⭐ feature جديدة
                total *= 0.9;
                break;

            default:
                System.out.println("Invalid discount code"); // ⭐ validation
        }

        // VIP discount
        if (type.equals("VIP")) {
            total *= 0.95;
        }

        return total;
    }
}
