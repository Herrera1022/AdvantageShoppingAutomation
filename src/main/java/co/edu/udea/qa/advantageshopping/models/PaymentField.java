package co.edu.udea.qa.advantageshopping.models;

import java.util.Arrays;
import java.util.function.UnaryOperator;

/**
 * Datos de pago obligatorios, identificados por el nombre de negocio usado en los features.
 * Cada valor sabe cómo dejar vacío ese dato en un conjunto de datos de pago.
 */
public enum PaymentField {

    SAFEPAY_USERNAME("usuario", details -> details.toBuilder().safePayUsername("").build()),
    SAFEPAY_PASSWORD("contraseña", details -> details.toBuilder().safePayPassword("").build()),
    CARD_NUMBER("número de tarjeta", details -> details.toBuilder().cardNumber("").build()),
    CVV("código de seguridad", details -> details.toBuilder().cvv("").build()),
    CARDHOLDER_NAME("nombre del titular", details -> details.toBuilder().cardholderName("").build());

    private final String label;
    private final UnaryOperator<PaymentDetails> eraser;

    PaymentField(String label, UnaryOperator<PaymentDetails> eraser) {
        this.label = label;
        this.eraser = eraser;
    }

    public PaymentDetails leaveEmptyIn(PaymentDetails details) {
        return eraser.apply(details);
    }

    public static PaymentField fromLabel(String label) {
        return Arrays.stream(values())
                .filter(field -> field.label.equalsIgnoreCase(label.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown payment data: " + label));
    }
}
