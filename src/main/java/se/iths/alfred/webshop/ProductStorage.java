package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Product;

import java.util.List;

public interface ProductStorage {

    void saveProduct(Product product);
    List<Product> getProducts();
    Product getProduct(String articleNumber);
}
