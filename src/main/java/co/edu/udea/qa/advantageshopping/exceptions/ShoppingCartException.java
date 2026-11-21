package co.edu.udea.qa.advantageshopping.exceptions;

public class ShoppingCartException extends AssertionError {

    public static final String WRONG_NUMBER_OF_ITEMS = "The cart does not have the expected number of items";
    public static final String WRONG_TOTAL = "The cart total is not the sum of unit price times quantity";
    public static final String WRONG_PRODUCTS = "The cart does not contain the expected products";
    public static final String WRONG_QUANTITY = "The product does not have the expected quantity in the cart";
    public static final String CART_NOT_EMPTY = "The cart should be empty";
    public static final String SOLD_OUT_NOT_SHOWN = "The product should be shown as sold out";

    public ShoppingCartException(String message, Throwable cause) {
        super(message, cause);
    }
}
