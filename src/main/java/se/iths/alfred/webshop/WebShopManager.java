package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Discount;
import se.iths.alfred.webshop.model.HalfPriceDiscount;
import se.iths.alfred.webshop.model.Product;
import se.iths.alfred.webshop.model.TenPercentDiscount;

import java.util.List;

public class WebShopManager {

    private ProductStorage productStorage;

    void startWebshop() {
        setDiscount();
        printMenu();

    }

    public Discount setDiscount() {
        String discountCode;
        Discount discount = null;
        boolean keepRunning = true;
        //felaktig loop som aldrig triggar catch. behövs ifsatser som kollar om discountCode
        //är null eller isempty(?)
        while (keepRunning) {
            try {
                discountCode = IO.readln("Skriv rabattkod: ");
                if (discountCode.equalsIgnoreCase("halfprice")) {
                    discount = new HalfPriceDiscount("50% rabatt");
                    keepRunning = false;
                } else {
                    discount = new TenPercentDiscount("10% rabatt");
                    keepRunning = false;
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Fel: Skriv något. " + e.getMessage());
            }
        }
        IO.println(discount.getDescription());
        return discount;
    }

    void printMenu(){
        boolean keepRunning = true;
        while (keepRunning) {
            IO.println("\nVälj:");
            IO.println("1. Lägg till produkt");
            IO.println("2. Lista alla produkter");
            IO.println("3. Visa information om en produkt");
            IO.println("4. Avsluta");

            String choice = IO.readln();
            switch (choice){
                case "1" -> {
                    IO.println("case 1");
                    addProduct();
                }
                case "2" -> {
                    IO.println("case 2");
                    getProductsFromStorage();
                }
                case "3" -> IO.println("case 3");
                case "4" -> {
                    IO.println("Hejdå");
                    keepRunning = false;
                }
                default -> IO.println("default");
            }
        }
    }

    void addProduct(){
        String articleNumber = IO.readln("Mata in artikelnummer: ");
        String title = IO.readln("Mata in titel: ");
        double price = Double.parseDouble((IO.readln("Mata in pris: ")));
        String description = IO.readln("Mata in beskrivning: ");
        Product product = new Product(articleNumber, title, price, description);
        saveProductToStorage(product);
    }

    public WebShopManager(ProductStorage productStorage){
        this.productStorage = productStorage;
    }

    void saveProductToStorage(Product product) {
        productStorage.saveProduct(product);
    }

    List<Product> getProductsFromStorage() {
        return productStorage.getProducts();
    }

    Product getProductFromStorage(String articleNumber) {
        return productStorage.getProduct(articleNumber);
    }


}
