package co.edu.udea.qa.advantageshopping.exceptions;

public class RegistrationException extends AssertionError {

    public static final String ACCOUNT_NOT_CREATED = "The customer account was not created with the expected username";
    public static final String WRONG_VALIDATION_MESSAGE = "The field did not show the expected validation message";
    public static final String INVALID_DATA_ACCEPTED = "The account could be created with invalid data";
    public static final String DUPLICATED_USERNAME_ACCEPTED = "The store did not reject an existing username";

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
