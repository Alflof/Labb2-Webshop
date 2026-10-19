package se.iths.alfred.webshop;

public class Main {

    //To do
    // - Se över static, private, osv på alla metoder
    //- Rad 10 i ProductListStorage

    static void main() {
        ProductStorage productStorage = new ProductFileStorage();
        OutInputHandler outInputHandler = new SwingOutInputHandler();
        WebShopManager webShopManager = new WebShopManager(productStorage, outInputHandler);
        webShopManager.startWebshop();
    }
}