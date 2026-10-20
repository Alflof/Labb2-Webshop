package se.iths.alfred.webshop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TenPercentDiscountTest {

    @Test
    public void calculatePriceReturns10PercentOff() {
        Discount discount = new TenPercentDiscount("10% rabatt");
        double price = 100;
        double discountPrice = discount.calculatePrice(price);
        Assertions.assertEquals(90, discountPrice);
    }
}