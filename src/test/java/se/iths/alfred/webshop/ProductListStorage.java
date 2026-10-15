package se.iths.alfred.webshop;

import org.junit.jupiter.api.Test;
import se.iths.alfred.webshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListStorage implements ProductStorage {

    List<Product> products = new ArrayList<>(); //ska den vara private?

    @Test
    @Override
    public void saveProduct(Product product) {
        products.add(product);
    }

    @Test
    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Test
    @Override
    public Product getProduct(String articleNumber) {
        for (Product product : products){
            if (product.getArticleNumber().equalsIgnoreCase(articleNumber)){
                return product;
            }
        }
        return null;
    }
}