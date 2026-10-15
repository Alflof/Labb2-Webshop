package se.iths.alfred.webshop;

public class Main {

    //Labbpdfen progress, har gjort sidor till och med 62

    static void main() {
        ProductStorage productStorage = new ProductFileStorage();
        WebShopManager webShopManager = new WebShopManager(productStorage);
        webShopManager.startWebshop();
    }
}