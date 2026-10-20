package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Product;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ProductFileStorage implements ProductStorage {

    private final Path productsFile = Path.of("products.txt");

    @Override
    public void saveProduct(Product product) {
        String fileLine = product.toFileLine();
        try {
            Files.writeString(productsFile, fileLine +
                    System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new UncheckedIOException("Kunde inte skriva till filen: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        if (Files.notExists(productsFile)) {
            return products;
        }
        try {
            List<String> lines = Files.readAllLines(productsFile);
            for (String line : lines) {
                Product product = Product.fromFileLine(line);
                products.add(product);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Kunde inte läsa raderna: " + e.getMessage(), e);
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
