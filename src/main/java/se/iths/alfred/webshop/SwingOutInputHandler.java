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