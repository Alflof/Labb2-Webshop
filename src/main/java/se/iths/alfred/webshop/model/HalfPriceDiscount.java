package se.iths.alfred.webshop.model;

public class HalfPriceDiscount extends Discount {


    public HalfPriceDiscount(String description) {
        super(description);
    }

    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice / 2;
    }
}
