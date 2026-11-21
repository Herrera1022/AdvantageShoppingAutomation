package co.edu.udea.qa.advantageshopping.models;

import java.util.Arrays;

/**
 * Medios de pago que acepta la tienda.
 */
public enum PaymentMethod {

    SAFEPAY("SafePay"),
    MASTER_CREDIT("MasterCredit");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PaymentMethod fromName(String name) {
        return Arrays.stream(values())
                .filter(method -> method.displayName.equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown payment method: " + name));
    }
}
