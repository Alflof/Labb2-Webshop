package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Discount;
import se.iths.alfred.webshop.model.HalfPriceDiscount;
import se.iths.alfred.webshop.model.Product;
import se.iths.alfred.webshop.model.TenPercentDiscount;

import java.util.List;

public class WebShopManager {

    private ProductStorage productStorage;
    private OutInputHandler outInputHandler;
    private Discount discount; //ÄR DETTA KORREKT? NÄMNS INTE VAR JAG SKA SPARA UNDAN DISCOUNT I PDFEN

    public WebShopManager(ProductStorage productStorage, OutInputHandler outInputHandler){
        this.productStorage = productStorage;
        this.outInputHandler = outInputHandler;
    }

    void startWebshop() {
        discount = setDiscount();
        printMenu();
    }

    public Discount setDiscount() {
        Discount discount = null;
        while (discount == null) {
            try {
                String discountCode = outInputHandler.prompt("Skriv rabattkod: ");
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
        outInputHandler.info(discount.getDescription());
        return discount;
    }

    void printMenu(){
        boolean keepRunning = true;
        while (keepRunning) {
            String choice = outInputHandler.menu();
            switch (choice){
                case "1" -> addProduct();
                case "2" -> listProducts();
                case "3" -> searchProducts();
                case "4" -> {
                    outInputHandler.info("Hejdå");
                    keepRunning = false;
                }
                default -> outInputHandler.info("Felaktigt val, försök igen");
            }
        }
    }

    void addProduct(){ //För varje stringinmatning, separat metod som verifierar?
        outInputHandler.info("\nLägg till produkt");
        String articleNumber = outInputHandler.prompt("Mata in artikelnummer: ");
        String title = outInputHandler.prompt("Mata in titel: ");
        double price = Double.parseDouble((outInputHandler.prompt("Mata in pris: ")));
        String description = outInputHandler.prompt("Mata in beskrivning: ");
        Product product = new Product(articleNumber, title, price, description);
        saveProductToStorage(product);
    }

    void listProducts(){
//        //V1
//        outInputHandler.info("\nLista alla produkter");
//        List<Product> products = getProductsFromStorage();
//        for (Product product : products){
//            double discountedPrice = discount.calculatePrice(product.getPrice());
//            outInputHandler.info(product.getArticleNumber()
//                    + ": " + product.getTitle()
//                    + " (" + discountedPrice + " kr)"
//                    + " - " + product.getDescription());
//        }

        //V2
        String allProducts = "Alla produkter:\n";
        List<Product> products = getProductsFromStorage();
        for (Product product : products){
            double discountedPrice = discount.calculatePrice(product.getPrice());
            allProducts += product.getArticleNumber()
                    + ": " + product.getTitle()
                    + " (" + discountedPrice + " kr)"
                    + " - " + product.getDescription() + "\n";
        }
        outInputHandler.info(allProducts);

    }

    void searchProducts(){ //Saknar felmeddelande om productId misslyckas
        outInputHandler.info("\nVisa information om en produkt");
        String productId = outInputHandler.prompt("Mata in produktens artikelnummer: ");
        Product product = getProductFromStorage(productId);
        double discountedPrice = discount.calculatePrice(product.getPrice());
        outInputHandler.info(product.getArticleNumber()
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
