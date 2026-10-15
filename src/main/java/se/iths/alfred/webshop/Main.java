package se.iths.alfred.webshop;

public class Main {

    //Labbpdfen progress, har gjort alla sidor nu

    static void main() {
        ProductStorage productStorage = new ProductFileStorage();
        OutInputHandler outInputHandler = new SwingOutInputHandler();
        WebShopManager webShopManager = new WebShopManager(productStorage, outInputHandler);
        webShopManager.startWebshop();
    }
}