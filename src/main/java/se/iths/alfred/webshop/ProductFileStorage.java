package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Product;

import java.nio.file.Path;
import java.util.List;

public class ProductFileStorage implements ProductStorage {

//    Produkterna ska sparas i filen som rader med semikolon-separerade värden:
//    B001;Clean Code;299.0;En bok om programmering
//    E001;Hörlurar;499.0;Trådlösa hörlurar
//    C001;T-shirt;149.0;Svart t-shirt

    private Path file = Path.of("products.txt");

    @Override
    public void saveProduct(Product product) {
        String fileLine = product.toFileLine();
//        skriv filrad sist i filen file på ny rad
    }

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(String articleNumber) {
        return null;
    }
}
