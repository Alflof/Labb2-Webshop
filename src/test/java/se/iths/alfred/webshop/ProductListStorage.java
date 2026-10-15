package se.iths.alfred.webshop;

import se.iths.alfred.webshop.model.Product;

import java.util.List;

public class ProductListStorage implements ProductStorage {


    @Override
    public void saveProduct(Product product) {

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

//OBS denna klass var helt tom tills jag skulle börja skriva tester för discount
//Tryckte på en knapp för att lägga t ill metoderna och overrides automatiskt
//Varför detta hände nu och inte när jag försökte köra Main vet jag inte