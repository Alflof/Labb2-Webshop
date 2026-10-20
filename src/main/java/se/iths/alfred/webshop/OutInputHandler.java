package se.iths.alfred.webshop;

public interface OutInputHandler {

    String prompt(String message);
    void info(String message);
    String menu();
}
