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
        if (discount == null){
            outInputHandler.info("Ingen rabattkod valdes. Programmet avslutas.");
            return;
        }
        printMenu();
    }

    public Discount setDiscount() {
        String discountCode = validateString("Skriv din rabattkod: ");
        if (discountCode == null){
            return null;
        }

        if (discountCode.equalsIgnoreCase("halfprice")){
            discount = new HalfPriceDiscount("Din rabatt: 50%");
        } else {
            discount = new TenPercentDiscount("Din rabatt: 10%");
        }

        outInputHandler.info(discount.getDescription());
        return discount;
    }

    void printMenu(){
        boolean keepRunning = true;
        while (keepRunning) {
            String choice = outInputHandler.menu();
            if (choice == null){
                outInputHandler.info("Hejdå!");
                break;
            }
            switch (choice){
                case "1" -> addProduct();
                case "2" -> listProducts();
                case "3" -> searchProducts();
                case "4" -> {
                    outInputHandler.info("Hejdå!");
                    keepRunning = false;
                }
                default -> outInputHandler.info("Felaktigt val. Försök igen.");
            }
        }
    }

    void addProduct() {
        outInputHandler.info("\nLägg till produkt");
        String articleNumber = validateString("Mata in artikelnummer: ");
        if (articleNumber == null) {
            return;
        }
        articleNumber = articleNumber.toUpperCase();
        Product existingProduct = getProductFromStorage(articleNumber);
        if (existingProduct != null)
        {
            outInputHandler.info("Artikeln finns redan - "
                    + existingProduct.getArticleNumber()
                    + ": " + existingProduct.getTitle());
            return;
        }

        String title = validateString("Mata in titel: ");
        if (title == null){
            return;
        }
        Double price = validateDouble("Mata in pris:");
        if (price == null){
            return;
        }
        String description = validateString("Mata in beskrivning: ");
        if (description == null){
            return;
        }
        Product product = new Product(articleNumber, title, price, description);
        saveProductToStorage(product);
    }

    void listProducts(){
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
        outInputHandler.info("Visa information om en produkt");
        String productId = validateString("Mata in produktens artikelnummer: ");
        if (productId == null){
            return;
        }
        Product product = getProductFromStorage(productId);
        if (product == null){
            outInputHandler.info("Ingen produkt med artikelnummer " + productId + " hittades.");
            return;
        }
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

    private String validateString(String promptText){
        while (true) {
            String text = outInputHandler.prompt(promptText);
            if (text == null) {
                return null;
            }
            if (!text.isBlank()){
                return text;
            }
            outInputHandler.info("Fel: Textfältet kan inte lämnas tomt.");
        }
    }

    private Double validateDouble(String promptText){
        while (true){
            String text = outInputHandler.prompt(promptText);
            if (text == null){
                return null;
            }
            if (text.isBlank()){
                outInputHandler.info("Fel: Textfältet kan inte lämnas tomt.");
                continue; //VARFÖR CONTINUE HÄR?
            }
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                outInputHandler.info("Fel: Använd endast siffror, t ex \"500\".");
            }
        }
    }
}
