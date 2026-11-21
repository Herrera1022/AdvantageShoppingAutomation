package co.edu.udea.qa.advantageshopping.exceptions;

public class LoginException extends AssertionError {

    public static final String CUSTOMER_NOT_SIGNED_IN = "The customer was not signed in with the registered username";
    public static final String INVALID_CREDENTIALS_ACCEPTED = "The store did not reject the invalid credentials";

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
