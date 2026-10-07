package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Product;

import java.util.List;

public interface ProductStorage {

    void saveProduct(Product product);              //sparar en produkt

    List<Product> getProducts();                    //hämtar alla produkter

    Product getProduct(String articleNumber);       //hämtar en produkt utifrån artikelnummer
}
