package se.iths.alfred.webshop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.iths.alfred.webshop.model.Product;

public class WebShopManagerTest {

    @Test
    void saveTwoProductsAndCheckSize(){
        ProductStorage storage = new ProductListStorage();
        OutInputHandler outInputHandler = new ConsoleOutInputHandler();
        WebShopManager webShopManager = new WebShopManager(storage, outInputHandler);

        Product product1 = new Product("Z000", "title1", 500, "description1");
        Product product2 = new Product("X000", "title2", 500, "description2");
        webShopManager.saveProductToStorage(product1);
        webShopManager.saveProductToStorage(product2);

        Assertions.assertEquals(2, webShopManager.getProductsFromStorage().size());
    }

    @Test
    void getProductFromArticleNumber(){
        ProductStorage storage = new ProductListStorage();
        OutInputHandler outInputHandler = new ConsoleOutInputHandler();
        WebShopManager webShopManager = new WebShopManager(storage, outInputHandler);

        Product product1 = new Product("Z000", "title1", 500, "description1");
        webShopManager.saveProductToStorage(product1);
        Product fetchedProduct = webShopManager.getProductFromStorage("Z000");

        Assertions.assertEquals("Z000", fetchedProduct.getArticleNumber());
    }

        @Test
        void checkMissingProductReturnNull(){
            ProductStorage storage = new ProductListStorage();
            OutInputHandler outInputHandler = new ConsoleOutInputHandler();
            WebShopManager webShopManager = new WebShopManager(storage, outInputHandler);
            Product fetchedProduct = webShopManager.getProductFromStorage("Z000");

            Assertions.assertNull(fetchedProduct);
        }
}
