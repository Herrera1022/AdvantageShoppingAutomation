package co.edu.udea.qa.advantageshopping.utils;

import co.edu.udea.qa.advantageshopping.models.PaymentDetails;
import co.edu.udea.qa.advantageshopping.models.PaymentMethod;

/**
 * Construye datos de pago válidos para cada medio de pago que acepta la tienda (datos de demostración, no cuentas reales).
 */
public final class PaymentDetailsFactory {

    private PaymentDetailsFactory() {
    }

    public static PaymentDetails validDetailsFor(PaymentMethod method) {
        return PaymentDetails.builder()
                .method(method)
                .safePayUsername("anasafe1")
                .safePayPassword("Udea2026")
                .cardNumber("488612345678")
                .cvv("123")
                .expirationMonth("12")
                .expirationYear("2030")
                .cardholderName("Ana Restrepo")
                .build();
    }
}
