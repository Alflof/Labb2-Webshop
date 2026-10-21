package se.iths.alfred.webshop;

public class Main {

    //To do
    //Kolla java swing formattera fönster till windows standard så det ser fint ut
    //Kolla swing output table istället för string hörde jag att det finns

    static void main() {
        ProductStorage productStorage = new ProductFileStorage();
        OutInputHandler outInputHandler = new SwingOutInputHandler();
        WebShopManager webShopManager = new WebShopManager(productStorage, outInputHandler);
        webShopManager.startWebshop();
    }
}