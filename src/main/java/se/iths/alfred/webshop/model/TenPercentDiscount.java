package se.iths.alfred.webshop.model;

public class TenPercentDiscount extends Discount {


    public TenPercentDiscount(String description) {
        super(description);
    }

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.9;
    }
}
