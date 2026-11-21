package co.edu.udea.qa.advantageshopping.models;

import lombok.Builder;
import lombok.Getter;

/**
 * Datos que pide cada medio de pago: cuenta SafePay o tarjeta MasterCredit.
 */
@Getter
@Builder(toBuilder = true)
public class PaymentDetails {

    private final PaymentMethod method;
    private final String safePayUsername;
    private final String safePayPassword;
    private final String cardNumber;
    private final String cvv;
    private final String expirationMonth;
    private final String expirationYear;
    private final String cardholderName;
}
