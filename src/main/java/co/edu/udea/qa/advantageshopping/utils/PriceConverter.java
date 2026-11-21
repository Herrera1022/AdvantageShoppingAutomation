package co.edu.udea.qa.advantageshopping.utils;

import java.math.BigDecimal;

/**
 * Convierte los precios que muestra la tienda (por ejemplo "$1,709.95") en números que se pueden comparar.
 */
public final class PriceConverter {

    private PriceConverter() {
    }

    public static BigDecimal toAmount(String displayedPrice) {
        String digits = displayedPrice.replaceAll("[^0-9.]", "");
        if (digits.isEmpty()) {
            throw new IllegalArgumentException("The text does not contain a price: " + displayedPrice);
        }
        return new BigDecimal(digits);
    }
}
