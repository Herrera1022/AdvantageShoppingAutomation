package co.edu.udea.qa.advantageshopping.exceptions;

public class PurchaseException extends AssertionError {

    public static final String ORDER_NOT_CONFIRMED = "The purchase was not confirmed";
    public static final String ORDER_WITHOUT_NUMBER = "The confirmed order does not have an order number";
    public static final String WRONG_PAYMENT_METHOD = "The order was not paid with the chosen payment method";
    public static final String WRONG_SUBTOTAL = "The order subtotal does not match the products in the cart";
    public static final String WRONG_TOTAL = "The order total is not the subtotal plus shipping";
    public static final String INCOMPLETE_PAYMENT_ACCEPTED = "The payment could be confirmed with incomplete data";

    public PurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
