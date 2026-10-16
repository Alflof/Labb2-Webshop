package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ProductFileStorage implements ProductStorage {

    private Path productsFile = Path.of("products.txt");

    @Override
    public void saveProduct(Product product) {
        String fileLine = product.toFileLine();
        try {
            Files.writeString(productsFile, fileLine +
                    System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("Fel - Kunde inte skriva till filen: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>(); //Skapa tom lista med produkter
        if (Files.notExists(productsFile)) { //Finns inte filen, returnera den tomma listan
            return products;
        }
        try {
            List<String> lines = Files.readAllLines(productsFile); //Läs rader från filen och stoppa i ny lista
            for (String line : lines) {
                Product product = Product.fromFileLine(line);
                products.add(product);
            }
        } catch (IOException e) {
            System.err.println("Kunde inte läsa raderna: " + e.getMessage());
        }

        return products;
    }

    @Override
    public Product getProduct(String articleNumber) {
        List<Product> products = getProducts();

        for (Product product : products) {
            if (product.getArticleNumber().equalsIgnoreCase(articleNumber)) {
                return product;
            }
        }
        return null;
    }
}
