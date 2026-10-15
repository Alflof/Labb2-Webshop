package se.iths.alfred.webshop.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HalfPriceDiscountTest {

    @Test
    public void calculatePriceTest(){
        Discount discount = new HalfPriceDiscount("50% rabatt");
        double price = 100;
        double discountPrice;
        discountPrice = discount.calculatePrice(price);
        Assertions.assertEquals(50, discountPrice);
    }
}