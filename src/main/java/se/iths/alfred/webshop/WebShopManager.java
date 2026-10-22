package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Discount;
import se.iths.alfred.webshop.model.HalfPriceDiscount;
import se.iths.alfred.webshop.model.Product;
import se.iths.alfred.webshop.model.TenPercentDiscount;

import java.io.UncheckedIOException;
import java.util.List;

public class WebShopManager {

    private final ProductStorage productStorage;
    private final OutInputHandler outInputHandler;
    private Discount discount;

    public WebShopManager(ProductStorage productStorage, OutInputHandler outInputHandler) {
        this.productStorage = productStorage;
        this.outInputHandler = outInputHandler;
    }

    void startWebshop() {
        discount = chooseDiscount();
        if (discount == null) {
            outInputHandler.info("Ingen rabattkod valdes. Programmet avslutas.");
            return;
        }
        runMenu();
    }

    private Discount chooseDiscount() {
        String discountCode = validateString("Skriv din rabattkod: ");
        if (discountCode == null) {
            return null;
        }

        Discount chosenDiscount;
        if (discountCode.equalsIgnoreCase("halfprice")) {
            chosenDiscount = new HalfPriceDiscount("Din rabatt: 50%");
        } else {
            chosenDiscount = new TenPercentDiscount("Din rabatt: 10%");
        }
        outInputHandler.info(chosenDiscount.getDescription());
        return chosenDiscount;
    }

    private void runMenu() {
        while (true) {
            String choice = outInputHandler.menu();
            if (choice == null) {
                choice = "4";
            }
            try {
                switch (choice) {
                    case "1" -> addProduct();
                    case "2" -> listProducts();
                    case "3" -> findProduct();
                    case "4" -> {
                        outInputHandler.info("Hejdå!");
                        return;
                    }
                    default -> outInputHandler.info("Felaktigt val. Försök igen.");
                }
            } catch (UncheckedIOException e) {
                outInputHandler.info("Fel: " + e.getMessage());
            }
        }
    }

    private void addProduct() {
        String cancel = "Du avbröt processen. Ingen ny produkt läggs till.";
        outInputHandler.info("\nLägg till produkt");
        String articleNumber = validateString("Mata in artikelnummer: ");
        if (articleNumber == null) {
            outInputHandler.info(cancel);
            return;
        }
        articleNumber = articleNumber.toUpperCase();
        Product existingProduct = getProductFromStorage(articleNumber);
        if (existingProduct != null) {
            outInputHandler.info("Artikeln finns redan - "
                    + existingProduct.getArticleNumber()
                    + ": " + existingProduct.getTitle());
            return;
        }

        String title = validateString("Mata in titel: ");
        if (title == null) {
            outInputHandler.info(cancel);
            return;
        }
        Double price = validateDouble("Mata in pris:");
        if (price == null) {
            outInputHandler.info(cancel);
            return;
        }
        String description = validateString("Mata in beskrivning: ");
        if (description == null) {
            outInputHandler.info(cancel);
            return;
        }
        Product product = new Product(articleNumber, title, price, description);
        saveProductToStorage(product);
        outInputHandler.info("Produkt tillagd: " + articleNumber
                + ": " + title
                + " (" + price + " kr)"
                + " - " + description);
    }

    private void listProducts() {
        String allProducts = "Alla produkter:\n";
        List<Product> products = getProductsFromStorage();
        for (Product product : products) {
            double discountedPrice = discount.calculatePrice(product.getPrice());
            allProducts += product.getArticleNumber()
                    + ": " + product.getTitle()
                    + " (" + product.getPrice() + " kr)"
                    + " [Ditt pris: " + discountedPrice + " kr]"
                    + " - " + product.getDescription() + "\n";
        }
        outInputHandler.info(allProducts);

    }

    private void findProduct() {
        outInputHandler.info("Visa information om en produkt");
        String articleNumber = validateString("Mata in produktens artikelnummer: ");
        if (articleNumber == null) {
            return;
        }
        Product product = getProductFromStorage(articleNumber);
        if (product == null) {
            outInputHandler.info("Ingen produkt med artikelnummer " + articleNumber + " hittades.");
            return;
        }
        double discountedPrice = discount.calculatePrice(product.getPrice());
        outInputHandler.info(product.getArticleNumber()
                + ": " + product.getTitle()
                + " (" + product.getPrice() + " kr)"
                + " [Ditt pris: " + discountedPrice + " kr]"
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

    private String validateString(String promptText) {
        while (true) {
            String text = outInputHandler.prompt(promptText);
            if (text == null) {
                return null;
            }
            if (!text.isBlank()) {
                return text;
            }
            outInputHandler.info("Fel: Textfältet kan inte lämnas tomt.");
        }
    }

    private Double validateDouble(String promptText) {
        while (true) {
            String text = outInputHandler.prompt(promptText);
            if (text == null) {
                return null;
            }
            if (text.isBlank()) {
                outInputHandler.info("Fel: Textfältet kan inte lämnas tomt.");
                continue;
            }
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                outInputHandler.info("Fel: Använd endast siffror, t ex \"500\".");
            }
        }
    }
}
