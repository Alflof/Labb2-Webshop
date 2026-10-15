package se.iths.alfred.webshop;

import javax.swing.*;

public class SwingOutInputHandler implements OutInputHandler {

    @Override
    public String prompt(String message) {
        return JOptionPane.showInputDialog(null, message);
    }

    @Override
    public void info(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public String menu() {
        return prompt("""
            Välj:
            1. Lägg till produkt
            2. Lista alla produkter
            3. Visa information om en produkt
            4. Avsluta""");
    }
}

//        IO read: String test= JOptionPane.showInputDialog(null, "Skriv något fint:");
//        IO print: JOptionPane.showMessageDialog(null, test);

//    void printMenu(){
//        boolean keepRunning = true;
//        while (keepRunning) {
//            IO.println("\nVälj:");
//            IO.println("1. Lägg till produkt");
//            IO.println("2. Lista alla produkter");
//            IO.println("3. Visa information om en produkt");
//            IO.println("4. Avsluta");
//
//            String choice = IO.readln();
//            switch (choice){
//                case "1" -> addProduct();
//                case "2" -> listProducts();
//                case "3" -> searchProducts();
//                case "4" -> {
//                    IO.println("Hejdå");
//                    keepRunning = false;
//                }
//                default -> IO.println("Felaktigt val, försök igen");
//            }
//        }
//    }