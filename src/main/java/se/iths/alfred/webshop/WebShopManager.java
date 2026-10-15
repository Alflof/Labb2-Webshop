package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Discount;
import se.iths.alfred.webshop.model.HalfPriceDiscount;
import se.iths.alfred.webshop.model.Product;
import se.iths.alfred.webshop.model.TenPercentDiscount;

import java.util.List;

public class WebShopManager {

    private ProductStorage productStorage;
    private Discount discount;

    public WebShopManager(ProductStorage productStorage){
        this.productStorage = productStorage;
    }

    void startWebshop() {
        discount = setDiscount();
        printMenu();
    }

    public Discount setDiscount() {
        Discount discount = null;
        while (discount == null) {
            try {
                String discountCode = IO.readln("Skriv rabattkod: ");

                if (discountCode == null || discountCode.isBlank() ) {
                    throw new IllegalArgumentException("Rabattkoden får inte vara tom.");
                }
                else if (discountCode.equalsIgnoreCase("halfprice")) {
                    discount = new HalfPriceDiscount("50% rabatt");
                } else {
                    discount = new TenPercentDiscount("10% rabatt");
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Fel: " +e.getMessage());
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
                case "1" -> addProduct();
                case "2" -> listProducts();
                case "3" -> searchProducts();
                case "4" -> {
                    IO.println("Hejdå");
                    keepRunning = false;
                }
                default -> IO.println("Felaktigt val, försök igen");
            }
        }
    }

    void addProduct(){
        IO.println("\nLägg till produkt");
        String articleNumber = IO.readln("Mata in artikelnummer: ");
        String title = IO.readln("Mata in titel: ");
        double price = Double.parseDouble((IO.readln("Mata in pris: ")));
        String description = IO.readln("Mata in beskrivning: ");
        Product product = new Product(articleNumber, title, price, description);
        saveProductToStorage(product);
    }

    void listProducts(){
        IO.println("\nLista alla produkter");
        List<Product> products = getProductsFromStorage();
        for (Product product : products){
            double discountedPrice = discount.calculatePrice(product.getPrice());
            IO.println(product.getArticleNumber()
                    + ": " + product.getTitle()
                    + " (" + discountedPrice + " kr)"
                    + " - " + product.getDescription());
        }
    }

    void searchProducts(){
        IO.println("\nVisa information om en produkt");
        String productId = IO.readln("Mata in produktens artikelnummer: ");
        Product product = getProductFromStorage(productId);
        double discountedPrice = discount.calculatePrice(product.getPrice());
        IO.println(product.getArticleNumber()
                + ": " + product.getTitle()
                + " (" + discountedPrice + " kr)"
                + " - " + product.getDescription());

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
