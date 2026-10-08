package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Product;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ProductFileStorage implements ProductStorage {

//    Produkterna ska sparas i filen som rader med semikolon-separerade värden:
//    B001;Clean Code;299.0;En bok om programmering
//    E001;Hörlurar;499.0;Trådlösa hörlurar
//    C001;T-shirt;149.0;Svart t-shirt

    private Path productsFile = Path.of("products.txt");

    @Override
    public void saveProduct(Product product) {
        String fileLine = product.toFileLine();
        try {
            //verifiera ny rad, ska ej tömma filen
            Files.writeString(productsFile, fileLine, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("Fel - Kunde inte skriva till filen: " +e.getMessage());
        }
    }

    @Override
    public List<Product> getProducts() {

        /*skapa tom lista med produkter
        kontrollera om filen inte finns, om den ej finns:
        returnera den tomma listan
        läs alla rader från filen
        för varje rad:
        product = Product.fromFileLine(rad)
        lägg till product i listan
        returnera listan*/

        return List.of();
    }

    @Override
    public Product getProduct(String articleNumber) {
        List<Product> products = getProducts();

        for (Product product : products){
            if (product.getArticleNumber().equals(articleNumber)){
                return product;
            }
        }
        /*för varje product i products:
        om product.getArticleNumber() är samma som articleNumber:
        returnera product
        returnera null (ingen träff i listan)*/
        return null;
    }
}
