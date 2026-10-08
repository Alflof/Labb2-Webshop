package se.iths.alfred.webshop;

public class Main {

    //Kommentar för att hålla koll på vilken sida i labbpdf jag är på: 36

    static void main() {
        ProductStorage productStorage = new ProductFileStorage();
        WebShopManager webShopManager = new WebShopManager(productStorage);
        webShopManager.startWebshop();
    }
}
